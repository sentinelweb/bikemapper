package co.uk.sentinelweb.bikemapper.data;

import java.util.Arrays;
import java.util.List;

import co.uk.sentinelweb.bikemapper.core.model.Location;

/**
 * Created by robert on 15/06/16.
 */
public class MockLocations {
    public static List<Location> getMockLocations() {
        return Arrays.asList(
                new Location(1, "Home", 51.525493, -0.0822173),
                new Location(2, "Farringdon station", 51.5202109, -0.1070064),
                new Location(3, "London bridge station", 51.5045761, -0.0882424),
                new Location(4, "Monument station", 51.5096179, -0.0860752),
                new Location(5, "Ledenhall Market", 51.511374, -0.0848521),
                new Location(6, "Fournier st", 51.519309, -0.071881),
                new Location(7, "Boundary Gardens", 51.526011, -0.075089),
                new Location(8, "Hackney city farm", 51.5309206, -0.0718702)
        );
    }

}
