package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import co.uk.sentinelweb.bikemapper.net.services.GoogleMapsDirectionsService;

/**
 * Created by robert on 17/06/16.
 */
public class GoogleMapsDirectionsClient extends BaseClient {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";

    GoogleMapsDirectionsService service;

    public GoogleMapsDirectionsClient(final IApiKeyProvider keyProvider) {
        super(keyProvider);
        service = (GoogleMapsDirectionsService) createClient(BASE_URL, GoogleMapsDirectionsService.class);
    }


}
