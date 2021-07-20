import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.Function;

/**
 * Abstract type adapter for jsr310 date-time types.
 *
 * @author Christophe Bornet
 */
abstract class TemporalTypeAdapter<T> extends TypeAdapter<T> {

    Function<String, T> parseFunction;

    TemporalTypeAdapter(Function<String, T> parseFunction) {
        Objects.requireNonNull(parseFunction);
        this.parseFunction = parseFunction;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }

    @Override
    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String temporalString = preProcess(in.nextString());
        return parseFunction.apply(temporalString);
    }

    public String preProcess(String in) {
        return in;
    }
}