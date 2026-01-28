using { model } from '../db/schema';

@(requires: 'authenticated-user')
service CatalogService{
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'ReadBooks'},
        { grant: 'WRITE', to : 'WriteBooksOwn', where: (createdBy = $user)},
        { grant: 'WRITE', to : 'WriteBooksCountry', where: (country = $user.Country)},
        { grant: 'WRITE', to : 'WriteBooksAll'},
    ])
    entity Books as projection on model.Books;
    
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'ReadAuthors'},
        { grant: 'WRITE', to : 'WriteAuthorsOwn', where: (createdBy = $user)},
        { grant: 'WRITE', to : 'WriteAuthorsCountry', where: (country = $user.Country)},
        { grant: 'WRITE', to : 'WriteAuthorsAll'}
    ])
    entity Authors as projection on model.Authors actions {
        action setEnable() returns Authors;
        action setDisable() returns Authors;
    };

}