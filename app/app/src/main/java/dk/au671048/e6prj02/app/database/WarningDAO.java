package dk.au671048.e6prj02.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dk.au671048.e6prj02.app.model.Warning;

@Dao
public interface WarningDAO {
    @Query("SELECT * FROM warning")
    LiveData<List<Warning>> getAllWarnings();

    @Query("SELECT * FROM warning WHERE _id LIKE :id")
    Warning findByID(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Warning warning);

    @Update
    void update(Warning warning);

    @Delete
    void delete(Warning warning);

    @Query("DELETE FROM warning")
    void deleteAll();
}
