package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import co.uk.sentinelweb.bikemapper.net.services.MapQuestDirectionsService;

/**
 * Created by robert on 17/06/16.
 */
public class MapQuestDirectionsClient extends BaseClient {
    public static final String BASE_URL = "http://open.mapquestapi.com/directions/v2/route/v2/";

    MapQuestDirectionsService service;

    public MapQuestDirectionsClient(final IApiKeyProvider keyProvider) {
        super(keyProvider);
        service = (MapQuestDirectionsService) createClient(BASE_URL, MapQuestDirectionsService.class);
    }


}
