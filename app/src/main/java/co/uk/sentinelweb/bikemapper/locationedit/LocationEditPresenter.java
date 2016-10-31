package co.uk.sentinelweb.bikemapper.locationedit;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationEditPresenter implements MVPContract.BasePresenter {

    @Inject
    protected ILocationsRepository _ILocationsRepository;

    private final LocationEditView _view;
    private final Long _locationId;

    private final Observer<SavedLocation> _observer = new Observer<SavedLocation>() {
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
    };
    private Subscription _subscription;

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
        _subscription = _ILocationsRepository.getLocation(_locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_observer);
    }

    @Override
    public void unsubscribe() {
        _subscription.unsubscribe();
    }
}
