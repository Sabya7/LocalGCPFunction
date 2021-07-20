import com.commercetools.api.models.common.CreatedBy;
import com.commercetools.api.models.common.CreatedByImpl;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class CreatedByInstanceCreator implements InstanceCreator<CreatedBy> {
    @Override
    public CreatedBy createInstance(Type type) {
        return new CreatedByImpl();
    }
}
