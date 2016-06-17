package co.uk.sentinelweb.bikemapper.net.services;

import co.uk.sentinelweb.bikemapper.net.response.google.GoogleMapsDirectionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service for google maps directions:
 * https://developers.google.com/maps/documentation/directions/intro
 * <p>
 * testurls: key is browser key use android key
 * https://maps.googleapis.com/maps/api/directions/json?origin=51.525493,-0.0822173&destination=51.5202109,-0.1070064&key=AIzaSyCBN95GAgbRr650S2eL_B8KqlaQP7FlXpM&mode=bicycling
 * <p>
 * <p>
 * Created by robert on 16/06/16.
 */
public interface GoogleMapsDirectionsService {

    @GET("json")
    Call<GoogleMapsDirectionsResponse> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key, @Query(value = "mode") String mode);
}
