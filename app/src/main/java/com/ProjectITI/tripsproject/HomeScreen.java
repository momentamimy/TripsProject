package com.ProjectITI.tripsproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ProjectITI.tripsproject.Login.LoginPresenter;
import com.ProjectITI.tripsproject.Model.TripDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private DatabaseReference mDatabase;
    public static Context context ;
    public static Application application;
    String user ;

    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle toggle;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        application = getApplication();
        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddTripActivity();
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_upcomping_trips, R.id.nav_upcoming, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_logout, R.id.nav_sync)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*
        List<String > notes = new ArrayList<>();
        notes.add("first note");
        notes.add("second note");
        notes.add("third note");

        TripDao.addNotes("-M1rt7kFfTdZoROJxVQ8",notes);
        */
        //TripDao.getTripNotes("-M1rt7kFfTdZoROJxVQ8");
    }
    //public com.ProjectITI.tripsproject.Model.Trip(String name, String from, String to, String time, String date, String status, String type) {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goToAddTripActivity() {
        int LAUNCH_SECOND_ACTIVITY = 1;

        Log.i("tag","gotoAddTrip");
            Intent i = new Intent(this, AddTrip.class);
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }


}
