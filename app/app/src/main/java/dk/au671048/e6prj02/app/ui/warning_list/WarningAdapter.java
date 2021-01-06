package dk.au671048.e6prj02.app.ui.warning_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.Utils;
import dk.au671048.e6prj02.app.model.Warning;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.WarningViewHolder> {
    // Interface to implement callback on the Item clicked
    public interface IItemClickedListener {
        void onItemClicked(String id);
    }
    private IItemClickedListener listener;

    // Internal variable to store the Warning objects coming form the ViewModel
    private List<Warning> warnings;

    public WarningAdapter(IItemClickedListener listener) {
        this.listener = listener;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WarningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.warning_item, parent, false);
        return new WarningViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WarningViewHolder holder, int position) {
        Warning warning = warnings.get(position);

        holder.errorIcon.setImageResource(Utils.getWarningIconID(warning.getType()));
        holder.binID.setText(warning.getBinId());
        holder.timestamp.setText(Utils.DATE_FORMAT.format(warning.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return  (null != warnings) ? warnings.size() : 0;
    }

    public class WarningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI widgets
        ImageView errorIcon;
        TextView binID;
        TextView timestamp;

        // Callback interface
        IItemClickedListener listener;

        public WarningViewHolder(@NonNull View itemView, final IItemClickedListener listener) {
            super(itemView);
            errorIcon = itemView.findViewById(R.id.warning_item_icon);
            binID = itemView.findViewById(R.id.warning_item_binID);
            timestamp = itemView.findViewById(R.id.warning_item_timestamp);

            itemView.setOnClickListener(this);

            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(warnings.get(getAdapterPosition()).get_id());
        }
    }
}
