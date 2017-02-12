package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.DistanceUnit;

/**
 * Created by robert on 12/02/2017.
 */

public class DistanceUnitMapper {

    public static final String SPACE = " ";

    public DistanceUnit map(final String text) {
        if (text != null && text.contains(SPACE)) {
            final String unitText = text.substring(text.lastIndexOf(SPACE) + 1, text.length());
            try {
                final String name = unitText.toUpperCase();
                return DistanceUnit.valueOf(name);
            } catch (final IllegalArgumentException ex) {
                return DistanceUnit.UNKNOWN;
            }
        }
        return DistanceUnit.UNKNOWN;
    }
}
