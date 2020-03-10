package com.ProjectITI.tripsproject.Model;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ProjectITI.tripsproject.HomeScreen;
import com.ProjectITI.tripsproject.Login.LoginPresenter;
import com.ProjectITI.tripsproject.ui.Trips_History.GalleryFragment;
import com.ProjectITI.tripsproject.ui.Upcoming_Trips.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TripDao {
    static Application application = HomeScreen.application;
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    static String userId = LoginPresenter.userId;

    public static void AddTrip(Trip trip)
    {
        mDatabase.child("Trips").child(userId).push().setValue(trip);
    }

    public static void getAllData(final String trip_status)
    {
        Log.i("tag","calling Method getAllData onChange ");
        final List<Trip> UpcomingData = new ArrayList<>();
        final List<Trip> HistoryData = new ArrayList<>();
        //  onDataChange is asynchronous

        DatabaseReference newsRef = mDatabase.child("Trips").child(userId);
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UpcomingData.clear();
                HistoryData.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String from = ds.child("from").getValue(String.class);
                    String to = ds.child("to").getValue(String.class);
                    String status = ds.child("status").getValue(String.class);
                    String time = ds.child("time").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    String type = ds.child("type").getValue(String.class);
                    String repeat = ds.child("repeat").getValue(String.class);
                    String id = ds.getKey();
                    ArrayList<String> map = (ArrayList<String>) ds.child("Notes").getValue();
                    if(map != null)
                    for (int i = 0 ; i<map.size() ;i++)
                    {
                        if(map.get(i)==null)
                        {
                            map.remove(i);
                        }
                    }else{
                        map = new ArrayList<>();
                    }

                    Log.i("tag","Map Notes : "+map);
                    if(status.equals("upcoming")) {
                        UpcomingData.add(new Trip(id,name, from, to, time, date, status, type, repeat,map));
                    }else{
                        HistoryData.add(new Trip(id,name, from, to, time, date, status, type, repeat,map));
                    }
//twoice
            }
                if(trip_status.equals("upcoming")) {
                    HomeFragment.SetDataInRcycleView(UpcomingData);
                }else{
                    GalleryFragment.SetDataInRcycleView(HistoryData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("tag", databaseError.getMessage());
            }
        });

        if(trip_status.equals("upcoming")) {
            HomeFragment.SetDataInRcycleView(UpcomingData);
        }else{
            GalleryFragment.SetDataInRcycleView(HistoryData);
        }
    }

    public static void deleteTrip( String tripId)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Trips").child(userId).child(tripId);
        mDatabase.removeValue();

    }

    public static void cancelTrip (String tripId)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Trips").child(userId).child(tripId);
        mDatabase.child("status").setValue("cancel");
    }

    public static void addNotes(String tripId , List<String> Notes )
    {
        for (int i=0 ; i<Notes.size() ; i++) {
            mDatabase.child("Trips").child(userId).child(tripId).child("Notes").push().setValue(Notes.get(i));
        }
    }


/*
    public static List<String> getTripNotes(String tripId)
    {
        final List<String> Notes = new ArrayList<>();
        DatabaseReference newsRef = mDatabase.child("Notes").child(tripId);
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("tag", databaseError.getMessage());
            }
        });
        return null;
    }
*/

}
