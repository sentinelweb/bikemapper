package co.uk.sentinelweb.bikemapper.sensors

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import rx.Observable
import rx.Subscriber
import rx.functions.Action0
import rx.observables.ConnectableObservable
import rx.subscriptions.Subscriptions

/**
 * NOT USED (just a kotlin test)
 * Created by robert on 03/11/2016.
 */
class LocationHandlerKt {

    var localtionManager: LocationManager
    val locationObservable: ConnectableObservable<Location> = Observable.create(object : Observable.OnSubscribe<Location> {

        override fun call(subscriber: Subscriber<in Location>) {
            val locationListener: LocationListener = object : LocationListener {
                var connected: Boolean = false;
                override fun onProviderDisabled(provider: String?) {

                }

                override fun onProviderEnabled(provider: String?) {

                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    connected = status == LocationProvider.AVAILABLE
                }

                override fun onLocationChanged(location: Location?) {
                    subscriber.onNext(location)
                }
            }

            start(locationListener);

            subscriber.add(Subscriptions.create(object : Action0 {

                override fun call() {
                    stop(locationListener)
                }

            }))
        }
    }).publish().refCount() as ConnectableObservable<Location>

    constructor(c: Context) {
        val systemService = c.getSystemService(Context.LOCATION_SERVICE)
        this.localtionManager = systemService as LocationManager
    }

    fun start(locationListener: LocationListener, minTime: Long = 10000, minDist: Float = 200.0f/*m*/) {
        localtionManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist, locationListener)
    }

    fun stop(locationListener: LocationListener) {
        localtionManager.removeUpdates(locationListener)
    }

}