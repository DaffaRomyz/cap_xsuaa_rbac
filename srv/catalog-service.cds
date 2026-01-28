using { model } from '../db/schema';

@(requires: 'authenticated-user')
service CatalogService{
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'ReadBooks'},
        { grant: '*', to : 'WriteBooksOwn', where: (createdBy = $user)},
        { grant: '*', to : 'WriteBooksCountry', where: (country = $user.Country)},
        { grant: '*', to : 'WriteBooksAll'},
    ])
    entity Books as projection on model.Books;
    
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'ReadAuthors'},
        { grant: '*', to : 'WriteAuthorsOwn', where: (createdBy = $user)},
        { grant: '*', to : 'WriteAuthorsCountry', where: (country = $user.Country)},
        { grant: '*', to : 'WriteAuthorsAll'}
    ])
    entity Authors as projection on model.Authors actions {
        action setEnable() returns Authors;
        action setDisable() returns Authors;
    };

    entity Configuration as projection on model.Configuration;
}