package co.uk.sentinelweb.bikemapper.locationmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BaseActivity;
import co.uk.sentinelweb.bikemapper.R;

public class LocationMapActivity extends BaseActivity {

    private static final String ARG_ID = "id";

    public static final String LOCATION_MAP_FRAGMENT_TAG = "locationMapFragment";

    private LocationMapFragment _locationMapFragment;

    @Inject
    protected LocationMapContract.Presenter _locationMapPresenter;

    @Inject
    protected LocationMapContract.View _locationMapView;

    public static Intent getLaunchIntent(final Context c, final Long locationId) {
        final Intent i = new Intent(c, LocationMapActivity.class);
        i.putExtra(ARG_ID, locationId);
        return i;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Long id = getIntent().getLongExtra(ARG_ID, -1);

        _locationMapFragment =
                (LocationMapFragment) getSupportFragmentManager().findFragmentByTag(LOCATION_MAP_FRAGMENT_TAG);
        if (_locationMapFragment == null) {
            // Create the fragment
            _locationMapFragment = LocationMapFragment.newInstance(id);
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, _locationMapFragment, LOCATION_MAP_FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else if (!_locationMapFragment.isAdded()) {
            final Bundle args = new Bundle();
            args.putLong(LocationMapFragment.ARG_ID, id);
            _locationMapFragment.setArguments(args);
        }
        // BikeApplicationComponent.Injector.getComponent(this).inject(this);
        // builds.injects dependencies
        new LocationMapComponent.Injector().createComponent(this, _locationMapFragment)
                .inject(this);
        _locationMapView.setPresenter(_locationMapPresenter);
        _locationMapPresenter.setData(id);
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
    protected void onDestroy() {
        super.onDestroy();
    }


}
