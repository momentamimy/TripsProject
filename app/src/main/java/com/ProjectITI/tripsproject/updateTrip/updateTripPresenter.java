package com.ProjectITI.tripsproject.updateTrip;

import com.ProjectITI.tripsproject.AddTrip.addTripContract;
import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

public class updateTripPresenter implements updateTripContract.PresenterInterface {

    public updateTripPresenter() {
    }

    @Override
    public void updateTrip(String id ,Trip trip) {
        TripDao tripDao = new TripDao();
        tripDao.EditTrip(id,trip);
    }
}