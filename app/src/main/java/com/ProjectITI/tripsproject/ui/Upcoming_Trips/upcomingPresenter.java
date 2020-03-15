package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;

public class upcomingPresenter implements upcomingContract.PresenterInterface {
    @Override
    public void addTrip(Trip trip , ArrayList<String> notes) {
        TripDao.AddTrip(trip,notes);

    }

    @Override
    public void setTripDone(String id) {
        TripDao.DoneTrip(id);
    }

    @Override
    public void getAllData() {
        TripDao.getAllData("upcoming");
    }
}
