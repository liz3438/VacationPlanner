package com.example.d424_software_engineering_capstonee.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424_software_engineering_capstonee.R;

public class TripDetailFragment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Trip Details");
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
