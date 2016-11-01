package co.uk.sentinelweb.bikemapper.locationedit;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.databinding.FragmentLocationEditBinding;
import rx.Observable;

/**
 * A fragment to edit a location
 * <p>
 */
public class LocationEditFragment extends Fragment implements LocationEditContract.View, OnMapReadyCallback {

    public static final String ARG_ID = "id";

    FragmentLocationEditBinding binding;

    LocationEditViewModel _viewModel;

    private LocationEditContract.Presenter _presenter;

    private Observable<CharSequence> _editNameObservable;

    private GoogleMap _googleMap;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationEditFragment() {
        setHasOptionsMenu(true);
    }

    public static LocationEditFragment newInstance(final Long id) {
        final LocationEditFragment fragment = new LocationEditFragment();
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
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_location, menu);
        // Associate searchable configuration with the SearchView
        final SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        //searchView.setSuggestionsAdapter();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                _presenter.searchPlaces(newText);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = FragmentLocationEditBinding.inflate(inflater, container, false);
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
    public void setPresenter(final LocationEditContract.Presenter presenter) {
        this._presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean loading) {
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLocation(final SavedLocation location) {
        _viewModel = new LocationEditViewModel(location);
        binding.setLocation(_viewModel);
        updateMapLocation();
    }

    private void updateMapLocation() {
        if (_googleMap != null && _viewModel != null) {
            _googleMap.addMarker(new MarkerOptions().position(_viewModel.getLatLng()).title(_viewModel.getName()));
            _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(_viewModel.getLatLng(), 16));
        }
    }

    @Override
    public void showLoadingError(final String message) {
        Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        _googleMap = googleMap;
        _googleMap.getUiSettings().setRotateGesturesEnabled(false);
        _googleMap.getUiSettings().setCompassEnabled(false);
        _googleMap.getUiSettings().setMapToolbarEnabled(false);
        _googleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        _googleMap.getUiSettings().setTiltGesturesEnabled(false);
        _googleMap.getUiSettings().setMapToolbarEnabled(false);
        _googleMap.getUiSettings().setRotateGesturesEnabled(false);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        updateMapLocation();
    }


}
