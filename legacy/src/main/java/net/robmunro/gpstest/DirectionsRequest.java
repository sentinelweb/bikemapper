package net.robmunro.gpstest;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import net.robmunro.gpstest.model.Directions;
import net.robmunro.gpstest.model.GeoPointDesc;
import net.robmunro.gpstest.model.SearchData;
import net.robmunro.gpstest.util.OnCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DirectionsRequest extends AsyncTask<Uri, Integer, Long> {
    public static final int ERRCODE_OK = 0;
    public static final int ERRCODE_FAIL_UNKNOWN = -1;
    public static final int ERRCODE_FAIL_SERVER_ERROR = -2;
    public static final int ERRCODE_FAIL_EXCEPTION = -3;
    boolean cancelled = false;
    SearchData data = null;
    ArrayList<OnCompleteListener<DirectionsRequest>> listeners = new ArrayList<>();
    Directions result = null;
    int errcode = ERRCODE_FAIL_UNKNOWN;

    public DirectionsRequest(final SearchData data) {
        super();
        this.data = data;
    }

    @Override
    protected Long doInBackground(final Uri... params) {
        final StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.google.com/maps/api/directions/json?");
        urlString.append("origin=");//from
        encode(urlString, data.startPoint.p, data.startSearchStr);
        urlString.append("&destination=");//to
        encode(urlString, data.endPoint.p, data.endSearchStr);
        urlString.append("&waypoints=");//waypoints
        encodewp(urlString, data.waypoints);
        urlString.append("&sensor=true&mode=");//mode=driving,mode=bicycling(only US)
        urlString.append(data.type);
        final Document doc = null;
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            final String urlstr = urlString.toString();
            Log.d(LocationTestActivity.LOG_TAG, urlstr);
            url = new URL(urlstr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();

            final InputStream jsonInputStream = urlConnection.getInputStream();
            final StringBuffer jsonData = new StringBuffer();
            final byte[] buf = new byte[1000];
            int pos = 0;
            while ((pos = jsonInputStream.read(buf)) > -1) {
                jsonData.append(new String(buf, 0, pos, "utf-8"));
            }
            final JSONObject json = new JSONObject(new JSONTokener(jsonData.toString()));
            if ("OK".equals(json.getString("status"))) {
                final JSONArray routes = json.getJSONArray("routes");
                result = new Directions(routes);
                errcode = ERRCODE_OK;
            } else {
                errcode = ERRCODE_FAIL_SERVER_ERROR;
            }
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Cannot show route: MalformedURLException", 500).show();
        } catch (final IOException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Cannot show route: IOException", 500).show();
        } catch (final JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Toast.makeText(this, "Cannot show route: JSONException", 500).show();
        }
        return null;
    }

    private void encodewp(final StringBuilder urlString, final ArrayList<GeoPointDesc> waypoints) {
        for (int i = 0; i < waypoints.size(); i++) {
            final GeoPointDesc gpd = waypoints.get(i);
            encode(urlString, gpd.p, null);
            if (i < waypoints.size() - 1) {
                urlString.append("|");
            }
        }

    }

    private void encode(final StringBuilder urlString, final LatLng gp, final String endStr) {
        if (gp != null) {
            urlString.append(Double.toString(gp.latitude));
            urlString.append(",");
            urlString.append(Double.toString(gp.longitude));
        } else {
            urlString.append(endStr);//to
        }
    }

    //private String geoPtParam(final int i) {
//        return Double.toString(i / 1.0E6);
//    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(final Long result) {
        for (int i = listeners.size() - 1; i >= 0; i--) {
            listeners.get(i).onComplete(this);
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(final Integer... values) {
        super.onProgressUpdate(values);
    }

    public void addOnCompleteListener(final OnCompleteListener<DirectionsRequest> o) {
        listeners.add(o);
    }

    public void removeOnCompleteListener(final OnCompleteListener<DirectionsRequest> o) {
        listeners.remove(o);
    }

}
