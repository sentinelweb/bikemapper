package co.uk.sentinelweb.bikemapper.locations;

import java.util.List;

import co.uk.sentinelweb.bikemapper.BaseView;
import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;

/**
 * Created by robert on 14/06/16.
 */
public interface LocationListView extends BaseView<LocationListPresenter> {

    void setLoadingIndicator(boolean loading);

    void showLoadingError(String message);

    void setLocations(List<SavedLocation> locations);
}
