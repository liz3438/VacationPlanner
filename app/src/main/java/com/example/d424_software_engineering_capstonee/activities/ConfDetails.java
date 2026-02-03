package com.example.d424_software_engineering_capstonee.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424_software_engineering_capstonee.R;
import com.example.d424_software_engineering_capstonee.database.Repository;
import com.example.d424_software_engineering_capstonee.entities.Conference;
import com.example.d424_software_engineering_capstonee.entities.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConfDetails extends AppCompatActivity {
    String name;
    String mydate;
    int conferenceID;
    int prodID;
    EditText editName;
    EditText editDate;

    Repository repository;

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conf_details);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Conference Details");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        mydate = getIntent().getStringExtra("date");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        editDate = findViewById(R.id.date);
        editDate.setText(mydate);
        conferenceID = getIntent().getIntExtra("id", -1);
        prodID = getIntent().getIntExtra("tripID", -1);
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Trip> tripArrayList = new ArrayList<>();
        tripArrayList.addAll(repository.getmAllTrips());
        ArrayList<Integer> productIdList = new ArrayList<>();
        for(Trip trip : tripArrayList){
            productIdList.add(trip.getTripID());
        }
        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDate.setError(null);
                editDate.setTextColor(getResources().getColor(android.R.color.black));
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) info = "01/13/2026";
                try {
                    myCalendar.setTime(sf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ConfDetails.this, startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });*/

    }
    private void updateLabelStart(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sf.format(myCalendar.getTime()));
    }
    private boolean isValidDateFormat(String date){
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
    private boolean isExcursionDateInVacation(String excursionDate, int vacationID){
        try {
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date eDate = sf.parse(excursionDate);
            List<Trip> trips = repository.getmAllTrips();
            for(Trip v : trips){
                if(v.getTripID() == vacationID){
                    Date startDate = sf.parse(v.getStartDate());
                    Date endDate = sf.parse(v.getEndDate());
                    return !eDate.before(startDate) && !eDate.after(endDate);
                }
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }
    private boolean isEndDateAfterStartDate(String startDate, String endDate){
        try {
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date start = sf.parse(startDate);
            Date end = sf.parse(endDate);
            assert end != null;
            return end.after(start);
        } catch (ParseException e){
            return false;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conference_details, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId()== R.id.save_excursion) {
            String excursionDateText = editDate.getText().toString();




            if(!isValidDateFormat(excursionDateText)){
                editDate.setError("Invalid date format. Use MM/DD/YYYY.");
                editDate.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(this, "Invalid start date format", Toast.LENGTH_LONG).show();
                return true;
            }

            if(!isExcursionDateInVacation(excursionDateText, prodID)){

                editDate.setError("Conference date must be during the vacation.");
                editDate.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                Toast.makeText(this, "Excursion date must be during the vacation dates.", Toast.LENGTH_LONG).show();
                return true;
            }



            editDate.setError(null);
            editDate.setTextColor(getResources().getColor(android.R.color.black));

            Conference conference;
            if(conferenceID == -1){
                conference = new Conference(editName.getText().toString(), editDate.getText().toString(), prodID);
                repository.insert(conference);
            } else {
                conference = new Conference(editName.getText().toString(),editDate.getText().toString(), prodID);
                conference.setConferenceID(conferenceID);
                repository.update(conference);
            }
                /*if(repository.getmAllExcursions().isEmpty())
                    excursionID = 1;
                else
                        excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() -1).getExcursionID() + 1;
                    excursion=new Excursion(editName.getText().toString(), editDate.getText().toString(), prodID);
                    repository.insert(excursion);
            } else{
                excursion = new Excursion(editName.getText().toString(), editDate.getText().toString(), prodID);
                repository.update(excursion);
            }*/
            finish();
            return true;
        }
        if (item.getItemId() == R.id.delete_excursion){
            if(conferenceID == -1) {
                Toast.makeText(this, "Cannot delete unsaved conference", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Conference conference = new Conference(
                        editName.getText().toString(),
                        editDate.getText().toString(),
                        prodID
                );
                conference.setConferenceID(conferenceID);

                repository.delete(conference);
                Toast.makeText(this, "Conference deleted", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }
        if (item.getItemId()==R.id.notify_excursion){
            String excursionName = editName.getText().toString();
            String dateFromScreen = editDate.getText().toString();
            String myFormat= "MM/dd/yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;

            try {
                myDate = sf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                assert myDate != null;
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ConfDetails.this, NotificationReceiver.class);
                intent.putExtra("title", "Conference Alert: " + excursionName);
                intent.putExtra("message", "Your conference " + excursionName + " is today!");
                PendingIntent sender = PendingIntent.getBroadcast(ConfDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                android.widget.Toast.makeText(this, "Alert set for excursion " + excursionName, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                android.widget.Toast.makeText(this, "Error setting alert", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




