package com.ProjectITI.tripsproject.SignUp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPresenter implements SignUpContract.PresenterInterface{

    SignUpContract.ViewInterface viewInterface;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Activity activity;

    public SignUpPresenter(SignUpContract.ViewInterface viewInterface,Activity activity) {
        this.viewInterface = viewInterface;
        this.activity=activity;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void createUser(final String email, final String userName, final String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CreateUser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            databaseReference.child("users").child(user.getUid()).child("Email").setValue(email);
                            databaseReference.child("users").child(user.getUid()).child("UserName").setValue(userName);
                            databaseReference.child("users").child(user.getUid()).child("Password").setValue(pass);
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(activity, "You Are Already Registered",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("CreationFailed:", "" + task.getException().getMessage());


                        }
                    }

                });
    }
}
