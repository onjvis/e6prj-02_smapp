package dk.au671048.e6prj02.app.ui.warning_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au671048.e6prj02.app.Constant;
import dk.au671048.e6prj02.app.model.Repository;
import dk.au671048.e6prj02.app.model.Warning;

public class WarningListViewModel extends AndroidViewModel {
    private Repository repository;

    private LiveData<List<Warning>> warnings;

    public WarningListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        repository.requestUpdateFromAPI(Constant.REQUEST_WARNINGS);
        warnings = repository.getAllWarnings();
    }

    public LiveData<List<Warning>> getWarnings() {
        return warnings;
    }
}