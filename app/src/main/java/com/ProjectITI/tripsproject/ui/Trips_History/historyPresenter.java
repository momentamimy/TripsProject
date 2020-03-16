package com.ProjectITI.tripsproject.ui.Trips_History;

import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;

import java.util.List;

public class historyPresenter implements historyContract.PresenterInterface  {

    historyContract.ViewInterface view;

    public historyPresenter(historyContract.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void getHistoryList() {
        TripDao.getHistoryData(this);
    }

    @Override
    public void setData(List<Trip> trips) {
        view.setHistoryData(trips);
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
