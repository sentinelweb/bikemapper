package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import org.junit.Assert;
import org.junit.Test;

import co.uk.sentinelweb.bikemapper.domain.model.DistanceUnit;

/**
 * Created by robert on 12/02/2017.
 */
public class DistanceUnitMapperTest {
    DistanceUnitMapper _mapper = new DistanceUnitMapper();

    @Test
    public void map() throws Exception {
        Assert.assertEquals(_mapper.map("28 m"), DistanceUnit.M);
        Assert.assertEquals(_mapper.map("28 foot"), DistanceUnit.FOOT);
        Assert.assertEquals(_mapper.map("28 km"), DistanceUnit.KM);
        Assert.assertEquals(_mapper.map("28 mile"), DistanceUnit.MILE);
        Assert.assertEquals(_mapper.map("28m"), DistanceUnit.UNKNOWN);
        Assert.assertEquals(_mapper.map("28 f"), DistanceUnit.UNKNOWN);
        Assert.assertEquals(_mapper.map("28 feet"), DistanceUnit.UNKNOWN);
        Assert.assertEquals(_mapper.map("28 rr"), DistanceUnit.UNKNOWN);
    }

}