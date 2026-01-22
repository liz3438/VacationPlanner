package com.example.d424_software_engineering_capstonee.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.example.d424_software_engineering_capstonee.entities.Trip;
@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM TRIPS ORDER BY tripID ASC")
    List<Trip> getAllTrips();
}
