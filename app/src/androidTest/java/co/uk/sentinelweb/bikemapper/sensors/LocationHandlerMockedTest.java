package co.uk.sentinelweb.bikemapper.sensors;

import android.location.LocationListener;
import android.location.LocationManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import rx.Observable;
import rx.Observer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class LocationHandlerMockedTest {
    LocationHandler _sut;

    //@Mock
    LocationManager mockLocationManager;

    //@Mock
    Observer<Location> mockObserver1;

    //@Mock
    Observer<Location> mockObserver2;

    //@Captor
    //ArgumentCaptor<LocationListener> _listenerArgumentCaptor;

    //@Captor
    //ArgumentCaptor<Location> _locatiomnArgumentCaptor;

    @Before
    public void setUp() throws Exception {
//        System.setProperty("dexmaker.dexcache", InstrumentationRegistry.getTargetContext().getCacheDir().getPath());
//        MockitoAnnotations.initMocks(this);// Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()' on a null object reference
//        _listenerArgumentCaptor = ArgumentCaptor.forClass(LocationListener.class);
//        _locatiomnArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        mockLocationManager = mock(LocationManager.class);
        mockObserver1 = (Observer<Location>) mock(Observer.class);
        mockObserver2 = (Observer<Location>) mock(Observer.class);
        _sut = new LocationHandler(mockLocationManager);
    }

    @Test
    public void testObservable() {
        final ArgumentCaptor<LocationListener> _listenerArgumentCaptor = ArgumentCaptor.forClass(LocationListener.class);
        final ArgumentCaptor<Location> _locatiomnArgumentCaptor = ArgumentCaptor.forClass(Location.class);

        final Observable<Location> observable = _sut.getObservable();

        final LocationListener listener = _listenerArgumentCaptor.getValue();
        final Observable<Location> locationObservable = observable.publish().refCount();

        locationObservable.subscribe(mockObserver1);
        locationObservable.subscribe(mockObserver2);

        verify(mockLocationManager).requestLocationUpdates(eq(LocationManager.GPS_PROVIDER), anyLong(), anyFloat(), _listenerArgumentCaptor.capture());

        final android.location.Location location = new android.location.Location("??");
        location.setLatitude(1);
        location.setLongitude(2);

        listener.onLocationChanged(location);

        verify(mockObserver1).onNext(_locatiomnArgumentCaptor.capture());
        final Location actual1 = _locatiomnArgumentCaptor.getValue();
        assertEquals(location.getLatitude(), actual1.getLatitude());
        assertEquals(location.getLongitude(), actual1.getLongitude());
    }

}