package com.example.d424_software_engineering_capstonee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.example.d424_software_engineering_capstonee.utils.ReportGen;

public class ReportActivity extends AppCompatActivity {
    private TextView reportText;
    private String reportContent;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Trip Report");
        }
        //Initialize
        repository = new Repository(getApplication());
        reportText = findViewById(R.id.report_text);

        String reportType = getIntent().getStringExtra("report_type");
         generateReport(reportType != null ? reportType : "details");

    }
    private void generateReport(String type){
        if(type.equals("summary")) {
            reportContent = ReportGen.generateReport(repository.getmAllTrips());
        } else {
            reportContent = ReportGen.generateTripReport(
                    repository.getmAllTrips(),
                    repository.getmAllConferences()
            );
        }
        reportText.setText(reportContent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        } else if(id == R.id.action_share_gen_report) {
            shareReport();
            return true;
        } else if(id == R.id.action_save_gen_report) {
            saveReport();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void shareReport() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Trip Report");
        shareIntent.putExtra(Intent.EXTRA_TEXT, reportContent);
        startActivity(Intent.createChooser(shareIntent, "Share Report"));
    }
    private void saveReport() {
        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)
                getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData.newPlainText("Trip Report", reportContent);
        Toast.makeText(this, "Report copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
