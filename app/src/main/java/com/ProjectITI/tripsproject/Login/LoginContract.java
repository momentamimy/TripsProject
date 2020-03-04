package com.ProjectITI.tripsproject.Login;

public interface LoginContract {
    interface PresenterInterface
    {
        void Login(String email,String pass);
    }

    interface ViewInterface
    {
        void LoginSucceed(String email,String pass,String userName);
    }
}
