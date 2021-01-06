package dk.au671048.e6prj02.app.ui.route;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dk.au671048.e6prj02.app.model.Bin;
import dk.au671048.e6prj02.app.model.Repository;
import dk.au671048.e6prj02.app.model.Route;

public class RouteDetailViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<String> id;
    private MutableLiveData<Route> route;
    private LiveData<List<Bin>> bins;

    public RouteDetailViewModel(@NonNull Application application) {
        super(application);
        id = new MutableLiveData<>();
        route = new MutableLiveData<>();
        repository = Repository.getRepository(application);
    }

    public void setArguments(Bundle arguments) {
        String id = RouteDetailFragmentArgs.fromBundle(arguments).getRouteID();
        this.id.setValue(id);
        Route r = repository.getRouteByID(id);

        route.setValue(r);
        this.bins = repository.getBinsByIds(r.getBins());
    }

    public LiveData<String> getId() {
        return id;
    }

    public LiveData<Route> getRoute() {
        return route;
    }

    public LiveData<List<Bin>> getBins() {
        return bins;
    }
}