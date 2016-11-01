package co.uk.sentinelweb.bikemapper.locationedit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import co.uk.sentinelweb.bikemapper.BaseActivity;
import co.uk.sentinelweb.bikemapper.R;

public class LocationEditActivity extends BaseActivity {

    private static final String ARG_ID = "id";

    public static final String LOCATION_EDIT_FRAGMENT_TAG = "locationEditFragment";
    private LocationEditFragment _locationEditFragment;
    private LocationEditPresenter _locationEditPresenter;

    public static Intent getLaunchIntent(final Context c, final Long locationId) {
        final Intent i = new Intent(c, LocationEditActivity.class);
        i.putExtra(ARG_ID, locationId);
        return i;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Long id = getIntent().getLongExtra(ARG_ID, -1);

        _locationEditFragment =
                (LocationEditFragment) getSupportFragmentManager().findFragmentByTag(LOCATION_EDIT_FRAGMENT_TAG);
        if (_locationEditFragment == null) {
            // Create the fragment
            _locationEditFragment = LocationEditFragment.newInstance(id);
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, _locationEditFragment, LOCATION_EDIT_FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else {
            final Bundle args = new Bundle();
            args.putLong(LocationEditFragment.ARG_ID, id);
            _locationEditFragment.setArguments(args);
        }
        _locationEditPresenter = new LocationEditPresenter(id, this, _locationEditFragment);
        _locationEditFragment.setPresenter(_locationEditPresenter);

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
