package com.example.d424_software_engineering_capstonee.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.example.d424_software_engineering_capstonee.dao.ConfDao;
import com.example.d424_software_engineering_capstonee.dao.TripDao;

@Database(entities = {Trip.class, Conference.class}, version = 1, exportSchema = false)
public abstract class TripDatabaseBuilder extends RoomDatabase {

    public abstract TripDao tripDao();

    public abstract ConfDao confDao();

    private static volatile TripDatabaseBuilder INSTANCE;

    static TripDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TripDatabaseBuilder.class,
                                    "MyTripDatabase.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}