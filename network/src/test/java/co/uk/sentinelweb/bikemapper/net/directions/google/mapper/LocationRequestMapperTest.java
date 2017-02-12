package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import org.junit.Before;
import org.junit.Test;

import co.uk.sentinelweb.bikemapper.domain.model.Location;

import static org.junit.Assert.assertEquals;

/**
 * Created by robert on 12/02/2017.
 */
public class LocationRequestMapperTest {
    LocationRequestMapper _locationRequestMapper;

    @Before
    public void setUp() throws Exception {
        _locationRequestMapper = new LocationRequestMapper();

    }

    @Test
    public void map() throws Exception {
        final Location l = new Location(51.34245, 45.56456);
        final String s = _locationRequestMapper.map(l);
        assertEquals(s, "51.34245,45.56456");
    }

}