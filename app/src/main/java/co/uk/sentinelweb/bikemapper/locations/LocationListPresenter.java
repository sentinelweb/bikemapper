package co.uk.sentinelweb.bikemapper.locations;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by robert on 14/06/16.
 */
public class LocationListPresenter implements MVPContract.BasePresenter {

    @Inject
    protected ILocationsRepository _locationsRepository;

    private final LocationListView _view;
    private Subscription _subscription;
    private final Observer<List<SavedLocation>> _observer = new Observer<List<SavedLocation>>() {
        @Override
        public void onCompleted() {
            _view.setLoadingIndicator(false);
        }

        @Override
        public void onError(final Throwable e) {
            _view.showLoadingError(e.getMessage());
        }

        @Override
        public void onNext(final List<SavedLocation> locations) {
            _view.setLocations(locations);
        }
    };

    LocationListPresenter(final Context c, final LocationListView view) {
        ((BikeApplication) c.getApplicationContext()).getComponent().inject(this);
        _view = view;
    }

    @Override
    public void subscribe() {
        _view.setLoadingIndicator(true);
        _subscription = _locationsRepository.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(_observer);
    }

    @Override
    public void unsubscribe() {
        _subscription.unsubscribe();
    }
}
