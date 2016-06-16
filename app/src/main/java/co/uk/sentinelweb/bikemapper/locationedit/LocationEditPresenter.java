package co.uk.sentinelweb.bikemapper.locationedit;

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
public class LocationEditPresenter implements BasePresenter {

    @Inject
    protected LocationsRepository _locationsRepository;

    private final LocationEditView _view;
    private final Long _locationId;

    LocationEditPresenter(@Nullable final Long locationId, final Context c, final LocationEditView view) {
        ((BikeApplication) c.getApplicationContext()).getComponent().inject(this);
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
