package co.uk.sentinelweb.bikemapper.net.services;

import co.uk.sentinelweb.bikemapper.net.response.mapquest.MapQuestDirectionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service for mapquest directions
 * <p>
 * http://open.mapquestapi.com/directions/
 * <p>
 * testurl
 * http://open.mapquestapi.com/directions/v2/route?key=FMRPx9Oy4ICRoMulfDFvtUvfJiSIbb4K&ambiguities=ignore&from=51.525493,-0.0822173&to=51.5202109,-0.1070064&outFormat=json&routeType=bicycle
 */
public interface MapQuestDirectionsService {

    @GET("route?outFormat=json&ambiguities=ignore")
    Call<MapQuestDirectionsResponse> getDirections(@Query("from") String origin, @Query("to") String destination, @Query("key") String key, @Query(value = "routeType") String mode);

}