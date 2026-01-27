using { model } from '../db/schema';

@(requires: 'authenticated-user')
service CatalogService{
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'DisplayBooks'},
        { grant: 'WRITE', to : 'WriteBooksOwn', where: (createdBy = $user)},
        { grant: 'WRITE', to : 'WriteBooksCountry', where: (country = $user.Country)},
        { grant: 'WRITE', to : 'WriteBooksAll'},
    ])
    entity Books as projection on model.Books;
    
    @odata.draft.enabled
    @(restrict: [
        { grant: '*', to : 'MaintainAuthorsOwn', where: (createdBy = $user)},
        { grant: '*', to : 'MaintainAuthorsCountry', where: (country = $user.Country)},
        { grant: '*', to : 'MaintainAuthorsAll'}
    ])
    entity Authors as projection on model.Authors actions {
        action setEnable() returns Authors;
        action setDisable() returns Authors;
    };

}