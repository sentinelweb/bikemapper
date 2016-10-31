
package co.uk.sentinelweb.bikemapper.net.response.google.places;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Photo {

    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("html_attributions")
    @Expose
    public List<String> htmlAttributions = new ArrayList<String>();
    @SerializedName("photo_reference")
    @Expose
    public String photoReference;
    @SerializedName("width")
    @Expose
    public Integer width;

}
