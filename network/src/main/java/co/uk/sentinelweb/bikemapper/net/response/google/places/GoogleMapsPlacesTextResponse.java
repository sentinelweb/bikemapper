
package co.uk.sentinelweb.bikemapper.net.response.google.places;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GoogleMapsPlacesTextResponse {

    @SerializedName("html_attributions")
    @Expose
    public List<Object> htmlAttributions = new ArrayList<Object>();
    @SerializedName("next_page_token")
    @Expose
    public String nextPageToken;
    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<Result>();
    @SerializedName("status")
    @Expose
    public String status;

}
