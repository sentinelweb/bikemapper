package co.uk.sentinelweb.bikemapper.locations;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.Location;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnInteractionListener}
 * interface.
 */
public class LocationListFragment extends Fragment implements LocationListView {

    private static final String ARG_SCENARIO = "scenario";

    @Bind(R.id.list)
    protected RecyclerView _recyclerView;
    @Bind(R.id.progress)
    protected ProgressBar _progress;
    @Bind(R.id.container)
    protected RelativeLayout _container;

    private String scenario;

    private OnInteractionListener _listener;
    private LocationRecyclerViewAdapter _adapter;
    private LocationListPresenter _presenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationListFragment() {
    }

    public static LocationListFragment newInstance(final String scenario) {
        final LocationListFragment fragment = new LocationListFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_SCENARIO, scenario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            scenario = getArguments().getString(ARG_SCENARIO);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        _container = (RelativeLayout) inflater.inflate(R.layout.fragment_location_list, container, false);
        ButterKnife.bind(this, _container);
        // Set the adapter
        if (_recyclerView instanceof RecyclerView) {
            final Context context = _recyclerView.getContext();
            _recyclerView.setLayoutManager(new LinearLayoutManager(context));
            _adapter = new LocationRecyclerViewAdapter(new ArrayList<>(), _listener);
            _recyclerView.setAdapter(_adapter);
        }
        return _container;
    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            _listener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
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
    public void setPresenter(final LocationListPresenter presenter) {
        this._presenter = presenter;

    }

    @Override
    public void setLoadingIndicator(final boolean loading) {
        _progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLocations(final List<Location> locations) {
        _adapter.setItems(locations);
    }

    @Override
    public void showLoadingError(final String message) {
        Snackbar.make(_recyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    public interface OnInteractionListener {
        void onListItemClick(Location item);

        void onListBikeClick(Location item);
    }
}
