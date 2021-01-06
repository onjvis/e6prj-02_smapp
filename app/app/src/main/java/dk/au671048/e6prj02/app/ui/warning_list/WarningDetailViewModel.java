package dk.au671048.e6prj02.app.ui.warning_list;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dk.au671048.e6prj02.app.model.Repository;
import dk.au671048.e6prj02.app.model.Warning;

public class WarningDetailViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<String> id;
    private MutableLiveData<Warning> warning;

    public WarningDetailViewModel(@NonNull Application application) {
        super(application);
        id = new MutableLiveData<>();
        warning = new MutableLiveData<>();
        repository = Repository.getRepository(application);
    }

    public void setArguments(Bundle arguments) {
        String id =  WarningDetailFragmentArgs.fromBundle(arguments).getWarningID();
        this.id.setValue(id);
        warning.setValue(repository.getWarningByID(id));
    }

    public LiveData<String> getId() {
        return id;
    }

    public LiveData<Warning> getWarning() {
        return warning;
    }
}