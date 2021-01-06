package dk.au671048.e6prj02.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dk.au671048.e6prj02.app.Constant;
import dk.au671048.e6prj02.app.model.Bin;
import dk.au671048.e6prj02.app.model.Route;
import dk.au671048.e6prj02.app.model.Warning;

@Database(entities = { Bin.class, Warning.class, Route.class }, version = 1, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class BinDatabase extends RoomDatabase {

    // Mandatory DAO getters
    public abstract BinDAO binDAO();
    public abstract WarningDAO warningDAO();
    public abstract RouteDAO routeDAO();

    // Database instance for Singleton pattern
    public static BinDatabase INSTANCE;

    // Singleton pattern for lazy loading
    public static BinDatabase getDatabase(final Context context) {
        if (null == INSTANCE) {
            synchronized (BinDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BinDatabase.class, Constant.BIN_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
