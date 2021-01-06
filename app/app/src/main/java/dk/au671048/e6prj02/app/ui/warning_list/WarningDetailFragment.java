package dk.au671048.e6prj02.app.ui.warning_list;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.Utils;

public class WarningDetailFragment extends Fragment {

    private WarningDetailViewModel viewModel;

    private ImageView icon;
    private TextView type, timestamp, binID, message;
    private Button binDetail;

    public static WarningDetailFragment newInstance() {
        return new WarningDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.warning_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WarningDetailViewModel.class);
        viewModel.setArguments(getArguments());

        icon = view.findViewById(R.id.bin_detail_icon);
        type = view.findViewById(R.id.bin_detail_location);
        timestamp = view.findViewById(R.id.bin_detail_battery);
        binID = view.findViewById(R.id.bin_detail_address);
        message = view.findViewById(R.id.warning_detail_message);
        binDetail = view.findViewById(R.id.bin_detail_show_on_map);

        binDetail.setOnClickListener(v -> navigateToBinDetail(viewModel.getWarning().getValue().getBinId()));

        viewModel.getWarning().observe(getViewLifecycleOwner(), w -> {
            icon.setImageResource(Utils.getWarningIconID(w.getType()));
            type.setText(Utils.capitalize(w.getType()));
            timestamp.setText(Utils.DATE_FORMAT.format(w.getTimestamp()));
            binID.setText(w.getBinId());
            message.setText(w.getMessage());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
    }

    private void navigateToBinDetail(String id) {
        // navigate to bin detail
        WarningDetailFragmentDirections.WarningDetailToBinDetail dir =
                WarningDetailFragmentDirections.warningDetailToBinDetail(id);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}