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

import dk.au671048.e6prj02.app.R;

public class RouteListFragment extends Fragment implements RouteAdapter.IItemClickedListener {

    private RouteListViewModel viewModel;

    private RecyclerView recyclerView;
    private RouteAdapter adapter;

    public static RouteListFragment newInstance() {
        return new RouteListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.route_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RouteListViewModel.class);

        adapter = new RouteAdapter(this);
        recyclerView = view.findViewById(R.id.route_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        viewModel.getRoutes().observe(getViewLifecycleOwner(), routes -> adapter.setRoutes(routes));
    }

    @Override
    public void onItemClicked(String id) {
        RouteListFragmentDirections.RouteListToRouteDetail dir = RouteListFragmentDirections
                .routeListToRouteDetail(id);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}