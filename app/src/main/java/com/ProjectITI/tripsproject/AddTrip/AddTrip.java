package com.ProjectITI.tripsproject.AddTrip;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import com.ProjectITI.tripsproject.AlertReceiver;
import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;
import com.ProjectITI.tripsproject.R;
import com.ProjectITI.tripsproject.TimePickerFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class AddTrip extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , addTripContract.ViewInterface {

    private TripDao tripDao;
    private addTripContract.PresenterInterface addTripPresenter;
    private TextView timeSelected;
    private ImageView timePicker;
    DatePickerDialog picker;
    private TextView dateSelected;
    private ImageView datePicker;
    private Spinner repeat, type;
    PlacesClient placesClient;
    private EditText tripname;

    int cur_hour, cur_mins;
    int selected_hourOfDay, selected_minute;

    int minDay, minYear, minMonth;
    int selected_year, selected_day, selected_Month;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    AutocompleteSupportFragment autocompleteStartPointFragment;
    AutocompleteSupportFragment autocompleteEndPointFragment;

    String apiKey = "AIzaSyBHpF_TLWue13wvxHvbIqugSt_3LOa2Acw";

    String start = "";
    String end = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        tripDao = new TripDao();

        addTripPresenter = new addTripPresenter(tripDao) ;

        setUpComponents();

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                minDay = cldr.get(Calendar.DAY_OF_MONTH);
                minMonth = cldr.get(Calendar.MONTH);
                minYear = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddTrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (year < minYear) {
                            displayError("Error");
                        } else if (monthOfYear < minMonth && year == minYear) {
                            displayError("Error");
                        } else if (dayOfMonth < minDay && year == minYear && monthOfYear == minMonth)
                        {
                            displayError("Error");
                        }else {
                            dateSelected.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            selected_year = year;
                            selected_day = dayOfMonth;
                            selected_Month = monthOfYear;

                        }


                    }
                }, minYear, minMonth, minDay);
                picker.show();
            }
        });


        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(AddTrip.this, apiKey);
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this);

        //StartPoint Fragment
        autocompleteStartPointFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteStartPointFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        start = place.getAddress();
                       // Toast.makeText(AddTrip.this, "" + latLng.latitude, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Status status) {
                        displayError(status.getStatusMessage());
                    }
                });

        //EndPoint Fragment

        autocompleteEndPointFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteEndPointFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        end = place.getAddress();
                       // Toast.makeText(AddTrip.this, "" + latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        displayError(status.getStatusMessage());
                    }
                });

        //mAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    private void setUpComponents() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tripname = findViewById(R.id.tripNameUpdated_Field);
        timeSelected = findViewById(R.id.time_Selected);
        timePicker = findViewById(R.id.time_Picker);

        dateSelected = findViewById(R.id.date_Selected);
        datePicker = findViewById(R.id.date_Picker);

        type = findViewById(R.id.direction_Spinner);
        repeat = findViewById(R.id.repeat_Spinner);

        autocompleteStartPointFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.startPoint_fragment);
        autocompleteEndPointFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.endPoint_fragment);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        selected_hourOfDay = timePicker.getCurrentHour();
        selected_minute = timePicker.getCurrentMinute();

        cur_hour= Calendar.HOUR_OF_DAY;
        cur_mins = Calendar.MINUTE;

        updateTimeText(c);
        startAlarm(c);

    }

    private void updateTimeText(Calendar c) {
        String timeText = "";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());


        timeSelected.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTrip() {

        if (tripname.getText().toString().equals("") || timeSelected.getText().toString().equals("") || dateSelected.getText().toString().equals("") || start.equals("") || end.equals("")) {

            displayMessage("Please Fill Empty Fields");

        } else {
            //   int cur_hour , cur_mins;
            // int selected_hourOfDay, selected_minute;
            // int minDay,minYear,minMonth;
            //    int selected_year, selected_day,selected_Month;
            //if(((selected_hourOfDay < cur_hour) || (selected_minute < cur_mins ))&& ( (minDay==selected_day) &&(minMonth==selected_Month) )
            Log.i("tag" ,"selected_hourOfDay : "+selected_hourOfDay );
            Log.i("tag" ,"cur_hour : "+cur_hour );
            Log.i("tag" ,"selected_minute : "+selected_minute );
            Log.i("tag" ,"cur_mins : "+cur_mins );
          //  Log.i("tag" ,"selected_hourOfDay : "+selected_hourOfDay );
           // Log.i("tag" ,"selected_hourOfDay : "+selected_hourOfDay );

          /*  if ((selected_hourOfDay < cur_hour && selected_minute < cur_mins) && (minDay == selected_day && minMonth == selected_Month && minYear == selected_year) ) {
                Toast.makeText(getApplicationContext(), "Error in time", Toast.LENGTH_LONG).show();
                Log.i("tag", "Error in time");

            } else {

           */
                String trip_name = tripname.getText().toString();
                String to = "to test";
                String time = timeSelected.getText().toString();
                String date = dateSelected.getText().toString();
                String trip_repeat = repeat.getSelectedItem().toString();
                String status = "upcoming";
                String trip_type = type.getSelectedItem().toString();
//Trip(String name, String from, String to, String time, String date, String status, String type, String repeat) {
                Trip trip = new Trip(trip_name, start, end, time, date, status, trip_type, trip_repeat);
                ArrayList<String> notes = new ArrayList<>();


            addTripPresenter.addTrip(trip);
                //databaseReference.child("Trips").child(mAuth.getUid()).push().setValue(trip);
                returnToMain();

           // }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTripClick(View view) {
        addTrip();

    }

    @Override
    public void returnToMain() {
        finish();
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void displayError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

}
