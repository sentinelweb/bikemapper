package co.uk.sentinelweb.bikemapper.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * An individual direction on the route
 * Created by robert on 18/06/16.
 */
@AutoValue
public abstract class Step {
    public static Step create(final Location start,
                              final Location end,
                              final Integer distance,
                              final DistanceUnit distanceUnit,
                              final Integer time,
                              final TimeUnit timeUnit,
                              final String instruction,
                              final TextType instructionType,
                              final TravelMode travelMode,
                              @Nullable final String mapLink,
                              @Nullable final String iconUrl
    ) {
        return new AutoValue_Step(start, end, distance, distanceUnit, time, timeUnit, instruction, instructionType, travelMode, mapLink, iconUrl);
    }

    public abstract Location start();

    public abstract Location end();

    public abstract Integer distance();

    public abstract DistanceUnit distanceUnit();

    public abstract Integer time();

    public abstract TimeUnit timeUnit();

    public abstract String instruction();

    public abstract TextType instructionType();

    public abstract TravelMode travelMode();

    @Nullable
    public abstract String mapLink();

    @Nullable
    public abstract String iconUrl();


}
