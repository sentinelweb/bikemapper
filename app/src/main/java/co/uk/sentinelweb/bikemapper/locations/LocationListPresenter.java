package co.uk.sentinelweb.bikemapper.locations;

import android.content.Context;

import java.util.List;

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
public class LocationListPresenter implements BasePresenter {

    @Inject
    protected LocationsRepository _locationsRepository;

    private final LocationListView _view;

    LocationListPresenter(final Context c, final LocationListView view) {
        ((BikeApplication) c.getApplicationContext()).getComponent().inject(this);
        _view = view;
    }

    @Override
    public void subscribe() {
        _locationsRepository.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Observer<List<Location>>() {
                    @Override
                    public void onCompleted() {
                        _view.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showLoadingTasksError();
                    }

                    @Override
                    public void onNext(final List<Location> locations) {
                        _view.setLocations(locations);
                    }
                });
    }

    @Override
    public void unsubscribe() {

    }
}
