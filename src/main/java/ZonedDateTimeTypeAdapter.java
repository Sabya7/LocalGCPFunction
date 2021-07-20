import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Type adapter for jsr310 {@link ZonedDateTime} class.
 *
 * @author Christophe Bornet
 */
public class ZonedDateTimeTypeAdapter extends DateTimeTypeAdapter<ZonedDateTime> {

    public ZonedDateTimeTypeAdapter() {
        super(ZonedDateTime::parse);
    }
}