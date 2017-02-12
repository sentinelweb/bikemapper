package co.uk.sentinelweb.bikemapper.locationedit;

import java.util.List;

import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;

/**
 * Created by robert on 01/11/2016.
 */

public interface LocationEditContract {

    interface Presenter extends MVPContract.BasePresenter {
        void searchPlaces(final String text);

        void onPlaceSelected(Place place);
    }

    interface View extends MVPContract.BaseView<co.uk.sentinelweb.bikemapper.locationedit.LocationEditContract.Presenter> {
        void setLoadingIndicator(boolean loading);

        void showLoadingError(String message);

        void setLocation(SavedLocation location);

        void setPlaces(List<Place> places);
    }
}
