package co.uk.sentinelweb.bikemapper.locationedit;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.Place;

public class SearchPlaceSuggestionsAdapter extends CursorAdapter {

    public static final String ID = "_id";
    public static final String NAME = "NAME";
    public static final String ADDRESS = "ADDRESS";
    public static final String[] COLUMN_NAMES = new String[]{ID, NAME, ADDRESS};
    private List<Place> _places = new ArrayList<>();

    public SearchPlaceSuggestionsAdapter(final Context c) {
        super(c, new MatrixCursor(COLUMN_NAMES), false);
        setPlaces(_places);
    }

    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        final View inflated = LayoutInflater.from(context).inflate(R.layout.edit_place_suggestion, parent, false);
        return inflated;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        final TextView name = (TextView) view.findViewById(R.id.suggest_name);
        name.setText(cursor.getString(1));
        final TextView address = (TextView) view.findViewById(R.id.suggest_address);
        address.setText(cursor.getString(2));
    }

    public void setPlaces(final List<Place> places) {
        final MatrixCursor c = new MatrixCursor(COLUMN_NAMES);
        _places = places;
        for (int i = 0; i < _places.size(); i++) {
            final Place p = places.get(i);
            c.addRow(new String[]{String.valueOf(i), p.getName(), p.getAddress()});
        }
        super.changeCursor(c);
    }

    public Place getPlace(final int position) {
        if (position < _places.size()) {
            return _places.get(position);
        }
        return null;
    }
}