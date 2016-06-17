package co.uk.sentinelweb.bikemapper.locations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import co.uk.sentinelweb.bikemapper.BaseActivity;
import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.locationedit.LocationEditActivity;
import co.uk.sentinelweb.bikemapper.locationmap.LocationMapActivity;
import co.uk.sentinelweb.bikemapper.util.ViewServer;

public class LocationListActivity extends BaseActivity implements
        LocationListFragment.OnInteractionListener,
        BaseActivity.FabButtonAction {

    public static final String LOCATION_LIST_FRAGMENT_TAG = "locationListFragment";
    private LocationListFragment _locationListFragment;
    private LocationListPresenter _locationListPresenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _locationListFragment =
                (LocationListFragment) getSupportFragmentManager().findFragmentByTag(LOCATION_LIST_FRAGMENT_TAG);
        if (_locationListFragment == null) {
            // Create the fragment
            _locationListFragment = LocationListFragment.newInstance(null);
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, _locationListFragment, LOCATION_LIST_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }

        _locationListPresenter = new LocationListPresenter(this, _locationListFragment);
        _locationListFragment.setPresenter(_locationListPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        ViewServer.get(this).removeWindow(this);
        super.onStop();
    }

    @Override
    public void onListItemClick(final Location item) {
        final Intent launchIntent = LocationEditActivity.getLaunchIntent(this, item.getId());
        startActivity(launchIntent);
    }

    @Override
    public void onListBikeClick(final Location item) {
        final Intent launchIntent = LocationMapActivity.getLaunchIntent(this, item.getId());
        startActivity(launchIntent);
    }

    @Override
    public void onFabClick() {

    }
}
