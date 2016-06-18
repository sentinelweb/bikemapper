package co.uk.sentinelweb.bikemapper.net.response.google;

import java.util.List;

/**
 * Created by robert on 17/06/16.
 */
public class GoogleMapsDirectionsResponse {
    public String status;

    public List<GRoute> routes;

    public static class GRoute {

        public GBounds bounds;
        public List<String> warnings;
        public String summary;
        public String copyrights;
        public GPoly overview_polyline;
        //public List<String> waypoint_order;//test
        public List<GLeg> legs;
    }

    public static class GBounds {
        public GLocation northeast;
        public GLocation southwest;
    }

    public static class GLocation {
        public double lat;
        public double lng;
    }

    public static class GLeg {
        public List<GStep> steps;
        public GDimension distance;
        public GDimension duration;
        public GLocation start_location;
        public GLocation end_location;
        public String start_address;
        public String end_address;

    }

    public static class GStep {
        public GDimension distance;
        public GDimension duration;
        public GLocation start_location;
        public GLocation end_location;
        public GPoly polyline;
        public String html_instructions;
        public String travel_mode;
    }

    public static class GDimension {
        public String text;
        public Integer value;
    }

    public static class GPoly {
        public String points;
    }
}
