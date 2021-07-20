import com.commercetools.api.models.common.CreatedBy;
import com.commercetools.api.models.common.LastModifiedBy;
import com.commercetools.api.models.common.LastModifiedByImpl;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.error.ErrorObject;
import com.commercetools.api.models.error.ErrorResponse;
import com.commercetools.api.models.error.ErrorResponseBuilder;
import com.commercetools.api.models.error.RequiredFieldErrorBuilder;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.*;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
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
               .create();
       JsonElement requestBody = JsonParser.parseReader(httpRequest.getReader());

       Customer customer = gson.fromJson(requestBody.getAsJsonObject()
               .get("resource").getAsJsonObject()
               .get("obj").getAsJsonObject(), CustomerImpl.class );

       CustomerSetLastNameAction lastNameAction = CustomerSetLastNameActionBuilder.of()
               .lastName("Gupta")
               .build();
       CustomerUpdateAction updateAction = CustomerSetLastNameAction.of()
               .withCustomerSetLastNameAction(a -> lastNameAction);

       CustomerUpdate update = CustomerUpdateBuilder.of()
               .actions(updateAction)
               .build();

       httpResponse.setStatusCode(HttpURLConnection.HTTP_OK);
       httpResponse.setContentType("application/json");

       var writer = new PrintWriter(httpResponse.getWriter());

       writer.print(gson.toJson(update));
    }
}
