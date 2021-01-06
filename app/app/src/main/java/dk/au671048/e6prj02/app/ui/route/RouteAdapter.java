package dk.au671048.e6prj02.app.ui.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.model.Route;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {
    private static final String TAG = "RouteAdapter";
    // Interface to implement callback on the Item clicked
    public interface IItemClickedListener {
        void onItemClicked(String id);
    }

    private IItemClickedListener listener;

    // Internal variable to store the Route objects coming form the ViewModel
    private List<Route> routes;

    public RouteAdapter(IItemClickedListener listener) {
        this.listener = listener;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        return new RouteViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Route r = routes.get(position);

        holder.routeID.setText(r.get_id());
        holder.binCount.setText(String.valueOf(r.getBins().size()));
    }

    @Override
    public int getItemCount() {
        return (null != routes) ? routes.size() : 0;
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI widgets
        TextView routeID, binCount;

        // Callback interface
        IItemClickedListener listener;

        public RouteViewHolder(@NonNull View itemView, IItemClickedListener listener) {
            super(itemView);
            routeID = itemView.findViewById(R.id.route_item_routeID);
            binCount = itemView.findViewById(R.id.route_item_bin_count_value);

            itemView.setOnClickListener(this);

            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(routes.get(getAdapterPosition()).get_id());
        }
    }
}
