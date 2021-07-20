import com.commercetools.api.models.common.LastModifiedBy;
import com.commercetools.api.models.common.LastModifiedByImpl;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class LastModifiedByInstanceCreator implements InstanceCreator<LastModifiedBy> {



    public LastModifiedByInstanceCreator() {

    }

    @Override
    public LastModifiedBy createInstance(Type type) {
        return new LastModifiedByImpl();
    }
}
