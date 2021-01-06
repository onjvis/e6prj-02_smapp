package dk.au671048.e6prj02.app.ui.bin_list;

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

public class BinListFragment extends Fragment implements BinAdapter.IItemClickedListener {

    private BinListViewModel viewModel;

    private RecyclerView recyclerView;
    private BinAdapter adapter;

    public static BinListFragment newInstance() {
        return new BinListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bin_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BinListViewModel.class);

        adapter = new BinAdapter(this);
        recyclerView = view.findViewById(R.id.bin_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        viewModel.getBins().observe(getViewLifecycleOwner(), bins -> adapter.setBins(bins));
    }

    @Override
    public void onItemClicked(String id) {
        BinListFragmentDirections.BinListToBinDetail dir = BinListFragmentDirections
                .binListToBinDetail(id);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}