
package co.uk.sentinelweb.bikemapper.net.response.google.places;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    public Boolean openNow;
    @SerializedName("weekday_text")
    @Expose
    public List<Object> weekdayText = new ArrayList<Object>();

}
