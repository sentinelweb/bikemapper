package co.uk.sentinelweb.bikemapper.core.model;

import java.net.URI;
import java.util.List;

/**
 * A series of {@link Step} in a route
 * Created by robert on 18/06/16.
 */
public class Leg {

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

    private URI _mapLink;
    private URI _iconUrl;

    private List<Step> _steps;

    public Integer getDistance() {
        return _distance;
    }

    public void setDistance(final Integer distance) {
        _distance = distance;
    }

    public DistanceUnit getDistanceUnit() {
        return _distanceUnit;
    }

    public Location getEnd() {
        return _end;
    }

    public void setEnd(final Location end) {
        _end = end;
    }

    public String getInstruction() {
        return _instruction;
    }

    public void setInstruction(final String instruction) {
        _instruction = instruction;
    }

    public TextType getInstructionType() {
        return _instructionType;
    }

    public Location getStart() {
        return _start;
    }

    public void setStart(final Location start) {
        _start = start;
    }

    public List<Step> getSteps() {
        return _steps;
    }

    public void setSteps(final List<Step> steps) {
        _steps = steps;
    }

    public Integer getTime() {
        return _time;
    }

    public void setTime(final Integer time) {
        _time = time;
    }

    public TimeUnit getTimeUnit() {
        return _timeUnit;
    }

    public URI getIconUrl() {
        return _iconUrl;
    }

    public void setIconUrl(final URI iconUrl) {
        this._iconUrl = iconUrl;
    }

    public URI getMapLink() {
        return _mapLink;
    }

    public void setMapLink(final URI mapLink) {
        this._mapLink = mapLink;
    }
}
