package customer.cap_xsuaa_rbac.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.request.UserInfo;

import cds.gen.catalogservice.CatalogService_;
import cds.gen.catalogservice.Configuration;
import cds.gen.catalogservice.Configuration_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceConfigurationHandler implements EventHandler {

    @On(event = CqnService.EVENT_READ, entity = Configuration_.CDS_NAME)
    public void onReadConfiguration(CdsReadEventContext context) {
        List<Configuration> configurationList = new ArrayList<>();
        
        Configuration configuration = Configuration.create();

        UserInfo userInfo = context.getUserInfo();

        configuration.setUsername(userInfo.getName());

        if (!userInfo.getAttributeValues("Country").isEmpty()) {
            configuration.setCountry(userInfo.getAttributeValues("Country").get(0));

        }
        configuration.setWriteBooksOwn(userInfo.hasRole("WriteBooksOwn"));
        configuration.setWriteBooksCountry(userInfo.hasRole("WriteBooksCountry"));
        configuration.setWriteBooksAll(userInfo.hasRole("WriteBooksAll"));
        configuration.setWriteAuthorsOwn(userInfo.hasRole("WriteAuthorsOwn"));
        configuration.setWriteAuthorsCountry(userInfo.hasRole("WriteAuthorsCountry"));
        configuration.setWriteAuthorsAll(userInfo.hasRole("WriteAuthorsAll"));
        configuration.setEnableAuthors(userInfo.hasRole("EnableAuthors"));

        configurationList.add(configuration);

        context.setResult(configurationList);
    }
}
