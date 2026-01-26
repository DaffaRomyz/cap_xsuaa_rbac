using { model } from '../db/schema';

service CatalogService {
    @odata.draft.enabled
    entity Books as projection on model.Books;
    
    @odata.draft.enabled
    entity Authors as projection on model.Authors actions {
        action setEnable() returns Authors;
        action setDisable() returns Authors;
    };

}