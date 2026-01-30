package customer.cap_xsuaa_rbac.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ResultBuilder;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.request.UserInfo;

import cds.gen.catalogservice.Books;
import cds.gen.catalogservice.Books_;
import cds.gen.catalogservice.CatalogService_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceBooksHandler implements EventHandler {

    @Autowired
    PersistenceService db;

    @After(event = CqnService.EVENT_READ, entity = Books_.CDS_NAME)
    public void afterReadBooks(CdsReadEventContext context) {
        UserInfo userInfo = context.getUserInfo();

        CqnSelect select = context.getCqn();
        List<Books> booksList = db.run(select).listOf(Books.class);

        for (Books books : booksList) {
            if ((userInfo.hasRole("WriteBooksOwn") && userInfo.getName().equals(books.getCreatedBy())) ||
                    (userInfo.hasRole("WriteBooksCountry") && !userInfo.getAttributeValues("Country").isEmpty()
                            && userInfo.getAttributeValues("Country").get(0).equals(books.getCountry()))
                    ||
                    userInfo.hasRole("WriteBooksAll")) {
                books.setIsDeletable(true);
            } else {
                books.setIsDeletable(false);
            }
        }

        ResultBuilder resultBuilder = ResultBuilder.selectedRows(booksList);
        if (context.getCqn().hasInlineCount()) {
            resultBuilder.inlineCount(booksList.size());
        }
        context.setResult(resultBuilder.result());
    }
}
