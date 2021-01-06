package dk.au671048.e6prj02.app.ui.route;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dk.au671048.e6prj02.app.R;
import dk.au671048.e6prj02.app.ui.bin_list.BinAdapter;

public class RouteDetailFragment extends Fragment implements BinAdapter.IItemClickedListener {

    private RouteDetailViewModel viewModel;

    private BinAdapter adapter;

    private TextView routeID;
    private Button openInBrowser;
    private RecyclerView recyclerView;

    public static RouteDetailFragment newInstance() {
        return new RouteDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RouteDetailViewModel.class);
        viewModel.setArguments(getArguments());

        routeID = view.findViewById(R.id.route_detail_routeID);
        openInBrowser = view.findViewById(R.id.route_detail_open_in_browser);

        adapter = new BinAdapter(this);

        recyclerView = view.findViewById(R.id.route_detail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        viewModel.getBins().observe(getViewLifecycleOwner(), bins -> adapter.setBins(bins));
        viewModel.getId().observe(getViewLifecycleOwner(), id -> routeID.setText(id));
    }

    @Override
    public void onItemClicked(String id) {
        RouteDetailFragmentDirections.RouteDetailToBinDetail dir = RouteDetailFragmentDirections
                .routeDetailToBinDetail(id);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}