import com.commercetools.api.models.common.CreatedBy;
import com.commercetools.api.models.common.LastModifiedBy;
import com.commercetools.api.models.common.LastModifiedByImpl;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.error.ErrorObject;
import com.commercetools.api.models.error.ErrorResponse;
import com.commercetools.api.models.error.ErrorResponseBuilder;
import com.commercetools.api.models.error.RequiredFieldErrorBuilder;
import com.commercetools.api.models.type.CustomFields;
import com.commercetools.importapi.models.customfields.CustomField;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.*;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class HelloWorld implements HttpFunction {
    
    private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

    private static Gson gson;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {


       gson = new GsonBuilder()
               .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
               .registerTypeAdapter(LastModifiedBy.class,new LastModifiedByInstanceCreator())
               .registerTypeAdapter(CreatedBy.class, new CreatedByInstanceCreator())
               .registerTypeAdapter(CustomFields.class,new CustomFieldsInstanceCreator())
               .create();
       JsonElement requestBody = JsonParser.parseReader(httpRequest.getReader());

       Customer customer = gson.fromJson(requestBody.getAsJsonObject()
               .get("resource").getAsJsonObject()
               .get("obj").getAsJsonObject(), CustomerImpl.class );

       JsonObject object = gson.fromJson(requestBody.getAsJsonObject()
               .get("resource").getAsJsonObject()
               .get("obj").getAsJsonObject(),JsonObject.class);

       CustomerSetLastNameAction lastNameAction = CustomerSetLastNameActionBuilder.of()
               .lastName("Gupta")
               .build();

        List<CustomerUpdateAction> list = new ArrayList<>();

       if(object.has("custom"))
       {
           StringBuilder sb = new StringBuilder(object.get("custom")
                   .getAsJsonObject().get("fields")
                   .getAsJsonObject()
                   .get("FavMovie").getAsString());
           CustomerSetCustomFieldAction fieldAction = CustomerSetCustomFieldActionBuilder.of()
                   .name("FavMovie")
                   .value(sb.append("_modifiedByExt").toString())
                   .build();
           CustomerUpdateAction updateAction2 = CustomerSetCustomFieldAction.of()
                   .withCustomerSetCustomFieldAction(f -> fieldAction);
           list.add(updateAction2);
       }

       CustomerUpdateAction updateAction = CustomerSetLastNameAction.of()
               .withCustomerSetLastNameAction(a -> lastNameAction);
       list.add(updateAction);

       CustomerUpdate update = CustomerUpdateBuilder.of()
               .actions(list)
               .build();

       httpResponse.setStatusCode(HttpURLConnection.HTTP_OK);
       httpResponse.setContentType("application/json");

       var writer = new PrintWriter(httpResponse.getWriter());

       writer.print(gson.toJson(update));
    }
}
