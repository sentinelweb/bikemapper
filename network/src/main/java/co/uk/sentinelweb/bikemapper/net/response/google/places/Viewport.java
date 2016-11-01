
package co.uk.sentinelweb.bikemapper.net.response.google.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Viewport {

    @SerializedName("northeast")
    @Expose
    public Location northeast;
    @SerializedName("southwest")
    @Expose
    public Location southwest;

}
