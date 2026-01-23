package com.example.d424_software_engineering_capstonee.database;

import android.app.Application;
import com.example.d424_software_engineering_capstonee.dao.TripDao;
import com.example.d424_software_engineering_capstonee.dao.ConfDao;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.example.d424_software_engineering_capstonee.entities.Conference;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Repository {
    private ConfDao mConferenceDAO;
    private TripDao mTripDAO;

    private List<Trip> mAllTrips;
    private List<Conference> mAllConferences;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        TripDatabaseBuilder db = TripDatabaseBuilder.getDatabase(application);
        mTripDAO = db.tripDao();
        mConferenceDAO = db.confDao();
    }

    public List<Trip>getmAllTrips(){
        databaseExecutor.execute(()->{
            mAllTrips=mTripDAO.getAllTrips();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        return mAllTrips;
    }
    //Conferences related to trips
    public List<Conference> getConferencesByTrips(int tripID){
        databaseExecutor.execute(()->{
            mAllConferences= mConferenceDAO.getConferenceByTrip(tripID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllConferences;
    }
    public void insert(Trip trip){
        databaseExecutor.execute(()->{
            mTripDAO.insert(trip);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Trip trip){
        databaseExecutor.execute(()->{
            mTripDAO.update(trip);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Trip trip){
        databaseExecutor.execute(()->{
            mTripDAO.delete(trip);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public List<Conference> getmAllConferences(){
        databaseExecutor.execute(()->{
            mAllConferences=mConferenceDAO.getAllConferences();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException();
        }

        return mAllConferences;
    }
    public void insert(Conference conference){
        databaseExecutor.execute(()->{
            mConferenceDAO.insert(conference);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Conference conference){
        databaseExecutor.execute(()->{
            mConferenceDAO.update(conference);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Conference conference){
        databaseExecutor.execute(()->{
            mConferenceDAO.delete(conference);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
