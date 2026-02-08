package com.example.d424_software_engineering_capstonee.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.activities.TripAdapter;
import com.example.d424_software_engineering_capstonee.activities.TripDetails;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TripsFragment extends Fragment {

    private Repository repository;
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private View emptyTripsView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null) {
            androidx.appcompat.app.ActionBar actionBar =
                    ((androidx.appcompat.app.AppCompatActivity) getActivity()).getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
//Set up button
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TripDetails.class);
                startActivity(intent);
            }
        });

        emptyTripsView = view.findViewById(R.id.empty_trips);
        recyclerView = view.findViewById(R.id.vacationRecyclerview);
        repository = new Repository(requireActivity().getApplication());

        tripAdapter= new TripAdapter(getContext());
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTrips();

        return view;
    }
    private void loadTrips() {
        List<Trip> allTrips = repository.getmAllTrips();
        //tripAdapter.setTrips(allTrips);
        if(allTrips==null || allTrips.isEmpty()){
            showEmpty();
        } else {
            emptyState();
            tripAdapter.setTrips(allTrips);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTrips();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.trip_list, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vacation_choice) {
            addSampleTrips();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void addSampleTrips() {
        //Sample trips
        Trip trip1 = new Trip(6000, "Brazil Trip", "Amazon Hotel", "05/05/2026", "05/20/2026");
        repository.insert(trip1);

        Trip trip2 = new Trip(7000, "London Trip", "Ye Old Ram's Inn", "07/07/2026", "07/20/2026");
        repository.insert(trip2);

        //Sample conferences
        Conference conference1 = new Conference("Jungle Hiking Conference", "05/06/2026", 1);
        repository.insert(conference1);

        Conference conference2 = new Conference("London Tower Conference", "07/08/2026", 2);
        repository.insert(conference2);

        loadTrips();


    }
    //Animations
    private void showEmpty(){
        recyclerView.setVisibility(View.GONE);
        emptyTripsView.setVisibility(View.VISIBLE);

        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
        emptyTripsView.startAnimation(fadeIn);

        ImageView icon = emptyTripsView.findViewById(R.id.icon);
        if(icon != null){
            Animation pulse = AnimationUtils.loadAnimation(getContext(),R.anim.pulsing);
            icon.startAnimation(pulse);
        }
        ImageView arrow = emptyTripsView.findViewById(R.id.arrow);
        if(arrow != null){
            Animation bounce = AnimationUtils.loadAnimation(getContext(),R.anim.bouncing);
            arrow.startAnimation(bounce);
        }
        CardView hint = emptyTripsView.findViewById(R.id.hints);
        if(hint != null){
            Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulsing);
            hint.startAnimation(pulse);
        }
    }
    private void emptyState(){
        recyclerView.setVisibility(View.VISIBLE);
        emptyTripsView.setVisibility(View.GONE);
    }
}
