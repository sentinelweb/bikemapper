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

import java.util.List;

import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.databinding.FragmentLocationEditBinding;
import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.util.converter.LocationConverter;

/**
 * A fragment to edit a location
 * <p>
 */
public class LocationEditFragment extends Fragment implements LocationEditContract.View, OnMapReadyCallback {

    public static final String ARG_ID = "id";

    FragmentLocationEditBinding binding;

    LocationEditViewModel _viewModel;

    private LocationEditContract.Presenter _presenter;
    LocationConverter _locationConverter;

    private GoogleMap _googleMap;
    private SearchPlaceSuggestionsAdapter _suggestionsAdapter;

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
        _locationConverter = new LocationConverter();// TODO remove

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
        _suggestionsAdapter = new SearchPlaceSuggestionsAdapter(getActivity());
        searchView.setSuggestionsAdapter(_suggestionsAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(final int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(final int position) {
                _presenter.onPlaceSelected(_suggestionsAdapter.getPlace(position));

                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText != null && !"".equals(newText)) {
                    _presenter.searchPlaces(newText);
                }

                return true;
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
        this._presenter.unsubscribe();
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
        _viewModel = new LocationEditViewModel(location, _locationConverter);
        binding.setLocation(_viewModel);
        updateMapLocation();
    }

    @Override
    public void setPlaces(final List<Place> places) {
        _suggestionsAdapter.setPlaces(places);
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
