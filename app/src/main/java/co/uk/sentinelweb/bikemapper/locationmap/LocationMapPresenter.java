package co.uk.sentinelweb.bikemapper.locationmap;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BasePresenter;
import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.data.LocationsRepository;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationMapPresenter implements BasePresenter {

    private final Context _context;
    @Inject
    protected LocationsRepository _locationsRepository;

    private final LocationMapView _view;
    private final Long _locationId;

    LocationMapPresenter(@Nullable final Long locationId, final Context c, final LocationMapView view) {
        _context = c;
        ((BikeApplication) _context.getApplicationContext()).getComponent().inject(this);
        _locationId = locationId;
        _view = view;
    }

    @Override
    public void subscribe() {
        loadLocation();
    }

    private void loadLocation() {
        _view.setLoadingIndicator(true);
        _locationsRepository.getLocation(_locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Location>() {
                    @Override
                    public void onCompleted() {
                        _view.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showLoadingError(e.getMessage());
                    }

                    @Override
                    public void onNext(final Location locations) {
                        _view.setLocation(locations);
                    }
                });
    }

    @Override
    public void unsubscribe() {

    }
}
