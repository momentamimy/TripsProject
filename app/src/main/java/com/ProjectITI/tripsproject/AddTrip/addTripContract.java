package com.ProjectITI.tripsproject.AddTrip;

import com.ProjectITI.tripsproject.Model.Trip;

public interface addTripContract {
    interface PresenterInterface {

            void addTrip(Trip trip);
        }

        interface ViewInterface {

            void returnToMain();

            void displayMessage(String message);

            void displayError(String message);

        }
    }


