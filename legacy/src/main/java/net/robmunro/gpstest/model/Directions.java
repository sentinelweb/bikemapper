package net.robmunro.gpstest.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import net.robmunro.gpstest.LocationTestActivity;
import net.robmunro.gpstest.util.DistanceCalc;
import net.robmunro.gpstest.util.GeoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Directions {

    public static class Route {
        public ArrayList<Leg> legs = new ArrayList<>();
        public int selectedLeg = -1;
    }

    public static class Distance {
        public int timeVal = 0;
        public String timeUnit = null;
        public int distVal = 0;
        public String distUnit = null;

    }

    public static class Leg extends Distance {
        public ArrayList<Step> steps = new ArrayList<>();
        public LatLng start;
        public LatLng end;
        public String startAddr;
        public String endAddr;
        public int selectedStep = -1;
    }

    public static class Step extends Distance {
        public LatLng start;
        public LatLng end;
    }

    //public Directions.Leg currentLeg;
    //Vector<Directions> wayPoint = new Vector<Directions>();
    public int currentLeg = -1;
    public Route route = new Route();
    LatLng currentLoc;

    public Directions(final JSONArray routes) {
        super();
        try {
            if (routes != null && routes.length() > 0) {
                // TODO modify for multiple legs.
                final JSONArray legsJS = routes.getJSONObject(0).getJSONArray("legs");// takes the first route
                for (int i = 0; i < legsJS.length(); i++) {
                    final JSONObject legJS = legsJS.getJSONObject(i);// note there will be multiple legs if waypoints are used
                    final Leg leg = new Leg();
                    route.legs.add(leg);
                    currentLeg = 0;
                    try {
                        leg.startAddr = legJS.getString("start_address");
                        leg.endAddr = legJS.getString("end_address");
                    } catch (final JSONException e) {
                        //Log.d(LocationTestActivity.LOCATION_TEST_TAG,"");
                    }
                    leg.start = getPoint(legJS.getJSONObject("start_location"));
                    leg.end = getPoint(legJS.getJSONObject("end_location"));
                    getDist(legJS, leg);
                    Log.d(LocationTestActivity.LOG_TAG, leg.startAddr + ":" + leg.endAddr);
                    final JSONArray stepsJS = legJS.getJSONArray("steps");
                    if (stepsJS.length() > 0) {
                        for (int stepCtr = 0; stepCtr < stepsJS.length(); stepCtr++) {
                            final Step s = new Step();
                            leg.steps.add(s);
                            final JSONObject stepJS = stepsJS.getJSONObject(stepCtr);
                            s.start = getPoint(stepJS.getJSONObject("start_location"));
                            s.end = getPoint(stepJS.getJSONObject("end_location"));
                            getDist(stepJS, s);
                        }
                    } else {
                        Log.d(LocationTestActivity.LOG_TAG, "No steps...");
                        //Toast.makeText(this, text, duration);
                        // TODO add errors
                    }
                }
            }
        } catch (final JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Leg currentLeg() {
        return route.legs.get(currentLeg);
    }

    private void getDist(final JSONObject js, final Distance d) {
        try {
            final JSONObject duration = js.getJSONObject("duration");
            if (duration != null) {
                d.timeUnit = duration.getString("text");
                d.timeVal = duration.getInt("value");
            }
            final JSONObject dist = js.getJSONObject("distance");
            if (dist != null) {
                d.distUnit = dist.getString("text");
                d.distVal = dist.getInt("value");
            }
        } catch (final JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private LatLng getPoint(final JSONObject startpoint) throws JSONException {
        return new LatLng((int) (startpoint.getDouble("lat")), (int) (startpoint.getDouble("lng")));//* 1E6
    }

    public void setLocation(final LatLng currLoc) {
        currentLoc = currLoc;
        float minDist = 1E9f;
        for (int i = 0; i < currentLeg().steps.size(); i++) {
            final Directions.Step d = currentLeg().steps.get(i);
            final float dist = (float) DistanceCalc.calculateDistance(currentLoc, d.start, DistanceCalc.METRES);
            Log.d(LocationTestActivity.LOG_TAG, "minDist:" + i + ":" + dist + ":" + minDist);
            if (dist < minDist) {
                setSelectedStep(i);
                minDist = dist;
            }
        }
        Log.d(LocationTestActivity.LOG_TAG, "minDist:selStep:" + currentLeg().selectedStep);
        int minVal = -1;
        for (int i = currentLeg().selectedStep - 1; i < currentLeg().selectedStep + 2; i++) {
            // TODO this seems a bit messy, should just pass 3 steps into method.
            if (i < 0 || i >= currentLeg().steps.size()) {
                continue;
            }
            final Directions.Step d = currentLeg().steps.get(i);
            Double bearingDir = GeoUtils.bearing(d.start, d.end);
            if (bearingDir < 0) {
                bearingDir += 360;
            }
            final float distDir = (float) DistanceCalc.calculateDistance(d.end, d.start, DistanceCalc.METRES);

            Double bearingCurrSt = GeoUtils.bearing(d.start, currentLoc);
            if (bearingCurrSt < 0) {
                bearingCurrSt += 360;
            }
            final float distCurrSt = (float) DistanceCalc.calculateDistance(currentLoc, d.start, DistanceCalc.METRES);

            Double bearingCurrEnd = GeoUtils.bearing(currentLoc, d.end);
            if (bearingCurrEnd < 0) {
                bearingCurrEnd += 360;
            }
            final float distCurrEnd = (float) DistanceCalc.calculateDistance(currentLoc, d.end, DistanceCalc.METRES);
            final float angleStart = (float) (bearingCurrSt - bearingDir);
            final float angleEnd = (float) (bearingCurrEnd - bearingDir);
            float dist = Math.min(distCurrSt, distCurrEnd);
            if (Math.abs(angleStart) < 90 && Math.abs(angleEnd) < 90) {
                dist = (float) Math.min(Math.abs(distCurrSt * Math.sin(angleStart)), Math.abs(distCurrEnd * Math.sin(angleEnd)));
            }
            if (dist < minDist || (minVal == -1)) {// <= is important to set minVal to a sane value (! -1).
                minVal = i;
                minDist = dist;
            }
            Log.d(LocationTestActivity.LOG_TAG, "minDist:" + i + ":" + dist + ":" + minDist);
        }
        setSelectedStep(minVal);
        Log.d(LocationTestActivity.LOG_TAG, "minDist:selStep1:" + currentLeg().selectedStep);
    }

    public int getSelectedStep() {
        return currentLeg().selectedStep;
    }

    public void setSelectedStep(final int selectedStep) {
        if (selectedStep < 0 || selectedStep > currentLeg().steps.size() - 1) {
        }
        this.currentLeg().selectedStep = selectedStep;
    }

    public LatLng getJourneyStart() {
        if (route == null || route.legs.size() == 0) {
            return null;
        }
        return route.legs.get(0).start;
    }

    public LatLng getJourneyEnd() {
        if (route == null || route.legs.size() == 0) {
            return null;
        }
        return route.legs.get(route.legs.size() - 1).end;
    }
}
