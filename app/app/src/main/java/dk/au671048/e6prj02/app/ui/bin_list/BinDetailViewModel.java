package dk.au671048.e6prj02.app.ui.bin_list;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import dk.au671048.e6prj02.app.model.Bin;
import dk.au671048.e6prj02.app.model.Repository;

public class BinDetailViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<String> id;
    private MutableLiveData<Bin> bin;

    public BinDetailViewModel(@NonNull Application application) {
        super(application);
        id = new MutableLiveData<>();
        bin = new MutableLiveData<>();
        repository = Repository.getRepository(application);
    }

    public void setArguments(Bundle arguments) {
        String id = BinDetailFragmentArgs.fromBundle(arguments).getBinID();
        this.id.setValue(id);
        bin.setValue(repository.getBinByID(id));
    }

    public LiveData<String> getId() {
        return id;
    }

    public LiveData<Bin> getBin() {
        return bin;
    }
}