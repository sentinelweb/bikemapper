package co.uk.sentinelweb.bikemapper.locationmap;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canelmas.let.AskPermission;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.databinding.FragmentLocationMapBinding;

/**
 * A fragment to edit a location
 * <p>
 */
public class LocationMapFragment extends Fragment implements LocationMapView, OnMapReadyCallback {

    public static final String ARG_ID = "id";

    FragmentLocationMapBinding binding;

    LocationMapViewModel _viewModel;

    private LocationMapPresenter _presenter;

    private GoogleMap _googleMap;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationMapFragment() {
    }

    public static LocationMapFragment newInstance(final Long id) {
        final LocationMapFragment fragment = new LocationMapFragment();
        final Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = FragmentLocationMapBinding.inflate(inflater, container, false);
        binding.map.onCreate(null);
        binding.map.getMapAsync(this);
        return binding.container;
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
    }

    @Override
    public void onPause() {
        binding.map.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        binding.map.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        binding.map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setPresenter(final LocationMapPresenter presenter) {
        this._presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean loading) {
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLocation(final SavedLocation location) {
        _viewModel = new LocationMapViewModel(location);
        binding.setLocation(_viewModel);
        updateMapLocation();
    }

    private void updateMapLocation() {
        if (_googleMap != null && _viewModel != null) {
            _googleMap.addMarker(new MarkerOptions().position(_viewModel.getLatLng()).title(_viewModel.getName()));
            //_googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(_viewModel.getLatLng(), 16));
            // the animate seem to make the mapView work
            final CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(_viewModel.getLatLng(), 16);
            _googleMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void showLoadingError(final String message) {
        Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    @AskPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onMapReady(final GoogleMap googleMap) throws SecurityException {
        _googleMap = googleMap;
        binding.map.onResume();
        _googleMap.getUiSettings().setAllGesturesEnabled(true);
        _googleMap.setMyLocationEnabled(false);
//        _googleMap.getUiSettings().setMapToolbarEnabled(false);
//        _googleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
//        _googleMap.getUiSettings().setTiltGesturesEnabled(false);
//        _googleMap.getUiSettings().setRotateGesturesEnabled(false);
//        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        updateMapLocation();
        _googleMap.setOnMapLoadedCallback(() -> {
            Snackbar.make(binding.container, "map loaded", Snackbar.LENGTH_LONG).show();
            binding.map.invalidate();
        });
    }
}
