using { model } from '../db/schema';

@(requires: 'authenticated-user')
service CatalogService{
    @odata.draft.enabled
    @(restrict: [
        { grant: 'READ', to : 'ReaderRole'},
        { grant: '*', to : 'AuthorRole', where: (createdBy = $user)},
        { grant: '*', to : 'AuditorRole', where: (country = $user.Country)},
        { grant: '*', to : 'ManagerRole'} // same as @(requires: 'ManagerRole')
    ])
    entity Books as projection on model.Books;
    
    @odata.draft.enabled
    @(restrict: [
        { grant: '*', to : 'AuthorRole', where: (createdBy = $user)},
        { grant: '*', to : 'AuditorRole', where: (country = $user.Country)},
        { grant: '*', to : 'ManagerRole'} // same as @(requires: 'ManagerRole')
    ])
    entity Authors as projection on model.Authors actions {
        action setEnable() returns Authors;
        action setDisable() returns Authors;
    };

}