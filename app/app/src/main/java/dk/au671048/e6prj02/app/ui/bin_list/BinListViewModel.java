package dk.au671048.e6prj02.app.ui.bin_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au671048.e6prj02.app.Constant;
import dk.au671048.e6prj02.app.model.Bin;
import dk.au671048.e6prj02.app.model.Repository;

public class BinListViewModel extends AndroidViewModel {
    private Repository repository;

    private LiveData<List<Bin>> bins;

    public BinListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        repository.requestUpdateFromAPI(Constant.REQUEST_BINS);
        bins = repository.getAllBins();
    }

    public LiveData<List<Bin>> getBins() {
        return bins;
    }
}