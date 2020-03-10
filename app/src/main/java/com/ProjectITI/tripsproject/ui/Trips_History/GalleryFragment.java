package com.ProjectITI.tripsproject.ui.Trips_History;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    public static Context context = HomeScreen.context;
    public String userId = FirebaseAuth.getInstance().getUid();

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager layoutManager;
    private List<Trip> input = new ArrayList<>();
    private static View noTrips;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        noTrips = root.findViewById(R.id.history_no_trips_layout);

        recyclerView = (RecyclerView) root.findViewById(R.id.history_recycleView_id);
        TripDao.getAllData("allStatus");
        return root;
    }

    public static void SetDataInRcycleView(List<Trip> newData) {

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        if (newData.size() == 0) {
            noTrips.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            noTrips.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter = new RV_Trips_HistoryAdapter(context, newData);
            recyclerView.setAdapter(mAdapter);
        }

    }

}