package net.robmunro.gpstest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.robmunro.gpstest.model.CompassData;
import net.robmunro.gpstest.model.Directions;
import net.robmunro.gpstest.model.GeoPointDesc;
import net.robmunro.gpstest.model.SearchData;
import net.robmunro.gpstest.model.SearchData.Type;
import net.robmunro.gpstest.ui.CompassView;
import net.robmunro.gpstest.ui.DirectionsOverlay;
import net.robmunro.gpstest.ui.TestItemisedOverlay;
import net.robmunro.gpstest.util.OnCompleteListener;
import net.robmunro.gpstest.util.SensorListeners;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import co.uk.sentinelweb.bikemapper.R;

//import com.google.android.maps.Overlay;
//import com.google.android.maps.OverlayItem;


/**
 * @author robm
 *         still need to reg for maps api key
 *         http://code.google.com/android/add-ons/google-apis/maps-api-signup.html
 *         <p>
 *         need md5sum of signing cert (keystore)
 *         <p>
 *         uses tutorials:
 *         http://developer.android.com/guide/tutorials/views/hello-mapview.html
 *         http://about-android.blogspot.com/2010/04/find-current-location-in-android-gps.html
 *         http://mobiforge.com/developing/story/using-google-maps-android
 *         for directions
 *         http://csie-tw.blogspot.com/2009/06/android-driving-direction-route-path.html
 *         this was ammended to use the google directions api
 *         http://code.google.com/apis/maps/documentation/directions/
 *         <p>
 *         Compass
 *         http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/graphics/Compass.html
 *         http://github.com/commonsguy/cw-advandroid/blob/master/Sensor/Compass/src/com/commonsware/android/sensor/CompassDemo.java
 *         http://developer.android.com/reference/android/hardware/SensorEvent.html
 *         <p>
 *         TODO better source for bike directions
 *         http://open.mapquestapi.com/directions/
 */
public class LocationTestActivity extends Activity implements OnMapReadyCallback {
    public static final String LOG_TAG = "Location Test";

    public static final int MODE_NONE = 0;
    public static final int MODE_START = 1;
    public static final int MODE_WAY = 2;
    public static final int MODE_END = 3;
    public static final int MODE_CURRENT = 4;

    static boolean initialised = false;
    static LocationTestActivity current = null;
    static CompassData compassData;
    GoogleMap map;

    static {
        compassData = new CompassData(new float[3], new float[3], new float[3], new float[3], new float[9], 0, -1000, -1000, -1);
    }

    // called after directions request has been completed
    static OnCompleteListener<SensorListeners> clientLocationListener = new OnCompleteListener<SensorListeners>() {
        @Override
        public void onComplete(final SensorListeners o) {
            if (initialised) {
                current.setCurrentLocation(o.currLoc);
            }
        }
    };

    // called after directions request has been completed
    static OnCompleteListener<DirectionsRequest> directionCompleteListener = new OnCompleteListener<DirectionsRequest>() {
        @Override
        public void onComplete(final DirectionsRequest me) {
            // remove last overlay
            if (initialised) {
                final Directions result = me.result;
                current.updateDirections(result);
                Log.d(LOG_TAG, "directions finished");
            }
        }
    };

    // called after directions request has been completed
    static OnCompleteListener<DirectionsRequest> setCompassListener = new OnCompleteListener<DirectionsRequest>() {
        @Override
        public void onComplete(final DirectionsRequest me) {

            if (initialised) {
                if (current.compass != null && me.result != null) {
                    compassData.setTarget(me.result.route.legs.get(0).end);
                }
            }
        }
    };

    static OnCompleteListener<Integer> beepListener = new OnCompleteListener<Integer>() {
        @Override
        public void onComplete(final Integer mode) {
            if (initialised) {
                int res = 0;
                switch (mode) {
                    case RadarService.LEFT:
                        res = R.drawable.indicator_orange;
                        break;
                    case RadarService.RIGHT:
                        res = R.drawable.indicator_red;
                        break;
                    case RadarService.TURN:
                        res = R.drawable.indicator_yellow;
                        break;
                    case RadarService.NONE:
                        res = R.drawable.indicator_off;
                        break;
                }
                final int res1 = res;
                current.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        current.lightView.setImageResource(res1);
                    }

                });

            }
        }
    };
    public static Directions directions;
    public static SearchData currentSearch;

    OnCompleteListener<LatLng> onPointClickListener = new OnCompleteListener<LatLng>() {
        @Override
        public void onComplete(final LatLng p) {
            Log.d(LocationTestActivity.LOG_TAG, "onPointClickListener");
            final GeoPointDesc point = new GeoPointDesc();
            point.p = p;
            addPointToOverlay(point);
            showDirectionTo(p);

            final Thread t = new Thread() {
                public void run() {
                    final Geocoder geoCoder = new Geocoder(LocationTestActivity.this, Locale.getDefault());
                    try {
                        final List<Address> addresses = geoCoder.getFromLocation(
                                p.latitude,
                                p.longitude, 1);
                        final StringBuffer addrBufffr = new StringBuffer();
                        if (addresses.size() > 0) {
                            final int sz = addresses.get(0).getMaxAddressLineIndex();
                            for (int i = 0; i < sz; i++) {
                                addrBufffr.append(addresses.get(0).getAddressLine(i));
                                if (i < sz - 1) {
                                    addrBufffr.append(",");
                                }
                            }
                        }
                        final String add = addrBufffr.toString();
                        point.d = add;
                        LocationTestActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LocationTestActivity.this, add, Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.d(LocationTestActivity.LOG_TAG, add);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }


    };

    // map components
    MapView mapView;

    Drawable startDrawable;
    Drawable endDrawable;
    Drawable wayPointDrawable;
    Drawable currLocationDrawable;

    TestItemisedOverlay startPointOverlay;
    TestItemisedOverlay endPointOverlay;
    TestItemisedOverlay wayPointsOverlay;
    TestItemisedOverlay currentLocationOverlay;

    //private OnTouchListener maponTouchListener;
    private DirectionsOverlay directionsOverlay;

    int color = Color.GREEN;
    ImageView lightView = null;
    // compass stuff
    CompassView compass;

    //searchBar
    LinearLayout buttCtnr;
    Button wayPtBut;
    Button startPtBut;
    Button endPtBut;
    Button clearBut;
    ToggleButton beepButton;

    EditText searchbar;
    RelativeLayout searchCtnr;

    int pointSelectMode = MODE_START;
    private LatLng _home;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        current = this;
        setContentView(R.layout.main);

        compass = (CompassView) findViewById(R.id.compass);
        compass.setData(compassData);
        //init mapview
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMapAsync(this);

//        mapView.setBuiltInZoomControls(true);
//        mapView.setSatellite(false);
//        mapView.setStreetView(false);
        lightView = (ImageView) findViewById(R.id.light);

        //init overlays
        //final List<Overlay> mapOverlays = mapView.getOverlays();
        wayPointDrawable = this.getResources().getDrawable(R.drawable.star_blue);
        startDrawable = this.getResources().getDrawable(R.drawable.star_green);
        endDrawable = this.getResources().getDrawable(R.drawable.star_red);
        currLocationDrawable = this.getResources().getDrawable(R.drawable.star_yellow);

        //create overlays
//        startPointOverlay = new TestItemisedOverlay(startDrawable, null, "startPointOverlay");
//        endPointOverlay = new TestItemisedOverlay(endDrawable, null, "endPointOverlay");
//        wayPointsOverlay = new TestItemisedOverlay(wayPointDrawable, null, "wayPointsOverlay");
        //currentLocationOverlay = new TestItemisedOverlay(currLocationDrawable, onPointClickListener, "currentLocationOverlay");

        //mapOverlays.add(startPointOverlay);
        // mapOverlays.add(endPointOverlay);
        // mapOverlays.add(wayPointsOverlay);
        //mapOverlays.add(currentLocationOverlay);

        //testing code
        //* 1E6
        _home = new LatLng((int) (51.52852), (int) (-0.08241));
        //currentLocation = home;
        compassData.setCurrentLocation(_home);
        // compass.setSrc(currentLocation);
        setCurrentLocation(_home);


        buttCtnr = (LinearLayout) findViewById(R.id.search_butt_ctnr);
        //buttCtnr.setVisibility(View.GONE);
        compass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttCtnr.setVisibility(buttCtnr.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        wayPtBut = (Button) findViewById(R.id.search_butt_wp);
        wayPtBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                wayPtBut.setEnabled(!wayPtBut.isEnabled());
                startPtBut.setEnabled(true);
                endPtBut.setEnabled(true);
                pointSelectMode = MODE_WAY;
            }
        });
        startPtBut = (Button) findViewById(R.id.search_butt_st);
        startPtBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                startPtBut.setEnabled(!startPtBut.isEnabled());
                wayPtBut.setEnabled(true);
                endPtBut.setEnabled(true);
                pointSelectMode = MODE_START;
            }
        });
        endPtBut = (Button) findViewById(R.id.search_butt_end);
        endPtBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                endPtBut.setEnabled(!endPtBut.isEnabled());
                wayPtBut.setEnabled(true);
                startPtBut.setEnabled(true);
                pointSelectMode = MODE_END;
            }
        });
        clearBut = (Button) findViewById(R.id.search_butt_clear);
        clearBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                endPtBut.setEnabled(true);
                wayPtBut.setEnabled(true);
                startPtBut.setEnabled(true);
                pointSelectMode = MODE_NONE;
                currentSearch = new SearchData();
                directions = new Directions(null);
                updateOverlays();
                mapView.invalidate();
            }
        });
        beepButton = (ToggleButton) findViewById(R.id.beep_but);
        beepButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent i = new Intent(LocationTestActivity.this, RadarService.class);
                if (beepButton.isChecked()) {
                    i.setAction(RadarService.ACTION_START_BEEPING);
                } else {
                    i.setAction(RadarService.ACTION_STOP_BEEPING);
                    final Intent j = new Intent(LocationTestActivity.this, RadarService.class);
                    j.setAction(RadarService.ACTION_STOP_LISTENING);
                    startService(j);
                }
                startService(i);
            }
        });

        searchbar = (EditText) findViewById(R.id.search_text);
        searchbar.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                currentSearch.endSearchStr = v.getText().toString().trim();
                currentSearch.endPoint.p = null;
                currentSearch.endPoint.d = "";
                showDirectionTo();
                return false;
            }
        });

        searchCtnr = (RelativeLayout) findViewById(R.id.search_bar_ctnr);
        searchCtnr.setVisibility(View.GONE);
        currentSearch = new SearchData(compassData.getCurrentLocation(), (LatLng) null, Type.driving);

        initialised = true;
        Log.d(LOG_TAG, "Oncreate finished...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        current = this;
        if (directions != null) {
            updateDirections(directions);
        }
        final Intent i = new Intent(this, RadarService.class);
        i.setAction(RadarService.ACTION_START_LISTENING);
        startService(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!beepButton.isChecked()) {
            final Intent j = new Intent(LocationTestActivity.this, RadarService.class);
            j.setAction(RadarService.ACTION_STOP_LISTENING);
            startService(j);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initialised = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        current = this;
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            searchCtnr.setVisibility(searchCtnr.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //    @Override
//    protected boolean isRouteDisplayed() {
//        return false;
//    }
    @Override
    public void onMapReady(final GoogleMap map) {
        map.addMarker(new MarkerOptions().position(_home).title("Home"));
        this.map = map;
        final UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
    }

    public void setCurrentLocation(final LatLng currLoc) {
        //currentLocation=currLoc;
        // update locationOverlay
//        final List<Overlay> mapOverlays = mapView.getOverlays();
//        if (currentLocationOverlay != null) {
//            mapOverlays.remove(currentLocationOverlay);
//        }
//        currentLocationOverlay = new TestItemisedOverlay(currLocationDrawable, onPointClickListener, "currentLocationOverlay");
//        final OverlayItem locOverlay = new OverlayItem(currLoc, "here", "now");
//        currentLocationOverlay.addOverlay(locOverlay);
//        mapOverlays.add(currentLocationOverlay);
//
//        mapView.invalidate();
//        mapView.getController().animateTo(currLoc);// slide to location

        if (directionsOverlay != null) {
            directions.setLocation(compassData.getCurrentLocation());
            if (compass != null) {
                // TODO make beep here;
                final int selectedStep = directions.getSelectedStep();
                if (selectedStep > -1) {
                    compassData.setCurrDirection(directions.currentLeg().steps.get(selectedStep));
                }
                if (selectedStep < directions.currentLeg().steps.size() - 1) {
                    compassData.setNextDirection(directions.currentLeg().steps.get(selectedStep + 1));
                }
            }
        } else {
            final Directions.Step s = new Directions.Step();
            s.start = currLoc;
            s.end = compassData.target != null ? compassData.target : currLoc;
            compassData.setCurrDirection(s);
            // compassData.setNextDirection( null );
        }
        if (compass != null) {
            compassData.setSrc(compassData.getCurrentLocation());
            compassData.setLastUpdated(System.currentTimeMillis());
        }
    }

    private void addPointToOverlay(final GeoPointDesc p) {
        final TestItemisedOverlay overlay = null;
        switch (pointSelectMode) {
            case MODE_START:
                //overlay=startPointOverlay;
                currentSearch.startPoint = p;
                break;
            case MODE_END:
                //overlay=endPointOverlay;
                currentSearch.endPoint = p;
                break;
            case MODE_WAY:
                currentSearch.addWayPoint(p);
                //overlay=wayPointOverlay;
                break;
            case MODE_CURRENT:/* not used*/
                //overlay=currentLocationOverlay;
                break;
        }

        updateOverlays();
        mapView.invalidate();
    }

    private void updateOverlays() {
        //final List<Overlay> mapOverlays = mapView.getOverlays();

//        OverlayItem overlayitem;
//        mapOverlays.remove(startPointOverlay);
//        if (currentSearch.startPoint != null && currentSearch.startPoint.p != null) {
//            startPointOverlay = new TestItemisedOverlay(startDrawable, onPointClickListener, "startPointOverlay");
//            overlayitem = new OverlayItem(currentSearch.startPoint.p, "Start", currentSearch.startPoint.d);
//            startPointOverlay.addOverlay(overlayitem);
//            mapOverlays.add(startPointOverlay);
//
//        }
//
//        mapOverlays.remove(endPointOverlay);
//        if (currentSearch.endPoint != null && currentSearch.endPoint.p != null) {
//            endPointOverlay = new TestItemisedOverlay(endDrawable, onPointClickListener, "endPointOverlay");
//            overlayitem = new OverlayItem(currentSearch.endPoint.p, "End", currentSearch.endPoint.d);
//            endPointOverlay.addOverlay(overlayitem);
//            mapOverlays.add(endPointOverlay);
//        }
//
//        mapOverlays.remove(wayPointsOverlay);
//        wayPointsOverlay = new TestItemisedOverlay(wayPointDrawable, onPointClickListener, "wayPointsOverlay");
//        if (currentSearch.waypoints.size() > 0) {
//            for (final GeoPointDesc wp : currentSearch.waypoints) {
//                overlayitem = new OverlayItem(wp.p, "Waypoint", wp.d);
//                wayPointsOverlay.addOverlay(overlayitem);
//            }
//            mapOverlays.add(wayPointsOverlay);
//        }
    }

    private void updateDirections(final Directions result) {

//        final List<Overlay> overlays = mapView.getOverlays();
//        final Vector<DirectionsOverlay> remove = new Vector<>();
//        for (final Overlay o : overlays) {
//            if (o instanceof DirectionsOverlay) {
//                remove.add((DirectionsOverlay) o);
//            }
//        }
//        overlays.removeAll(remove);
        directionsOverlay = null;
        if (result != null) {
            directions = result;
            if (directions.route.legs.size() > 0) {
                //currentSearch.startPoint.p = directions.route.legs.get(0).start;
                currentSearch.startPoint.d = directions.route.legs.get(0).startAddr;
                //currentSearch.endPoint.p = directions.route.legs.get(directions.route.legs.size()-1).end;
                currentSearch.endPoint.d = directions.route.legs.get(directions.route.legs.size() - 1).endAddr;
                //directions.currentLeg=directions.route.legs.get(0);
                directions.currentLeg().selectedStep = 0;
                directionsOverlay = new DirectionsOverlay(DirectionsOverlay.TYPE_WALKING, color);
                //overlays.add(directionsOverlay);
                directionsOverlay.setDirections(directions);
            }
            directions.setLocation(compassData.getCurrentLocation());
        }
        updateOverlays();
        mapView.invalidate();
    }


    public void showDirectionTo(final LatLng gp) {
        //currentSearch.endPoint = gp;
        if (currentSearch != null) {
            drawPath(currentSearch, setCompassListener);
        } else {
            Toast.makeText(this, "Cannot show route current location not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDirectionTo() {
        if (currentSearch != null) {
            drawPath(currentSearch, setCompassListener);
        } else {
            Toast.makeText(this, "Cannot show route current location not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void drawPath(final SearchData data, final OnCompleteListener<DirectionsRequest>... listeners) {
        final DirectionsRequest r = new DirectionsRequest(data);
        for (final OnCompleteListener<DirectionsRequest> o : listeners) {
            r.addOnCompleteListener(o);
        }

        r.addOnCompleteListener(directionCompleteListener);
        r.execute((Uri[]) null);

    }
    /*

	private void DrawPathDirAPI(GeoPoint src,GeoPoint dest, int color, MapView mMapView01){
		// connect to map web service
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps/api/directions/json?"); 
		urlString.append("origin=");//from
		urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 ));
		urlString.append("&destination=");//to
		urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
		urlString.append("&sensor=true&mode=walking");//mode=driving,mode=bicycling(only US)
		Log.d(LOCATION_TEST_TAG,"directions URL="+urlString.toString());
		// get the kml (XML) doc. And parse it to get the coordinates(direction route).
		Document doc = null;
		HttpURLConnection urlConnection= null;
		URL url = null;
		
		//if (directions.size()!=0) {
		//	for (DirectionsOverlay d : directions) {
		//		mMapView01.getOverlays().remove(d);
		//	}
		//}
		//directions.clear();
		
		List<Overlay> overlays = mMapView01.getOverlays();
		Vector<DirectionsOverlay> remove = new Vector<DirectionsOverlay>();
		for (Overlay o : overlays) {
			if (o instanceof DirectionsOverlay) {
				remove.add((DirectionsOverlay)o);
			}
		}
		overlays.removeAll(remove);
		directionsOverlay = new DirectionsOverlay(DirectionsOverlay.TYPE_WALKING,color);
		overlays.add(directionsOverlay);
		
		//DirectionsOverlay d = new DirectionsOverlay(DirectionsOverlay.TYPE_WALKING,color);
		try	{
			url = new URL(urlString.toString());
			urlConnection=(HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setConnectTimeout(5000);
			urlConnection.connect();

			InputStream jsonInputStream = urlConnection.getInputStream();
			StringBuffer jsonData = new StringBuffer();
			byte[] buf = new byte[1000];int pos = 0;
			while ((pos = jsonInputStream.read(buf))>-1) {
				jsonData.append(new String(buf,0,pos,"utf-8"));
			}
			JSONObject json = new JSONObject(new JSONTokener(jsonData.toString()));
			if ("OK".equals(json.getString("status"))) {
				JSONArray routes = json.getJSONArray("routes");
				if (routes.length()>0) {
					JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");// takes the first route
					JSONObject leg = legs.getJSONObject(0);// note there will be multiple legs if waypoints are used
					JSONArray steps = leg.getJSONArray("steps");
					if (steps.length()>0) {
						for (int stepCtr = 0 ;stepCtr<steps.length();stepCtr++) {
							JSONObject step = steps.getJSONObject(stepCtr);
							directionsOverlay.directions.add(new Directions(step));
						}
					} else {
						Log.d(LOCATION_TEST_TAG,"");
					}
				}
			}
			mMapView01.invalidate();
			directionsOverlay.setLocation(currentLocation);
		}
		catch (MalformedURLException e)		{
			e.printStackTrace();
			Toast.makeText(this, "Cannot show route: MalformedURLException", 500).show();
		}
		catch (IOException e)		{
			e.printStackTrace();
			Toast.makeText(this, "Cannot show route: IOException", 500).show();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "Cannot show route: JSONException", 500).show();
		}
		Log.d(LOCATION_TEST_TAG,"directions finished");
	}
	*/
}