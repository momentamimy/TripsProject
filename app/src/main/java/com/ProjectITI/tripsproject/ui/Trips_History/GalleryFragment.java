package com.ProjectITI.tripsproject.ui.Trips_History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ProjectITI.tripsproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryFragment extends Fragment {


 // public static int HIEGHT = 0;
 // public static int WIDTH = 0;

  //  private OnFragmentInteractionListener mListener;

    private RecyclerView v;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HistoryDataRow> input = new ArrayList<>();
    private  View noTrips;
    private ConstraintLayout showTripDetails;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

         noTrips = root.findViewById(R.id.history_no_trips_layout);

      /*  galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/
     //  showTripDetails = root.findViewById(R.id.history_showDetails);

        v = (RecyclerView) root.findViewById(R.id.history_recycleView_id);

        SetDataInRcycleView();

        /*
        HIEGHT =  showTripDetails.getHeight();
        WIDTH = showTripDetails.getWidth();
       */
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       /* Fragment childFragment = new showTripsDetails();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.details_fragment, childFragment).commit();*/
    }
    private void SetDataInRcycleView() {
        v.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        v.setLayoutManager(layoutManager);
     //   (String tripName, String from, String to, String data, String time, String status)
        input = Arrays.asList(new HistoryDataRow("test1", "Location 1 ", "test1", "1-1-2020", "10:30","Done")
                , new HistoryDataRow("test Name Of the Tdfklgndlfkgndklfgndlfgndklfgndlrip2", "Locatuuuudfldsfkdlfkdl  kf;blskdf kefklf jknklnrgr erfuuuuuuuuuuion 1 ", "tesiooood kkjlkj kjrtklwejjr,. ndkfnkloioiopokpopot1", "1-1-2020", "10:30","Cancelled")
                , new HistoryDataRow("test3", "Location 2 ", "test2", "1-1-2020", "10:30","Done")
                , new HistoryDataRow("test4", "Location 3 ", "test3", "1-1-2020", "10:30","Cancelled")
                , new HistoryDataRow("test5", "Location 4 ", "test4", "1-1-2020", "10:30","Done")
                , new HistoryDataRow("test6", "Location 5 ", "test5", "1-1-2020", "10:30","Cancelled")
                , new HistoryDataRow("test7", "Location 6 ", "test6", "1-1-2020", "10:30","Done"));



        if (input.size() == 0) {
            noTrips.setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
        } else {
            noTrips.setVisibility(View.INVISIBLE);
            v.setVisibility(View.VISIBLE);
            mAdapter = new RV_Trips_HistoryAdapter(getContext(), input);
            v.setAdapter(mAdapter);

        }

    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }*/
}