package co.uk.sentinelweb.bikemapper.domain.model;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by robert on 18/06/16.
 */
@AutoValue
public abstract class Route {
    public static Route create(final Location startLocation,
                               final Address startAddress,
                               final Location endLocation,
                               final Address endAddress,
                               final Location northEast,
                               final Location southWest,
                               final List<Leg> legs) {

        return new AutoValue_Route(startLocation, startAddress, endLocation, endAddress, northEast, southWest, legs);
    }

    public abstract Location startLocation();

    public abstract Address startAddress();

    public abstract Location endLocation();

    public abstract Address endAddress();

    public abstract Location northEast();

    public abstract Location southWest();

    public abstract List<Leg> legs();
}
