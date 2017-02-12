package co.uk.sentinelweb.bikemapper.domain.model;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * A series of {@link Step} in a route
 * Created by robert on 18/06/16.
 */
@AutoValue
public abstract class Leg {
    public static Leg create(final Location start,
                             final Location end,
                             final Address startAddress,
                             final Address endAddress,
                             final Integer distance,
                             final DistanceUnit distanceUnit,
                             final Integer time,
                             final TimeUnit timeUnit,
                             final List<Step> steps
    ) {

        return new AutoValue_Leg(start, end, startAddress, endAddress, distance, distanceUnit, time, timeUnit, steps);
    }

    public abstract Location start();

    public abstract Location end();

    public abstract Address startAddress();

    public abstract Address endAddress();

    public abstract Integer distance();

    public abstract DistanceUnit distanceUnit();// = DistanceUnit.UNKNOWN;

    public abstract Integer time();

    public abstract TimeUnit timeUnit();// = TimeUnit.UNKNOWN;

    public abstract List<Step> steps();


}
