package dk.au671048.e6prj02.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.au671048.e6prj02.app.model.Bin;

@Dao
public interface BinDAO {
    @Query("SELECT * FROM bin")
    LiveData<List<Bin>> getAllBins();

    @Query("SELECT * FROM bin WHERE _id LIKE :id")
    Bin findByID(String id);

    @Query("SELECT * FROM bin WHERE _id IN (:ids)")
    LiveData<List<Bin>> getBinsByIds(List<String> ids);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bin bin);

    @Update
    void update(Bin bin);

    @Delete
    void delete(Bin bin);

    @Query("DELETE FROM bin")
    void deleteAll();
}
