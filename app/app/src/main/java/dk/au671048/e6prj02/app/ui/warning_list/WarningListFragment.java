package dk.au671048.e6prj02.app.ui.warning_list;

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

public class WarningListFragment extends Fragment implements WarningAdapter.IItemClickedListener {
    private static final String TAG = "WarningListFragment";
    private WarningListViewModel viewModel;

    private RecyclerView recyclerView;
    private WarningAdapter adapter;

    public static WarningListFragment newInstance() {
        return new WarningListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.warning_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WarningListViewModel.class);

        adapter = new WarningAdapter(this);
        recyclerView = view.findViewById(R.id.warning_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        viewModel.getWarnings().observe(getViewLifecycleOwner(),
                warnings -> adapter.setWarnings(warnings));
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(String id) {
        // go to warning detail
        WarningListFragmentDirections.WarningListToWarningDetail dir = WarningListFragmentDirections.warningListToWarningDetail(id);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}