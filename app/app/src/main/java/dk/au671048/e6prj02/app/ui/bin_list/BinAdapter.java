package dk.au671048.e6prj02.app.ui.bin_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.model.Bin;

public class BinAdapter extends RecyclerView.Adapter<BinAdapter.BinViewHolder> {
    // Interface to implement callback on the Item clicked
    public interface IItemClickedListener {
        void onItemClicked(String id);
    }

    private IItemClickedListener listener;

    // Internal variable to store the Bin objects coming form the ViewModel
    private List<Bin> bins;

    public BinAdapter(IItemClickedListener listener) {
        this.listener = listener;
    }

    public void setBins(List<Bin> bins) {
        this.bins = bins;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bin_item, parent, false);
        return new BinViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BinViewHolder holder, int position) {
        Bin bin = bins.get(position);

        holder.location.setText(bin.getAddress());
        holder.battery.setText(String.valueOf(bin.getBattery()).concat(" %"));
        holder.fullness.setText(String.valueOf(bin.getFullness()).concat(" %"));
    }

    @Override
    public int getItemCount() {
        return (null != bins) ? bins.size() : 0;
    }

    public class BinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI widgets
        TextView location;
        TextView battery;
        TextView fullness;

        // Callback interface
        IItemClickedListener listener;

        public BinViewHolder(@NonNull View itemView, IItemClickedListener listener) {
            super(itemView);
            location = itemView.findViewById(R.id.bin_item_location);
            battery = itemView.findViewById(R.id.bin_item_battery);
            fullness = itemView.findViewById(R.id.bin_item_fullness);

            itemView.setOnClickListener(this);

            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(bins.get(getAdapterPosition()).get_id());
        }
    }
}
