package com.example.d424_software_engineering_capstonee.activities;

import android.app.Notification;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import androidx.appcompat.widget.Toolbar;

import com.example.d424_software_engineering_capstonee.entities.Trip;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424_software_engineering_capstonee.R;

public class TripDetails extends AppCompatActivity {
    String name;
    double price;
    int productID;

    String startdate;
    String enddate;
    EditText editName;
    EditText editPrice;

    String hotelname;

    EditText hotelName;
    EditText startDateField;
    EditText endDateField;

    private void scheduleAlert(String vacationTitle, String date, boolean isStart){
        try {
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date alertDate = sf.parse(date);

            if(alertDate == null){
                Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("title", "Trip Alert: " + vacationTitle);
            intent.putExtra("message", vacationTitle + " is " + (isStart ? "starting" : "ending") + " today!");

            int requestCode = (vacationTitle + date + isStart).hashCode();

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if(alarmManager != null){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(alarmManager.canScheduleExactAlarms()){
                        alarmManager.setExact(
                                AlarmManager.RTC_WAKEUP,
                                alertDate.getTime(),
                                pendingIntent
                        );
                        Toast.makeText(this,
                                "Alert set for " + vacationTitle + " " + (isStart ? "start" : "end") + " date",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Intent permIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                        startActivity(permIntent);
                        Toast.makeText(this, "Please allow for scheduling exact alarms", Toast.LENGTH_LONG).show();
                    }
                } else {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            alertDate.getTime(),
                            pendingIntent
                    );
                    Toast.makeText(this, "Alert set for " + vacationTitle + " " + (isStart ? "start" : "end") + " date",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error setting alert", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker(EditText dateField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = String.format("%02d/%02d/%04d",
                    selectedMonth + 1, selectedDay, selectedYear);
            dateField.setText(date);
        },
                year, month, day
        );
        datePickerDialog.show();
    }
    //Date validation

    private boolean isValidDateFormat(String date) {
        if(date == null || date.isEmpty()) {
            return false;
        }
        String datePattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$";
        if(!date.matches(datePattern)){
            return false;
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            sf.setLenient(false);
            sf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isEndDateAfterStartDate(String startDate, String endDate) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date start = sf.parse(startDate);
            Date end = sf.parse(endDate);
            assert end != null;
            return end.after(start);
        } catch (ParseException e) {
            return false;
        }
    }

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Trip Details");
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        editName = findViewById(R.id.vacationname);
        editPrice = findViewById(R.id.pricetext);
        hotelName = findViewById(R.id.hotel);
        startDateField = findViewById(R.id.editTextStartDate);
        endDateField = findViewById(R.id.editTextEndDate);
        productID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        hotelname = getIntent().getStringExtra("hotelName");
        startdate = getIntent().getStringExtra("startDate");
        enddate = getIntent().getStringExtra("endDate");

        editName.setText(name);
        editPrice.setText(Double.toString(price));
        hotelName.setText(hotelname);
        startDateField.setText(startdate);
        endDateField.setText(enddate);

        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startDateField);
            }
        });
        endDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(endDateField);
            }
        });
        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateField.setError(null);
                startDateField.setTextColor(getResources().getColor(android.R.color.black));
                showDatePicker(startDateField);
            }
        });
        endDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateField.setError(null);
                endDateField.setTextColor(getResources().getColor(android.R.color.black));
                showDatePicker(endDateField);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetails.this, ConfDetails.class);
                intent.putExtra("tripID", productID);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.exrecyclerview);
        repository = new Repository(getApplication());
        final ConfAdapter confAdapter = new ConfAdapter(this);
        recyclerView.setAdapter(confAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Conference> filteredConferences = new ArrayList<>();
        for(Conference c : repository.getmAllConferences()){
            if(c.getTripID() == productID) filteredConferences.add(c);
        }
        confAdapter.setmConferences(filteredConferences);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_trip_details, menu);
        return true;
    }
    private void shareVacation(){
        String vacationName = editName.getText().toString();
        String hotel = hotelName.getText().toString();
        String price = editPrice.getText().toString();
        String startDate = startDateField.getText().toString();
        String endDate = endDateField.getText().toString();

        String shareMessage = "Trip Details:\n\n" +
                "Trip: " + vacationName + "\n" +
                "Hotel: " + hotel + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n\n" +
                "Share from Trip Planner";
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Trip Details for " + vacationName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        startActivity(Intent.createChooser(shareIntent, "Share trip via"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        if(item.getItemId()==R.id.share_vacation){
            shareVacation();
            return true;
        }
        if(item.getItemId()==R.id.set_start_alert){
            String vacationName = editName.getText().toString();
            String startDate = startDateField.getText().toString();
            if(!startDate.isEmpty() && !vacationName.isEmpty()){
                scheduleAlert(vacationName, startDate, true);
            } else {
                Toast.makeText(this, "Please enter trip and start date first.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(item.getItemId() == R.id.set_end_alert){
            String vacationName = editName.getText().toString();
            String endDate = endDateField.getText().toString();
            if(!endDate.isEmpty() && !vacationName.isEmpty()){
                scheduleAlert(vacationName, endDate,false);
            } else {
                Toast.makeText(this, "Please enter trip name and the end date", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(item.getItemId()== R.id.vacationsave) {
            startDateField.setTextColor(getResources().getColor(android.R.color.black));
            endDateField.setTextColor(getResources().getColor(android.R.color.black));
            String startDate = startDateField.getText().toString();
            String endDate = endDateField.getText().toString();

            if(!isValidDateFormat(startDate)){
                startDateField.setError("Invalid date format. Use MM/DD/YYYY");
                startDateField.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(this, "Invalid start date format", Toast.LENGTH_LONG).show();
                return true;
            }
            if(!isValidDateFormat(endDate)){
                endDateField.setError("Invalid end date format. Use MM/DD/YYYY.");
                endDateField.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(this, "Invalid end date!", Toast.LENGTH_LONG).show();
                return true;
            }
            if(!isEndDateAfterStartDate(startDate, endDate)){
                startDateField.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                endDateField.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(this,"End date must be after the start date", Toast.LENGTH_LONG).show();
                return true;
            }

            Trip trip;
            if(productID==-1){
                if(repository.getmAllTrips().isEmpty()) productID = 1;
                else
                    productID = repository.getmAllTrips().get(repository.getmAllTrips().size() - 1).getTripID() + 1;
                trip = new Trip(Double.parseDouble(editPrice.getText().toString()), editName.getText().toString(), hotelName.getText().toString(), startDateField.getText().toString(), endDateField.getText().toString());
                repository.insert(trip);
                this.finish();
            }
            else {
                trip = new Trip(
                        Double.parseDouble(editPrice.getText().toString()),
                        editName.getText().toString(),
                        hotelName.getText().toString(),
                        startDateField.getText().toString(),
                        endDateField.getText().toString()
                );
                trip.setTripID(productID);
                repository.update(trip);
            }
            this.finish();
            return true;
        } else if(item.getItemId()==R.id.deletevacation) {
            if (item.getItemId() == R.id.deletevacation) {
                List<Conference> assocConferences = repository.getConferencesByTrips(productID);
                if (assocConferences != null && assocConferences.size() > 0) {
                    Toast.makeText(TripDetails.this, "Cannot delete a trip with an associated conference.Delete conference first.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Trip trip1 = new Trip(
                            Double.parseDouble(editPrice.getText().toString()),
                            editName.getText().toString(), hotelName.getText().toString(),
                            startDateField.getText().toString(),
                            endDateField.getText().toString()
                    );
                    trip1.setTripID(productID);
                    repository.delete(trip1);
                    Toast.makeText(TripDetails.this,
                            "Trip deleted",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.exrecyclerview);
        final ConfAdapter confAdapter = new ConfAdapter(this);
        recyclerView.setAdapter(confAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Conference> filteredConferences = new ArrayList<>();
        for(Conference c : repository.getmAllConferences()){
            if(c.getTripID() == productID) filteredConferences.add(c);
        }
        confAdapter.setmConferences(filteredConferences);
    }

}