package co.uk.sentinelweb.bikemapper.core.model;

import java.net.URI;

/**
 * An individual direction on the route
 * Created by robert on 18/06/16.
 */
public class Step {

    enum DistanceUnit {
        KM, M, MILE, FOOT, UNKNOWN
    }

    enum TimeUnit {
        HOUR, MINUTE, SECOND, UNKNOWN
    }

    enum TextType {
        TEXT, HTML
    }

    private Location _start;
    private Location _end;

    private Integer _distance;
    private final DistanceUnit _distanceUnit = DistanceUnit.UNKNOWN;
    private Integer _time;
    private final TimeUnit _timeUnit = TimeUnit.UNKNOWN;

    private String _instruction;
    private final TextType _instructionType = TextType.TEXT;

    private URI mapLink;
    private URI iconUrl;
}
