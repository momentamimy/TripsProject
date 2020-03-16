package com.ProjectITI.tripsproject.AddTrip;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;

public class addTripPresenter implements addTripContract.PresenterInterface {
    TripDao tripDao ;
    addTripContract.ViewInterface view;

    public addTripPresenter(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    public void addTrip(Trip trip) {
        ArrayList<String> empty_notes = new ArrayList<>();
        tripDao.AddTrip(trip,empty_notes);
    }
}