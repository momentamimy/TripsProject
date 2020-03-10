package com.ProjectITI.tripsproject;

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
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ProjectITI.tripsproject.ui.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static com.ProjectITI.tripsproject.Login.LoginPresenter.databaseReference;
import static com.ProjectITI.tripsproject.Login.LoginPresenter.mAuth;

public class UpdateTripData extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView tripName ;
    private TextView timeSelected ;
    private ImageView timePicker ;
    DatePickerDialog picker;
    private TextView dateSelected ;
    private ImageView datePicker ;
    private Spinner spinner1, spinner2;
    PlacesClient placesClient;
    AutocompleteSupportFragment autocompleteStartPointFragment ;
    AutocompleteSupportFragment autocompleteEndPointFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip_data);

        tripName = findViewById(R.id.tripNameUpdated_Field);
        timeSelected = findViewById(R.id.time_Updated) ;
        timePicker = findViewById(R.id.time_Picker) ;
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        dateSelected = findViewById(R.id.date_Updated) ;
        datePicker = findViewById(R.id.date_Picker) ;
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(UpdateTripData.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateSelected.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        String apiKey = "AIzaSyCsCJEIvV6hUuaAb4R5wVJIPuikZ9TN5ag";

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(UpdateTripData.this, apiKey);
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this);

        //StartPoint Fragment

        autocompleteStartPointFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.startPoint_fragment);

        autocompleteStartPointFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteStartPointFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();

                        Toast.makeText(UpdateTripData.this, ""+latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(UpdateTripData.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //EndPoint Fragment
        autocompleteEndPointFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.endPoint_fragment);

        autocompleteEndPointFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        autocompleteEndPointFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();

                        Toast.makeText(UpdateTripData.this, ""+latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(UpdateTripData.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        setValuesFromIntent();
    }
    public void setValuesFromIntent(){
        tripName.setText(getIntent().getStringExtra("tripName")) ;
        timeSelected.setText(getIntent().getStringExtra("time"));
        dateSelected.setText(getIntent().getStringExtra("date"));
        autocompleteStartPointFragment.setText(getIntent().getStringExtra("startPoint"));
        autocompleteEndPointFragment.setText(getIntent().getStringExtra("endPoint"));

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

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

    public void updateTripClick(View view) {
        updateTrip();
        finish();

    }
    public void updateTrip(){
        Trip trip = new Trip(tripName.getText().toString(),"test", "test6",
                timeSelected.getText().toString(), dateSelected.getText().toString(),
                "Done","one way");
        databaseReference.child("Trips").child(mAuth.getUid()).push().setValue(trip);
    }
}
