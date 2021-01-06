package dk.au671048.e6prj02.app.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dk.au671048.e6prj02.app.Constant;
import dk.au671048.e6prj02.app.Utils;
import dk.au671048.e6prj02.app.database.BinDatabase;

public class Repository {
    private static final String TAG = "Repository";
    // Room database
    private BinDatabase db;

    // Current data
    private LiveData<List<Bin>> allBins;
    private LiveData<List<Route>> allRoutes;
    private LiveData<List<Warning>> allWarnings;

    // For asynchronous processing
    private ExecutorService executor;

    // Volley queue
    private RequestQueue queue;

    private Application application;

    private static Repository INSTANCE;

    public static Repository getRepository(Application application) {
        if (null == INSTANCE) {
            INSTANCE = new Repository(application);
        }
        return INSTANCE;
    }

    private Repository(Application application) {
        db = BinDatabase.getDatabase(application.getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        // Get request queue
        queue = Volley.newRequestQueue(application.getApplicationContext());
        // Set LiveData fields from DAO
        allWarnings = db.warningDAO().getAllWarnings();
        allBins = db.binDAO().getAllBins();
        allRoutes = db.routeDAO().getAllRoutes();

        this.application = application;
    }

    public LiveData<List<Bin>> getAllBins() {
        return allBins;
    }

    public LiveData<List<Route>> getAllRoutes() {
        return allRoutes;
    }

    public LiveData<List<Warning>> getAllWarnings() {
        return allWarnings;
    }

    public void requestUpdateFromAPI(int type) {
        String getUrl = Constant.API_BASE_URL;
        switch (type) {
            case Constant.REQUEST_WARNINGS:
                getUrl += "/warning";
                break;
            case Constant.REQUEST_BINS:
                getUrl += "/bin" ;
                break;
            case Constant.REQUEST_ROUTES:
                getUrl += "/route";
                break;
        }

        SharedPreferences sp = this.application.getApplicationContext()
                .getSharedPreferences(Constant.SHARED_PREFS, Context.MODE_PRIVATE);
        String token = sp.getString(Constant.JWT_KEY, Constant.DEFAULT_STRING_VALUE);

        JsonArrayRequest request = new JsonArrayRequest(getUrl, response -> {
            Log.d(TAG, "onResponse: " + response);
            parseJsonResponse(response, type);
        }, error -> Log.e(TAG, "onErrorResponse: ", error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("content-type", "application/json");
                return params;
            }
        };

        queue.add(request);
    }

    private void parseJsonResponse(JSONArray response, int type) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                switch (type) {
                    case Constant.REQUEST_WARNINGS:
                        Warning w = Utils.GSON.fromJson(String.valueOf(json), Warning.class);
                        this.insertWarning(w);
                        break;
                    case Constant.REQUEST_BINS:
                        Bin b = Utils.GSON.fromJson(String.valueOf(json), Bin.class);
                        double lat = json.getJSONObject("location").getDouble("latitude");
                        double lng = json.getJSONObject("location").getDouble("longitude");
                        String address = Utils.getAddressFromLocation(lat, lng);
                        b.setLatitude(lat);
                        b.setLongitude(lng);
                        b.setAddress(address);
                        this.insertBin(b);
                        break;
                    case Constant.REQUEST_ROUTES:

                        Route r = Utils.GSON.fromJson(String.valueOf(json), Route.class);
                        Log.d(TAG, "parseJsonResponse: " + r.toString());
                        this.insertRoute(r);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Warning DAO
    public Warning getWarningByID(final String id) {
        Future<Warning> w = executor.submit(() -> db.warningDAO().findByID(id));
        try {
            return w.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertWarning(final Warning warning) {
        executor.execute(() -> db.warningDAO().insert(warning));
    }

    public void updateWarning(final Warning warning) {
        executor.execute(() -> db.warningDAO().update(warning));
    }

    public void deleteWarning(final Warning warning) {
        executor.execute(() -> db.warningDAO().delete(warning));
    }

    public void deleteAllWarnings() {
        executor.execute(() -> db.warningDAO().deleteAll());
    }

    // Bin DAO
    public Bin getBinByID(final String id) {
        Future<Bin> b = executor.submit(() -> db.binDAO().findByID(id));
        try {
            return b.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Bin>> getBinsByIds(List<String> ids) {
        Future<LiveData<List<Bin>>> bs = executor.submit(() -> db.binDAO().getBinsByIds(ids));
        try {
            return bs.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertBin(final Bin bin) {
        executor.execute(() -> db.binDAO().insert(bin));
    }

    public void updateBin(final Bin bin) {
        executor.execute(() -> db.binDAO().update(bin));
    }

    public void deleteBin(final Bin bin) {
        executor.execute(() -> db.binDAO().delete(bin));
    }

    public void deleteAllBins() {
        executor.execute(() -> db.binDAO().deleteAll());
    }

    // Route DAO
    public Route getRouteByID(final String id) {
        Future<Route> r = executor.submit(() -> db.routeDAO().findByID(id));
        try {
            return r.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertRoute(final Route route) {
        executor.execute(() -> db.routeDAO().insert(route));
    }

    public void updateRoute(final Route route) {
        executor.execute(() -> db.routeDAO().update(route));
    }

    public void deleteRoute(final Route route) {
        executor.execute(() -> db.routeDAO().delete(route));
    }

    public void deleteAllRoutes() {
        executor.execute(() -> db.routeDAO().deleteAll());
    }
}
