package com.example.d424_software_engineering_capstonee.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.d424_software_engineering_capstonee.R;

public class ShareTripsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_share_trips, container, false);
        Button shareButton = view.findViewById(R.id.button_share);
        shareButton.setOnClickListener(view1 -> {
            shareTrips();
        });

        return view;

    }
    //Sharing method
    private void shareTrips(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Trip Plans");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Here are my trip plans!\n\n" +
                "Trip 1: Beach Trip - Brazil\n" +
                "Trip 2: London Adventure - Explore London\n\n" +
                "Planned with Trip Planner App");
        startActivity(Intent.createChooser(shareIntent, "Share your trips via"));
    }
}
