package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class upcomingPresenter implements upcomingContract.PresenterInterface {


    upcomingContract.ViewInterface view;


    public upcomingPresenter(upcomingContract.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void addTrip(Trip trip , ArrayList<String> notes,Calendar calendar) {
        TripDao.AddTrip(trip,notes,calendar);

    @Override
    public void getupcomingList() {
        TripDao.getAllData(this);
    }

    @Override
    public void setData(List<Trip> trips) {
        view.setupcomingData(trips);
    }

    @Override
    public void onDelete(String tripId) {
        TripDao.deleteTrip(tripId);
    }

    @Override
    public void stop() {
        view = null;
    }


}
