package com.ProjectITI.tripsproject.AddTrip;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.ArrayList;
import java.util.Calendar;

public class addTripPresenter implements addTripContract.PresenterInterface {

    addTripContract.ViewInterface view;
    public addTripPresenter() {

    }

    @Override
    public String  addTrip(Trip trip, Calendar calendar) {
        ArrayList<String> empty_notes = new ArrayList<>();
        return TripDao.AddTrip(trip,empty_notes,calendar);
    }
}