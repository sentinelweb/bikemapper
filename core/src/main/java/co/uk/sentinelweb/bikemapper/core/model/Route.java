package co.uk.sentinelweb.bikemapper.core.model;

import java.util.List;

/**
 * Created by robert on 18/06/16.
 */
public class Route {
    private Location _startLocation;
    private Address _startAddress;
    private Location _endLocation;
    private Address _endAddress;
    private List<Leg> _legs;

    public Address getEndAddress() {
        return _endAddress;
    }

    public void setEndAddress(final Address endAddress) {
        _endAddress = endAddress;
    }

    public Location getEndLocation() {
        return _endLocation;
    }

    public void setEndLocation(final Location endLocation) {
        _endLocation = endLocation;
    }

    public Address getStartAddress() {
        return _startAddress;
    }

    public void setStartAddress(final Address startAddress) {
        _startAddress = startAddress;
    }

    public Location getStartLocation() {
        return _startLocation;
    }

    public void setStartLocation(final Location startLocation) {
        _startLocation = startLocation;
    }

    public List<Leg> getLegs() {
        return _legs;
    }

    public void setLegs(final List<Leg> legs) {
        _legs = legs;
    }

    public static class Bounds {
        private Location northEast;
        private Location southWest;

        public Location getNorthEast() {
            return northEast;
        }

        public void setNorthEast(final Location northEast) {
            this.northEast = northEast;
        }

        public Location getSouthWest() {
            return southWest;
        }

        public void setSouthWest(final Location southWest) {
            this.southWest = southWest;
        }
    }
}
