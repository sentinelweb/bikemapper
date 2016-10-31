package co.uk.sentinelweb.bikemapper.template;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationTmplPresenter implements MVPContract.BasePresenter {

    @Inject
    protected ILocationsRepository _ILocationsRepository;

    private final LocationTmplView _view;
    private final Long _locationId;

    LocationTmplPresenter(@Nullable final Long locationId, final Context c, final LocationTmplView view) {
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
        _ILocationsRepository.getLocation(_locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SavedLocation>() {
                    @Override
                    public void onCompleted() {
                        _view.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showLoadingError(e.getMessage());
                    }

                    @Override
                    public void onNext(final SavedLocation locations) {
                        _view.setLocation(locations);
                    }
                });
    }

    @Override
    public void unsubscribe() {

    }
}
