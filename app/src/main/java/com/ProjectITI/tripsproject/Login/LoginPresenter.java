package com.ProjectITI.tripsproject.Login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ProjectITI.tripsproject.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter implements LoginContract.PresenterInterface{

    LoginContract.ViewInterface viewInterface;
    Activity activity;
    public static FirebaseAuth mAuth;
    public static DatabaseReference databaseReference;
    public static String userId ;


    public LoginPresenter(LoginContract.ViewInterface viewInterface, Activity activity) {
        this.viewInterface = viewInterface;
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void Login(final String email, final String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    databaseReference.child("users").child(mAuth.getUid()).child("UserName").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                userId = mAuth.getUid();
                                Log.v("tag",mAuth.getUid());
                                viewInterface.LoginSucceed(email,pass,dataSnapshot.getValue(String.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(activity,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
