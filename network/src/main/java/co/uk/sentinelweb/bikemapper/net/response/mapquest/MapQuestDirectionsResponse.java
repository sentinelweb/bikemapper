package co.uk.sentinelweb.bikemapper.net.response.mapquest;

import java.util.List;

/**
 * Created by robert on 17/06/16.
 */
public class MapQuestDirectionsResponse {
    public MInfo info;

    public static class MRoute {
        public MBounds boundingbox;
        public double distance;// miles
        public int time;// sec
        public String formattedTime;
        public List<Integer> locationSequence;
        public List<MLocationDesc> locations;
        public List<MLeg> legs;

    }

    public static class MLeg {
        public double distance;//miles or km
        public long time;//sec
        public List<MManeuvers> maneuvers;
    }

    public static class MManeuvers {
        public double distance;//miles or km
        public long time;//sec
        public int direction;
        public String directionName;
        public int turnType;
        public String narrative;
        public String iconUrl;
        public String mapUrl;
        public String transportMode;
        public String formattedTime;
        public MLocation startPoint;
        public List<String> streets;
        public List<String> signs;
        public List<String> maneuverNotes;

        public MDirection getMDirection() {
            return MDirection.from(direction);
        }

        public MTurnType getMTurnType() {
            return MTurnType.from(turnType);
        }
    }

    public static class MLocationDesc {
        public MLocation latLng;
        public String adminArea4;
        public String adminArea5Type;
        public String adminArea4Type;
        public String adminArea5;
        public String street;
        public String adminArea1;
        public String adminArea3;
        public String type;
        public long linkId;
        public String postalCode;
        public String sideOfStreet;
        public boolean dragPoint;
        public String adminArea1Type;
        public String geocodeQuality;
        public String geocodeQualityCode;
        public String adminArea3Type;

    }

    public static class MBounds {
        MLocation ul;
        MLocation lr;
    }

    public static class MInfo {
        public int status;
        public MCopyright copyright;
        public List<String> messages;
    }

    public static class MCopyright {
        public String text;
        public String imageUrl;
        public String imageAltText;
    }

    public static class MLocation {
        public double lat;
        public double lng;

    }

    public enum MDirection {
        NONE, NORTH, NORTHWEST, NORTHEAST, SOUTH, SOUTHEAST, SOUTHWEST, WEST, EAST;

        public static MDirection from(final int i) {
            return MDirection.values()[i];
        }
    }

    public enum MTurnType {
        STRAIGHT,
        SLIGHT_RIGHT, RIGHT, SHARP_RIGHT,
        REVERSE,
        SHARP_LEFT, LEFT, SLIGHT_LEFT,
        RIGHT_U_TURN, LEFT_U_TURN,
        RIGHT_MERGE, LEFT_MERGE,
        RIGHT_ON_RAMP, LEFT_ON_RAMP,
        RIGHT_OFF_RAMP, LEFT_OFF_RAMP,
        RIGHT_FORK, LEFT_FORK, STRAIGHT_FORK;

        public static MTurnType from(final int i) {
            return MTurnType.values()[i];
        }
    }
}
