package com.ProjectITI.tripsproject.ui.Trips_History.Alert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.ProjectITI.tripsproject.Model.TripDao;

public class deleteTrip extends AppCompatActivity {
    String trip_id;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.10));

        Intent intent = getIntent();
        trip_id = intent.getStringExtra("trip_id");

        AlertDialog.Builder builder = new AlertDialog.Builder(deleteTrip.this);
        builder.setMessage("Do you want to delete this trip ?");
        builder.setTitle("Alert !");
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        // When the user click yes button
                        TripDao.deleteTrip(trip_id);
                        finish();
                    }
                });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        dialog.cancel();
                        finish();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();

    }
}
