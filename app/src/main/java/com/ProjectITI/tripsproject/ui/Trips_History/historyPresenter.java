package com.ProjectITI.tripsproject.ui.Trips_History;

import com.ProjectITI.tripsproject.Model.TripDao;

public class historyPresenter implements historyContract.PresenterInterface  {
    @Override
    public void getAllData() {
        TripDao.getAllData("allstatus");
    }
}
