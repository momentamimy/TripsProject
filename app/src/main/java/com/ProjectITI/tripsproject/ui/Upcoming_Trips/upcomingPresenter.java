package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;
import java.util.List;

public class upcomingPresenter implements upcomingContract.PresenterInterface {


    upcomingContract.ViewInterface view;


    public upcomingPresenter(upcomingContract.ViewInterface view) {
        this.view = view;
    }

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
