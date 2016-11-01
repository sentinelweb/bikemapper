package co.uk.sentinelweb.bikemapper.locationedit;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import co.uk.sentinelweb.bikemapper.net.interactor.IPlaceApiInteractor;
import co.uk.sentinelweb.bikemapper.network.api.GoogleMapsApiKeyProvider;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationEditPresenter implements LocationEditContract.Presenter {

    @Inject
    protected ILocationsRepository _lcationsRepository;

    @Inject
    protected IPlaceApiInteractor _placeApiInteractor;

    private final LocationEditContract.View _view;
    private final Long _locationId;
    private Subscription _subscription;

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


    LocationEditPresenter(@Nullable final Long locationId, final Context c, final LocationEditContract.View view) {
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
        _subscription = _lcationsRepository.getLocation(_locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_observer);
    }

    @Override
    public void unsubscribe() {
        _subscription.unsubscribe();
    }

    // TODO background
    public void searchPlaces(final String text) {
        _placeApiInteractor.getPlaces(text, new GoogleMapsApiKeyProvider());
    }
}
