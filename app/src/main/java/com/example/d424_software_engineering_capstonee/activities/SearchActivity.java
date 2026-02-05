package com.example.d424_software_engineering_capstonee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.example.d424_software_engineering_capstonee.models.Search;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText searchEdit;
    private RecyclerView searchResultRec;
    private TextView count;
    private View noResultLayout;

    private SearchAdapter adapter;
    private Chip chipAll, chipTrips, chipConf;
    private String currFilter = "ALL";

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search");

        }
        //Repository initialization
        repository = new Repository(getApplication());
        //Views initialization
        searchEdit = findViewById(R.id.search_edit_text);
        searchResultRec = findViewById(R.id.results_recycler);
        noResultLayout = findViewById(R.id.no_results);
        count = findViewById(R.id.results);
        ImageButton searchButton = findViewById(R.id.search_button);

        //Setup the chips
        chipAll = findViewById(R.id.chip_all);
        chipTrips = findViewById(R.id.filter_trips);
        chipConf = findViewById(R.id.filter_conferences);
        //RecyclerView setup
        adapter = new SearchAdapter();
        searchResultRec.setLayoutManager(new LinearLayoutManager(this));
        searchResultRec.setAdapter(adapter);

        //Item clicks
        adapter.setOnItemClickListener(result->{
            if(result.getType().equals("TRIP")) {
                Intent intent = new Intent(SearchActivity.this, TripDetails.class);
                intent.putExtra("tripID", result.getId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(SearchActivity.this, ConfDetails.class);
                intent.putExtra("id", result.getId());
                startActivity(intent);
            }
        });
        //Button click
        searchButton.setOnClickListener(v-> performSearch());

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int after, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                performSearch();

            }
        });

        //Filter
        chipAll.setOnClickListener(v->{
            currFilter = "ALL";
            performSearch();
        });

        chipTrips.setOnClickListener(v->{
            currFilter = "TRIPS";
            performSearch();
        });

        chipConf.setOnClickListener(v->{
            currFilter = "CONFERENCES";
            performSearch();
        });

        //Load search
        performSearch();

    }
    private void performSearch(){
        String querySearch = searchEdit.getText().toString().toLowerCase().trim();
        List<Search> results = new ArrayList<>();

        if(currFilter.equals("ALL") || currFilter.equals("TRIPS")){
            List<Trip> trips = repository.getmAllTrips();
            for(Trip trip : trips) {
                boolean matchesQuery = querySearch.isEmpty() ||
                        trip.getTripName().toLowerCase().contains(querySearch) ||
                        trip.getHotel().toLowerCase().contains(querySearch)||
                        trip.getHotel().toLowerCase().contains(querySearch);
                if(matchesQuery){
                    Search result = new Search(
                            "TRIP",
                            trip.getTripID(),
                            trip.getTripName(),
                            trip.getHotel(),
                            trip.getStartDate() + " - " + trip.getEndDate(),
                            "$" + String.format("%.2f", trip.getPrice())
                    );
                    results.add(result);
                }

            }
        }
        if(currFilter.equals("ALL") || currFilter.equals("CONFERENCES")) {
            List<Conference> conferences = repository.getmAllConferences();
            for (Conference conf : conferences) {
                boolean matchesQuery = querySearch.isEmpty() ||
                        conf.getConferenceName().toLowerCase().contains(querySearch);

                if(matchesQuery){
                    Search result = new Search(
                            "CONFERENCE",
                            conf.getConferenceID(),
                            conf.getConferenceName(),
                            "Trip ID: " + conf.getTripID(),
                            conf.getDate(),
                            ""
                    );
                    results.add(result);
                }

            }
        }
        adapter.setResults(results);
        count.setText(results.size() + " result" + (results.size() != 1 ? "s" : ""));

        if(results.isEmpty()){
            noResultLayout.setVisibility(View.VISIBLE);
            searchResultRec.setVisibility(View.GONE);
        } else {
            noResultLayout.setVisibility(View.GONE);
            searchResultRec.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
