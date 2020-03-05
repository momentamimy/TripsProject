package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ProjectITI.tripsproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView v;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<DataRow> input = new ArrayList<>();
    private LinearLayout noTrips;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //  final TextView textView = root.findViewById(R.id.text_home);
        noTrips = root.findViewById(R.id.no_trips_layout);
        v = (RecyclerView) root.findViewById(R.id.recycleView_id);

        SetDataInRcycleView();
        return root;

    }

    private void SetDataInRcycleView() {
        v.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        v.setLayoutManager(layoutManager);
        // DataRow(String tripName, String from, String to, String data, String time, long img)
       input = Arrays.asList(new DataRow("test1", "Location 1 ", "test1", "1-1-2020", "10:30")
                , new DataRow("test Name Of the Trip2", "Locatuuuuuuuuuuuuuuion 1 ", "tesioooooioiopokpopot1", "1-1-2020", "10:30")
                , new DataRow("test3", "Location 2 ", "test2", "1-1-2020", "10:30")
                , new DataRow("test4", "Location 3 ", "test3", "1-1-2020", "10:30")
                , new DataRow("test5", "Location 4 ", "test4", "1-1-2020", "10:30")
                , new DataRow("test6", "Location 5 ", "test5", "1-1-2020", "10:30")
                , new DataRow("test7", "Location 6 ", "test6", "1-1-2020", "10:30"));



        if (input.size() == 0) {
            noTrips.setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
        } else {

            noTrips.setVisibility(View.INVISIBLE);
            v.setVisibility(View.VISIBLE);
            mAdapter = new RecycleViewAdapter(getContext(), input);
            v.setAdapter(mAdapter);

        }
    }
}