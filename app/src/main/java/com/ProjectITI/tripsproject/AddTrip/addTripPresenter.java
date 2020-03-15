package com.ProjectITI.tripsproject.AddTrip;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;

public class addTripPresenter implements addTripContract.PresenterInterface {

    addTripContract.ViewInterface view;
    public addTripPresenter() {

    }

    @Override
    public void addTrip(Trip trip) {
        ArrayList<String> empty_notes = new ArrayList<>();
        TripDao.AddTrip(trip,empty_notes);
    }
}