
package co.uk.sentinelweb.bikemapper.net.response.google.places;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Location {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lng")
    @Expose
    public Double lng;

}
