package co.uk.sentinelweb.bikemapper.net.services;

import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.places.GoogleMapsPlacesTextResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service for google maps directions:<br/>
 * https://developers.google.com/maps/documentation/directions/intro<br/>
 * <br/>
 * testurls: key is browser key use android key<br/>
 * https://maps.googleapis.com/maps/api/directions/json?origin=51.525493,-0.0822173&destination=51.5202109,-0.1070064&key=AIzaSyCBN95GAgbRr650S2eL_B8KqlaQP7FlXpM&mode=bicycling<br/>
 * <br/>
 * <br/>
 * Places Api :<br/>
 * Info: https://developers.google.com/places/web-service/search <br/>
 * Example: https://maps.googleapis.com/maps/api/place/textsearch/json?query=pitfield+st&key=<br/>
 * <p>
 * TODO convert to return rx.observable
 * Created by robert on 16/06/16.<br/>
 */
public interface GoogleMapsService {

    @GET("directions/json")
    Call<GoogleMapsDirectionsResponse> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key, @Query(value = "mode") String mode);

    @GET("place/textsearch/json")
    Call<GoogleMapsPlacesTextResponse> getTextPlaces(@Query("query") String query, @Query("key") String key);


}
