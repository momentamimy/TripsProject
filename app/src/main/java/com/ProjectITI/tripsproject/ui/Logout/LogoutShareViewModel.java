package com.ProjectITI.tripsproject.ui.Logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LogoutShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}