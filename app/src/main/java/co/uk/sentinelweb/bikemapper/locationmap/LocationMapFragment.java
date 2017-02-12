package co.uk.sentinelweb.bikemapper.locationmap;

import android.Manifest;
import android.content.Context;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canelmas.let.AskPermission;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import co.uk.sentinelweb.bikemapper.BR;
import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.databinding.FragmentLocationMapBinding;

/**
 * A fragment to edit a location
 * <p>
 */
public class LocationMapFragment extends Fragment implements LocationMapContract.View, OnMapReadyCallback {

    private Circle _currentLocationCircle;

    public static LocationMapFragment newInstance(final Long id) {
        final LocationMapFragment fragment = new LocationMapFragment();
        final Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static final String ARG_ID = "id";
    public static final int ERROR_SNACKBAR_LENGTH = 5000;
    public static final int DIRECTIONS_SNACKBAR_LENGTH = 10000;

    FragmentLocationMapBinding _binding;

    LocationMapModel _model;
    private LocationMapContract.Presenter _presenter;

    private GoogleMap _googleMap;
    private Marker _currentLoctionMarker;// TODO mode to model

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationMapFragment() {
    }

    @Override
    public void setPresenter(final LocationMapContract.Presenter presenter) {
        this._presenter = presenter;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        _binding = FragmentLocationMapBinding.inflate(inflater, container, false);
        _binding.map.onCreate(null);
        _binding.map.getMapAsync(this);
        return _binding.container;
    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        this._presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        this._presenter.unsubscribe();
    }

    @Override
    public void onPause() {
        _binding.map.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        _binding.map.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        _binding.map.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void setModel(final LocationMapModel model) {
        _model = model;
        _binding.setModel(_model);
        _model.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable observable, final int propertyId) {
                switch (propertyId) {
                    case BR.route:
                        setRoute();
                        break;
                    case BR.routeBounds:
                        setRouteBounds();
                        break;
                    case BR.targetLocation:
                        updateMapLocation();
                        break;
                    case BR.currentLocation:
                        setCurrentLocation();
                        break;
                    case BR.routeDescription:
                        setRouteDescription();
                        break;
                }
            }
        });
    }

    private void setCurrentLocation() {
        if (_googleMap != null) {
            if (_currentLoctionMarker == null) {
                final MarkerOptions markerOptions = new MarkerOptions()
                        .position(_model.getCurrentLocation());
                _currentLoctionMarker = _googleMap.addMarker(markerOptions);
                final CircleOptions center = new CircleOptions()
                        .center(_model.getCurrentLocation())
                        .radius(getResources().getDimension(R.dimen.map_circle_radius))
                        .fillColor(ContextCompat.getColor(getActivity(), R.color.colorCurrent))
                        .strokeColor(ContextCompat.getColor(getActivity(), R.color.colorCurrentEdge))
                        .strokeWidth(getResources().getDimension(R.dimen.map_circle_edge));
                _currentLocationCircle = _googleMap.addCircle(center);
            } else {
                _currentLocationCircle.setCenter(_model.getCurrentLocation());
            }
        }
    }

    private void setRouteDescription() {
        showColouredSnackBar(_model.getRouteDescription(), R.color.colorDirectionsText, R.color.colorDirectionsBackground, DIRECTIONS_SNACKBAR_LENGTH);
    }

    private void updateMapLocation() {
        if (_googleMap != null && _model != null) {
            _googleMap.addMarker(new MarkerOptions().position(_model.getTargetLocation()).title(_model.getName()));
            final CircleOptions options = new CircleOptions()
                    .center(_model.getTargetLocation())
                    .radius(getResources().getDimension(R.dimen.map_circle_radius))
                    .fillColor(ContextCompat.getColor(getActivity(), R.color.colorTo))
                    .strokeColor(ContextCompat.getColor(getActivity(), R.color.colorToEdge))
                    .strokeWidth(getResources().getDimension(R.dimen.map_circle_edge));
            _googleMap.addCircle(options);
            // the animate seem to make the mapView work
            final CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(_model.getTargetLocation(), 16);
            _googleMap.animateCamera(cameraUpdate);
        }
    }

    private void setRoute() {
        if (_googleMap != null && _model != null) {
            _googleMap.addPolyline(_model.getRoute());
        }
    }

    private void setRouteBounds() {
        if (_googleMap != null && _model != null) {
            final CameraUpdate cameraUpdate =
                    CameraUpdateFactory.newLatLngBounds(_model.getRouteBounds(),
                            (int) getResources().getDimension(R.dimen.map_route_padding));
            _googleMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void showError(final String message) {
        showColouredSnackBar(message, R.color.colorErrorText, R.color.colorErrorBackground, ERROR_SNACKBAR_LENGTH);
    }

    private void showColouredSnackBar(
            final String message,
            @ColorRes final int colorRes,
            @ColorRes final int bgColorRes, final int length) {
        final int color = ContextCompat.getColor(getActivity(), colorRes);
        final int bgColor = ContextCompat.getColor(getActivity(), bgColorRes);
        final Snackbar make = Snackbar.make(_binding.container, message, length);//.show();
        final View snackBarView = make.getView();
        snackBarView.setBackgroundColor(bgColor);
        final TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        make.show();
    }

    @Override
    @AskPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onMapReady(final GoogleMap googleMap) throws SecurityException {
        _googleMap = googleMap;
        _binding.map.onResume();
        _googleMap.getUiSettings().setAllGesturesEnabled(true);
        _googleMap.setMyLocationEnabled(false);
//        _googleMap.getUiSettings().setMapToolbarEnabled(false);
//        _googleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
//        _googleMap.getUiSettings().setTiltGesturesEnabled(false);
//        _googleMap.getUiSettings().setRotateGesturesEnabled(false);
//        _googleMap.getUiSettings().setZoomControlsEnabled(true);

        _googleMap.setOnMapLoadedCallback(() -> {
            //Snackbar.make(binding.container, "map loaded", Snackbar.LENGTH_LONG).show();
            _binding.map.invalidate();
        });
    }
}
