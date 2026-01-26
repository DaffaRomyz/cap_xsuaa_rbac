package customer.cap_xsuaa_rbac.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.catalogservice.Authors;
import cds.gen.catalogservice.AuthorsSetDisableContext;
import cds.gen.catalogservice.AuthorsSetEnableContext;
import cds.gen.catalogservice.Authors_;
import cds.gen.catalogservice.CatalogService_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceAuthorsHandler implements EventHandler {

    @Autowired
    PersistenceService db;

    @On(event = AuthorsSetEnableContext.CDS_NAME, entity = Authors_.CDS_NAME)
    public void setEnable(AuthorsSetEnableContext context) {
        CqnSelect select = context.getCqn();
        Authors authors = db.run(select).single(Authors.class);

        authors.setIsActive(true);

        CqnUpdate update = Update.entity(Authors_.CDS_NAME).entry(authors);
        db.run(update);

        context.getMessages().success("Enabled");
        context.setResult(authors);
        context.setCompleted();
    }

    @On(event = AuthorsSetDisableContext.CDS_NAME, entity = Authors_.CDS_NAME)
    public void setDisable(AuthorsSetDisableContext context) {
        CqnSelect select = context.getCqn();
        Authors authors = db.run(select).single(Authors.class);

        authors.setIsActive(false);

        CqnUpdate update = Update.entity(Authors_.CDS_NAME).entry(authors);
        db.run(update);

        context.getMessages().success("Disabled");
        context.setResult(authors);
        context.setCompleted();
    }
}
