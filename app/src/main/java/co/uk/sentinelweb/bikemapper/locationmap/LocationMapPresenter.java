package co.uk.sentinelweb.bikemapper.locationmap;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.domain.model.TravelMode;
import co.uk.sentinelweb.bikemapper.net.directions.google.interactor.IDirectionsInteractor;
import co.uk.sentinelweb.bikemapper.sensors.LocationHandler;
import co.uk.sentinelweb.bikemapper.util.context.IWrappedContext;
import co.uk.sentinelweb.bikemapper.util.rx.ISchedulers;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by robert on 14/06/16.
 */
public class LocationMapPresenter implements LocationMapContract.Presenter {


    private final ILocationsRepository _locationsRepository;
    private final LocationHandler _locationHandler;
    private final ISchedulers _schedulers;
    private final IDirectionsInteractor _directionsInteractor;
    private final LocationMapModelAdapter _locationMapModelAdapter;
    private final IWrappedContext _wrappedContext;

    private final LocationMapContract.View _view;
    private final LocationMapModel _model;
    private Subscription _loadSubscription;
    private Subscription _locationSubscription;

    private SavedLocation _target;
    private Long _locationId;

    @Inject
    LocationMapPresenter(final LocationMapContract.View view,
                         final ILocationsRepository locationsRepository,
                         final LocationHandler locationHandler,
                         final ISchedulers schedulers,
                         final IDirectionsInteractor directionsInteractor,
                         final LocationMapModelAdapter locationMapModelAdapter,
                         final IWrappedContext wrappedContext) {
        _locationsRepository = locationsRepository;
        _locationHandler = locationHandler;
        _schedulers = schedulers;
        _view = view;
        _directionsInteractor = directionsInteractor;
        _locationMapModelAdapter = locationMapModelAdapter;
        _wrappedContext = wrappedContext;

        _model = new LocationMapModel();
        _locationMapModelAdapter.setModel(_model);
    }

    @Override
    public void setData(final Long id) {
        _locationId = id;
    }

    @Override
    public void subscribe() {
        _view.setModel(_model);
        loadLocation();
        startLocationListening();
    }

    @Override
    public void unsubscribe() {
        _loadSubscription.unsubscribe();
        _locationSubscription.unsubscribe();
    }

    private void startLocationListening() {
        _locationSubscription = _locationHandler.getObservable()
                // TODO tset this
//                .subscribeOn(_schedulers.io())
//                .observeOn(_schedulers.main())
                .subscribe(new Subscriber<Location>() {
                    boolean firstTime = true;

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showError(e.getMessage());
                        Log.d(LocationMapPresenter.class.getSimpleName(), "Error getting current location", e);
                    }

                    @Override
                    public void onNext(final Location location) {
                        _locationMapModelAdapter.setCurrent(location);
                        if (firstTime) {
                            loadRoute(location);
                            firstTime = false;
                        }
                    }
                });
    }

    private void loadLocation() {
        _model.setLoadingVisible(true);
        _loadSubscription = _locationsRepository.getLocation(_locationId)
                .subscribeOn(_schedulers.io())
                .observeOn(_schedulers.main())
                .subscribe(new Observer<SavedLocation>() {
                    @Override
                    public void onCompleted() {
                        _model.setLoadingVisible(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showError(e.getMessage());
                        Log.d(LocationMapPresenter.class.getSimpleName(), "Error loading location", e);
                    }

                    @Override
                    public void onNext(final SavedLocation location) {
                        _target = location;
                        _locationMapModelAdapter.setTarget(_target.getLocation());

                    }
                });
    }

    private void loadRoute(final Location start) {
        _model.setLoadingVisible(true);
        _loadSubscription = _directionsInteractor.getDirections(start, _target.getLocation(), TravelMode.BICYCLING)
                .subscribeOn(_schedulers.io())
                .observeOn(_schedulers.main())
                .subscribe(new Observer<List<Route>>() {
                    @Override
                    public void onCompleted() {
                        _model.setLoadingVisible(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        _view.showError(e.getMessage());
                        Log.d(LocationMapPresenter.class.getSimpleName(), "Error getting directions", e);
                    }

                    @Override
                    public void onNext(final List<Route> routes) {
                        if (routes!=null && routes.size() > 0) {
                            _locationMapModelAdapter.setRoute(routes.get(0), _wrappedContext.getColor(R.color.colorRoute));
                        }
                    }
                });
    }


}
