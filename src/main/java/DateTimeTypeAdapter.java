import java.util.function.Function;

/**
 * Abstract type adapter for jsr310 date-time types.
 *
 * @author Christophe Bornet
 */
abstract class DateTimeTypeAdapter<T> extends TemporalTypeAdapter<T> {

    DateTimeTypeAdapter(Function<String, T> parseFunction) {
        super(parseFunction);
    }

    @Override
    public String preProcess(String in) {
        if (in.endsWith("+0000")) {
            return in.substring(0, in.length()-5) + "Z";
        }
        return in;
    }
}