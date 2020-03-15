package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import com.ProjectITI.tripsproject.Model.Trip;

import java.util.ArrayList;

public interface upcomingContract {
    interface PresenterInterface {
        void addTrip(Trip trip , ArrayList<String> notes);
        void setTripDone(String id);
        void getAllData();
    }

    interface ViewInterface {

        void cancel(String tripId);

        void Notes(String tripid, ArrayList<String> notes);

        void delete(String tripid);

        void gotToMap(String source , String destiaion);

        void displayMessage(String msg);

    }
}
