package customer.cap_xsuaa_rbac.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ResultBuilder;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.request.UserInfo;

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
        if (!context.getUserInfo().hasRole("EnableAuthors")) {
            throw new ServiceException(ErrorStatuses.FORBIDDEN, "Not Authorized");
        }
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
        if (!context.getUserInfo().hasRole("EnableAuthors")) {
            throw new ServiceException(ErrorStatuses.FORBIDDEN, "Not Authorized");
        }
        CqnSelect select = context.getCqn();
        Authors authors = db.run(select).single(Authors.class);

        authors.setIsActive(false);

        CqnUpdate update = Update.entity(Authors_.CDS_NAME).entry(authors);
        db.run(update);

        context.getMessages().success("Disabled");
        context.setResult(authors);
        context.setCompleted();
    }

    @After(event = CqnService.EVENT_READ, entity = Authors_.CDS_NAME)
    public void afterReadAuthors(CdsReadEventContext context) {
        
        UserInfo userInfo = context.getUserInfo();

        List<Authors> authorsList = context.getResult().listOf(Authors.class);

        for (Authors authors : authorsList) {
            if ( (userInfo.hasRole("WriteAuthorsOwn") && userInfo.getName().equals(authors.getCreatedBy())) ||
                 (userInfo.hasRole("WriteAuthorsCountry") && !userInfo.getAttributeValues("Country").isEmpty() && userInfo.getAttributeValues("Country").get(0).equals(authors.getCountry()) ) ||
                 userInfo.hasRole("WriteAuthorsAll")) {
                authors.setIsDeletable(true);
            } else {
                authors.setIsDeletable(false);
            }
        }

        ResultBuilder resultBuilder = ResultBuilder.selectedRows(authorsList);
        if (context.getCqn().hasInlineCount()) {
            resultBuilder.inlineCount(authorsList.size());
        }
        context.setResult(resultBuilder.result());
    }
}
