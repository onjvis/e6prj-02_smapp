package dk.au671048.e6prj02.app.ui.bin_list;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.model.Bin;

public class BinDetailFragment extends Fragment {

    private BinDetailViewModel viewModel;

    private TextView location, battery, fullness, address;
    private Button showOnMap;

    public static BinDetailFragment newInstance() {
        return new BinDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bin_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BinDetailViewModel.class);
        viewModel.setArguments(getArguments());

        location = view.findViewById(R.id.bin_detail_location);
        battery = view.findViewById(R.id.bin_detail_battery);
        fullness = view.findViewById(R.id.bin_detail_fullness);
        address = view.findViewById(R.id.bin_detail_address);
        showOnMap = view.findViewById(R.id.bin_detail_show_on_map);

/*
 * https://stackoverflow.com/questions/6205827/how-to-open-standard-google-map-application-from-my-application
 * https://stackoverflow.com/questions/3990110/how-do-i-show-a-marker-in-maps-launched-by-geo-uri-intent
 */
        showOnMap.setOnClickListener(v -> {
            // open map app with latlong
            Bin b = viewModel.getBin().getValue();
            if (null == b) return;
            double lat = b.getLatitude();
            double lng = b.getLongitude();
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=20&q=%f,%f",
                    lat, lng, lat, lng);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            requireContext().startActivity(intent);
        });

        viewModel.getBin().observe(getViewLifecycleOwner(), b -> {
            location.setText(String.valueOf(b.getLatitude()).concat(", ")
                    .concat(String.valueOf(b.getLongitude())));
            battery.setText(String.valueOf(b.getBattery()).concat(" %"));
            fullness.setText(String.valueOf(b.getFullness()).concat(" %"));
            address.setText(b.getAddress());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
    }
}