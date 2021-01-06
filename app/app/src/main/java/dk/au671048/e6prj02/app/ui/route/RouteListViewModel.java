package dk.au671048.e6prj02.app.ui.route;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au671048.e6prj02.app.Constant;
import dk.au671048.e6prj02.app.model.Repository;
import dk.au671048.e6prj02.app.model.Route;

public class RouteListViewModel extends AndroidViewModel {
    private Repository repository;

    private LiveData<List<Route>> routes;

    public RouteListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        repository.requestUpdateFromAPI(Constant.REQUEST_ROUTES);
        routes = repository.getAllRoutes();
    }

    public LiveData<List<Route>> getRoutes() {
        return routes;
    }
}