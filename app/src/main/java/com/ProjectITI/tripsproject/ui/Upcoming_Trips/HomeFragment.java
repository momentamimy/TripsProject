package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class HomeFragment extends Fragment implements upcomingContract.ViewInterface {


  //  private upcomingContract.PresenterInterface presenterInterface;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        noTrips = root.findViewById(R.id.no_trips_layout);
        v = (RecyclerView) root.findViewById(R.id.recycleView_id);
       // presenterInterface = new upcomingPresenter(getView());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        upcomingPresenter main = new upcomingPresenter(this);
        main.getupcomingList();
    }



    public static void SetDataInRcycleView(List<Trip> newData ) {

    }


    @Override
    public void Notes(String tripid, ArrayList<String> notes) {

    }

    @Override
    public void displayMessage(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupcomingData(List<Trip> upcomingtrips) {
        v.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        v.setLayoutManager(layoutManager);

        if (upcomingtrips.size() == 0) {
            NoupcomingTrips();
        } else {
            noTrips.setVisibility(View.INVISIBLE);
            v.setVisibility(View.VISIBLE);
            mAdapter = new RecycleViewAdapter( context , upcomingtrips );
            v.setAdapter(mAdapter);
        }
    }

    @Override
    public void NoupcomingTrips() {
        noTrips.setVisibility(View.VISIBLE);
        v.setVisibility(View.INVISIBLE);
    }
}