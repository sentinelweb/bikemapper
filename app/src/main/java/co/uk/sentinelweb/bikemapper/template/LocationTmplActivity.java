package co.uk.sentinelweb.bikemapper.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import co.uk.sentinelweb.bikemapper.BaseActivity;
import co.uk.sentinelweb.bikemapper.R;

public class LocationTmplActivity extends BaseActivity {

    private static final String ARG_ID = "id";

    public static final String LOCATION_EDIT_FRAGMENT_TAG = "locationEditFragment";
    private LocationTmplFragment _locationTmplFragment;
    private LocationTmplPresenter _locationTmplPresenter;

    public static Intent getLaunchIntent(final Context c, final Long locationId) {
        final Intent i = new Intent(c, LocationTmplActivity.class);
        i.putExtra(ARG_ID, locationId);
        return i;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Long id = getIntent().getLongExtra(ARG_ID, -1);

        _locationTmplFragment =
                (LocationTmplFragment) getSupportFragmentManager().findFragmentByTag(LOCATION_EDIT_FRAGMENT_TAG);
        if (_locationTmplFragment == null) {
            // Create the fragment
            _locationTmplFragment = LocationTmplFragment.newInstance(id);
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, _locationTmplFragment, LOCATION_EDIT_FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else {
            final Bundle args = new Bundle();
            args.putLong(LocationTmplFragment.ARG_ID, id);
            _locationTmplFragment.setArguments(args);
        }
        _locationTmplPresenter = new LocationTmplPresenter(id, this, _locationTmplFragment);
        _locationTmplFragment.setPresenter(_locationTmplPresenter);

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
