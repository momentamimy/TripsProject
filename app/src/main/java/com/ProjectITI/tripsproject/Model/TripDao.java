package com.ProjectITI.tripsproject.Model;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ProjectITI.tripsproject.HomeScreen;
import com.ProjectITI.tripsproject.ui.Trips_History.HistoryFragment;
import com.ProjectITI.tripsproject.ui.Upcoming_Trips.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripDao {
    static Application application = HomeScreen.application;
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    static String userId = FirebaseAuth.getInstance().getUid();

    public static void AddTrip(Trip trip , ArrayList<String> notes)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("Trips").child(userId).push().getKey();
        Log.i("tag" , " key : "+key);
        mDatabase.child("Trips").child(userId).child(key).setValue(trip);

        if(notes.size() !=0 || notes != null)
        {
            TripDao.addNotes(key,notes);
        }
        mDatabase.keepSynced(true);


        /*
        mDatabase.child("Trips").child(userId).child(key).child("time").setValue(trip.getTime());
        mDatabase.child("Trips").child(userId).child(key).child("date").setValue(trip.getDate());
        mDatabase.child("Trips").child(userId).child(key).child("name").setValue(trip.getName());
        mDatabase.child("Trips").child(userId).child(key).child("from").setValue(trip.getFrom());
        mDatabase.child("Trips").child(userId).child(key).child("to").setValue(trip.getTo());
        mDatabase.child("Trips").child(userId).child(key).child("status").setValue(trip.getStatus());
        mDatabase.child("Trips").child(userId).child(key).child("type").setValue(trip.getType());
        mDatabase.child("Trips").child(userId).child(key).child("repeat").setValue(trip.getRepeat());

         */
    }

    public static void getAllData(final String trip_status)
    {
        final List<Trip> UpcomingData = new ArrayList<>();
        final List<Trip> HistoryData = new ArrayList<>();

        DatabaseReference newsRef = mDatabase.child("Trips").child(userId);

        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  UpcomingData.clear();
                    HistoryData.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("tag","for loop");
                    String name = ds.child("name").getValue(String.class);
                    String from = ds.child("from").getValue(String.class);
                    String to = ds.child("to").getValue(String.class);
                    String status = ds.child("status").getValue(String.class);
                    String time = ds.child("time").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    String type = ds.child("type").getValue(String.class);
                    String repeat = ds.child("repeat").getValue(String.class);
                    String id = ds.getKey();
                    Map<String, String> td ;
                    ArrayList<String> map;
                    try {
                        td = (HashMap<String, String>) ds.child("Notes").getValue();
                        map = new ArrayList<>(td.values());
                    }catch (Exception e)
                    {
                        td = new HashMap<>();
                        map = new ArrayList<>();
                    }
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
                    if(status.equals("upcoming")) {
                        UpcomingData.add(new Trip(id,name, from, to, time, date, status, type, repeat,map));
                    }else{
                        HistoryData.add(new Trip(id,name, from, to, time, date, status, type, repeat,map));
                    }
//twoice
            }
                if(trip_status.equals("upcoming")) {
                    HomeFragment.SetDataInRcycleView(UpcomingData);
                }else if (trip_status.equals("allstatus")){
                    HistoryFragment.SetDataInRcycleView(HistoryData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("tag", databaseError.getMessage());
            }
        });

        if(trip_status.equals("upcoming")) {
            HomeFragment.SetDataInRcycleView(UpcomingData);
        }else {
            HistoryFragment.SetDataInRcycleView(HistoryData);
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
        mDatabase.child("status").setValue("Cancel");
    }

    public static void addNotes(String tripId , List<String> Notes )
    {
        mDatabase.child("Trips").child(userId).child(tripId).child("Notes").removeValue();
        for (int i=0 ; i<Notes.size() ; i++) {
            mDatabase.child("Trips").child(userId).child(tripId).child("Notes").push().setValue(Notes.get(i));
        }
    }

    public static void EditTrip (String id , Trip trip)
    {
        String key = id;
        Log.i("tag","key od edit : "+key);
        mDatabase.child("Trips").child(userId).child(key).setValue(trip);
        /*
        mDatabase.child("Trips").child(userId).child(key).child("name").setValue(trip.getName());
        mDatabase.child("Trips").child(userId).child(key).child("from").setValue(trip.getFrom());
        mDatabase.child("Trips").child(userId).child(key).child("to").setValue(trip.getTo());
        mDatabase.child("Trips").child(userId).child(key).child("status").setValue(trip.getStatus());
        mDatabase.child("Trips").child(userId).child(key).child("type").setValue(trip.getType());
        mDatabase.child("Trips").child(userId).child(key).child("repeat").setValue(trip.getRepeat());
        mDatabase.child("Trips").child(userId).child(key).child("time").setValue(trip.getTime());
        mDatabase.child("Trips").child(userId).child(key).child("date").setValue(trip.getDate());

         */
    }
    public static void DoneTrip (String tripId)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Trips").child(userId).child(tripId);
        mDatabase.child("status").setValue("Done");
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
