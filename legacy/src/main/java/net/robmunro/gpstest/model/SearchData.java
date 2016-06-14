package net.robmunro.gpstest.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SearchData {

    public enum Type {driving, walking, bicycling, none}

    public enum Unit {metric, imperial}

    public ArrayList<GeoPointDesc> waypoints = new ArrayList<>();
    public GeoPointDesc startPoint = new GeoPointDesc();
    public GeoPointDesc endPoint = new GeoPointDesc();
    public String startSearchStr;
    public String endSearchStr;

    public Type type = Type.walking;
    public Unit units = Unit.metric;

    public SearchData() {
    }

    public SearchData(final LatLng startPoint, final LatLng endPoint, final Type type) {
        super();
        this.startPoint.p = startPoint;
        this.endPoint.p = endPoint;
        this.type = type;
    }

    public SearchData(final String startSearchStr, final String endSearchStr, final Type type) {
        super();
        this.startSearchStr = startSearchStr;
        this.endSearchStr = endSearchStr;
        this.type = type;
    }

    public SearchData(final LatLng startPoint, final String endSearchStr, final Type type) {
        super();
        this.startPoint.p = startPoint;
        this.endSearchStr = endSearchStr;
        this.type = type;
    }

    public SearchData(final LatLng startPoint, final LatLng endPoint,
                      final String startSearchStr, final String endSearchStr, final Type type) {
        super();
        this.startPoint.p = startPoint;
        this.endPoint.p = endPoint;
        this.startSearchStr = startSearchStr;
        this.endSearchStr = endSearchStr;
        this.type = type;
    }

    public void addWayPoint(final GeoPointDesc wayPoint) {

        waypoints.add(wayPoint);
    }

}
