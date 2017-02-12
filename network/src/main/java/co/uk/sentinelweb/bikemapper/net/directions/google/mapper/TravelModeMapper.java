package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.TravelMode;

/**
 * Created by robert on 12/02/2017.
 */

public class TravelModeMapper {

    public TravelMode map(final String text) {
        if (text != null) {
            try {
                return TravelMode.valueOf(text.toUpperCase());
            } catch (final IllegalArgumentException ex) {
                return TravelMode.UNKNOWN;
            }
        }
        return TravelMode.UNKNOWN;
    }
}
