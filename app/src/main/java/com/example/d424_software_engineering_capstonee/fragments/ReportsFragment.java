package com.example.d424_software_engineering_capstonee.fragments;


import android.content.Intent;
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
import com.example.d424_software_engineering_capstonee.activities.ReportActivity;
import com.example.d424_software_engineering_capstonee.utils.ReportGen;

public class ReportsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        Button generateButton = view.findViewById(R.id.button_reports);
        Button summaryreport = view.findViewById(R.id.button_summary);
        generateButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), ReportActivity.class);
            intent.putExtra("report_type", "detailed");
            startActivity(intent);
            Toast.makeText(getContext(), "Generating detailed report", Toast.LENGTH_SHORT).show();
        });

        summaryreport.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(),ReportActivity.class);
            intent.putExtra("report_type", "summary");
            startActivity(intent);
            Toast.makeText(getContext(),"Generating summary report", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
