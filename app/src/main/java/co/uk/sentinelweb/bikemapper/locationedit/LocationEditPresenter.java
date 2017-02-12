package co.uk.sentinelweb.bikemapper.locationedit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import co.uk.sentinelweb.bikemapper.net.place.google.interactor.IPlaceApiInteractor;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationEditPresenter implements LocationEditContract.Presenter {

    @Inject
    protected ILocationsRepository _locationsRepository;

    @Inject
    protected IPlaceApiInteractor _placeApiInteractor;

    private final LocationEditContract.View _view;
    private final Long _locationId;
    private Subscription _subscription;

    private SavedLocation _location;

    private final Observer<SavedLocation> _loadLocationObserver = new Observer<SavedLocation>() {
        @Override
        public void onCompleted() {
            _view.setLoadingIndicator(false);
        }

        @Override
        public void onError(final Throwable e) {
            _view.showLoadingError(e.getMessage());
        }

        @Override
        public void onNext(final SavedLocation location) {
            _location = location;
            _view.setLocation(_location);
        }
    };

    private final Observer<List<Place>> _loadPlacesObserver = new Observer<List<Place>>() {
        @Override
        public void onCompleted() {
            //_view.setLoadingIndicator(false);
        }

        @Override
        public void onError(final Throwable e) {
            Log.d(LocationEditPresenter.class.getSimpleName(), "Error getting places", e);
            _view.showLoadingError(e.getMessage());
        }

        @Override
        public void onNext(final List<Place> places) {
            _view.setPlaces(places);
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
        _subscription = _locationsRepository.getLocation(_locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_loadLocationObserver);
    }

    @Override
    public void unsubscribe() {
        _subscription.unsubscribe();
    }

    public void searchPlaces(final String text) {
        _placeApiInteractor
                .getPlaces(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_loadPlacesObserver);
    }

    @Override
    public void onPlaceSelected(final Place place) {
        _location.setLocation(place.getLocation());
        _location.setName(place.getName());
        _location.setAddress(null);
        _locationsRepository.saveLocation(_location);
        _view.setLocation(_location);
    }
}
