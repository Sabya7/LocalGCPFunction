import com.commercetools.api.models.type.CustomFields;
import com.commercetools.api.models.type.CustomFieldsImpl;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class CustomFieldsInstanceCreator implements InstanceCreator<CustomFields> {
    @Override
    public CustomFields createInstance(Type type) {
        return new CustomFieldsImpl();
    }
}
