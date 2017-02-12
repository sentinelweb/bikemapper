package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.DistanceUnit;
import co.uk.sentinelweb.bikemapper.domain.model.TimeUnit;

/**
 * Created by robert on 12/02/2017.
 */

public class TimeUnitMapper {

    public static final String SPACE = " ";

    public TimeUnit map(final String text) {
        if (text != null && text.contains(SPACE)) {
            final String unitText = text.substring(text.lastIndexOf(SPACE), text.length());
            try {
                return TimeUnit.valueOf(unitText.toUpperCase());
            } catch (final IllegalArgumentException ex) {
                return TimeUnit.UNKNOWN;
            }
        }
        return TimeUnit.UNKNOWN;
    }
}
