package dk.au671048.e6prj02.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.au671048.e6prj02.app.model.Route;

@Dao
public interface RouteDAO {
    @Query("SELECT * FROM route")
    LiveData<List<Route>> getAllRoutes();

    @Query("SELECT * FROM route WHERE _id LIKE :id")
    Route findByID(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Route route);

    @Update
    void update(Route route);

    @Delete
    void delete(Route route);

    @Query("DELETE FROM route")
    void deleteAll();
}
