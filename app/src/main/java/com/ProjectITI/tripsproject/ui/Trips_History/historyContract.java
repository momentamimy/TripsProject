package com.ProjectITI.tripsproject.ui.Trips_History;

import java.util.ArrayList;

public interface historyContract {
    interface PresenterInterface {
        void getAllData();
    }

    interface ViewInterface {
        void showNotes(ArrayList<String> trip_notes);

        void delete(String tripid);


    }
}
