package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ProjectITI.tripsproject.HomeScreen;
import com.ProjectITI.tripsproject.Login.LoginPresenter;
import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;
import com.ProjectITI.tripsproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static String userId = FirebaseAuth.getInstance().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Trips");

    public static Context context = HomeScreen.context;

    private static RecyclerView v;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private List<Trip> input = new ArrayList<>();
    private static LinearLayout noTrips;
    static List<Trip> newData = new ArrayList<>();;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //  final TextView textView = root.findViewById(R.id.text_home);
        noTrips = root.findViewById(R.id.no_trips_layout);
        v = (RecyclerView) root.findViewById(R.id.recycleView_id);
        TripDao.getAllData("upcoming");
       // SetDataInRcycleView();
        return root;

    }

    public static void SetDataInRcycleView(List<Trip> newData ) {

     //   getAllData();
     //   TripDao.deleteTrip("test");

        Log.i("tag","newData value " +newData.size());
/*
        newData = Arrays.asList(new Trip("test1", "Location 1 ", "test1", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test Name Of the Trip2", "Locatuuuuuuuuuuuuuuion 1 ", "tesioooooioiopokpopot1", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test3", "Location 2 ", "test2", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test4", "Location 3 ", "test3", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test5", "Location 4 ", "test4", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test6", "Location 5 ", "test5", "1-1-2020", "10:30","none","aaa","bbb")
                , new Trip("test7", "Location 6 ", "test6", "1-1-2020", "10:30","none","aaa","bbb"));
        Log.i("tag","input value " +newData);
*/
        v.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        v.setLayoutManager(layoutManager);

       if (newData.size() == 0) {
            noTrips.setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
        } else {

            noTrips.setVisibility(View.INVISIBLE);
            v.setVisibility(View.VISIBLE);
            mAdapter = new RecycleViewAdapter( context , newData );
            v.setAdapter(mAdapter);

        }
    }
    /*
    public void  getAllData()
    {
        Log.i("tag","getAllData ");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newsRef = rootRef.child("Trips").child(userId);
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String desc = ds.child("name").getValue(String.class);
                    Log.i("tag", "name  : "+desc );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("tag", databaseError.getMessage());
            }
        });

    }
*/
}