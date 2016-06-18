package co.uk.sentinelweb.bikemapper.locations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.locations.LocationListFragment.OnInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Location} and makes a call to the
 * specified {@link OnInteractionListener}.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private List<SavedLocation> _items;
    private final OnInteractionListener _listener;

    public LocationRecyclerViewAdapter(final List<SavedLocation> items, final OnInteractionListener listener) {
        _items = items;
        _listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = _items.get(position);
        holder._idView.setText(Long.toString(holder.mItem.getId()));
        holder._textView.setText(holder.mItem.getName());

        if (null != _listener) {
            holder._view.setOnClickListener((v) -> {
                _listener.onListItemClick(holder.mItem);
            });
            holder._bikeButton.setOnClickListener((v) -> {
                _listener.onListBikeClick(holder.mItem);
            });
        }


    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void setItems(final List<SavedLocation> items) {
        _items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View _view;
        @Bind(R.id.id)
        public TextView _idView;
        @Bind(R.id.content)
        public TextView _textView;
        @Bind(R.id.bikeButton)
        public ImageView _bikeButton;

        private SavedLocation mItem;

        public ViewHolder(final View view) {
            super(view);
            _view = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + _textView.getText() + "'";
        }
    }
}
