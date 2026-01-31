package com.example.d424_software_engineering_capstonee.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.d424_software_engineering_capstonee.R;

public class ReportsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        Button generateButton = view.findViewById(R.id.button_reports);
        generateButton.setOnClickListener(v ->{
            Toast.makeText(getContext(), "Generating report", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
