package com.example.d424_software_engineering_capstonee.dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import java.util.List;

@Dao
public interface ConfDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Conference conference);

    @Update
    void update(Conference conference);

    @Delete
    void delete(Conference conference);

    @Query("SELECT * FROM CONFERENCES ORDER BY conferenceID ASC")
    List<Conference> getAllConferences();

    @Query("SELECT * FROM CONFERENCES WHERE tripID = :tripID")
    List<Conference> getConferenceByTrip(int tripID);
}
