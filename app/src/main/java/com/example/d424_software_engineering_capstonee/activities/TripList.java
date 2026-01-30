package com.example.d424_software_engineering_capstonee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TripList extends AppCompatActivity {
private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout
        setContentView(R.layout.activity_trip_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripList.this, TripDetails.class);
                startActivity(intent);
            }
        });
        //Set Recyclerview
        RecyclerView recyclerView = findViewById(R.id.vacationRecyclerview);
        repository=new Repository(getApplication());
        List<Trip> allTrips = repository.getmAllTrips();
        final TripAdapter tripAdapter = new TripAdapter(this);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter.setTrips(allTrips);




        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Menu options for trip list screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.trip_list, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.vacationRecyclerview);
        repository = new Repository(getApplication());
        List<Trip> allTrips = repository.getmAllTrips();
        final TripAdapter tripAdapter = new TripAdapter(this);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter.setTrips(allTrips);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.vacation_choice){
            repository = new Repository(getApplication());
            Trip trip = new Trip(6000, "Brazil Trip", "Amazon Hotel", "05/05/2026", "05/20/2026");
            repository.insert(trip);
            trip = new Trip(7000, "London Trip", "Ye Old Ram's Inn","07/07/2026","07/20/2026");
            repository.insert(trip);
            Conference conference = new Conference("Jungle Hiking", "05/06/2026", 1);
            repository.insert(conference);
            conference = new Conference("Explore London History", "07/08/2026", 2);
            repository.insert(conference);
            return true;

        }
        return true;
    }

}