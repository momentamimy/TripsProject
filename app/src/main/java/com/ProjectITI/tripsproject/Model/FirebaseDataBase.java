package com.ProjectITI.tripsproject.Model;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

abstract class FirebaseDataBase {

    private static DatabaseReference rootRef ;
    public abstract TripDao tripDao();

    public static DatabaseReference getInstance(Application application) {
        if (FirebaseDataBase.rootRef == null) {
             rootRef = FirebaseDatabase.getInstance().getReference();
             return rootRef;
        }

        return rootRef;
    }
}
