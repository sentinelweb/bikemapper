package co.uk.sentinelweb.bikemapper.locations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.uk.sentinelweb.bikemapper.R;
import co.uk.sentinelweb.bikemapper.core.model.Location;
import co.uk.sentinelweb.bikemapper.locations.LocationListFragment.OnInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Location} and makes a call to the
 * specified {@link OnInteractionListener}.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private List<Location> _items;
    private final OnInteractionListener _listener;

    public LocationRecyclerViewAdapter(final List<Location> items, final OnInteractionListener listener) {
        _items = items;
        _listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = _items.get(position);
        holder.mIdView.setText(Long.toString(holder.mItem.getId()));
        holder.mContentView.setText(holder.mItem.getName());

        holder.mView.setOnClickListener((v) -> {
            if (null != _listener) {
                _listener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void setItems(final List<Location> items) {
        _items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @Bind(R.id.id)
        public TextView mIdView;
        @Bind(R.id.content)
        public TextView mContentView;

        private Location mItem;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
