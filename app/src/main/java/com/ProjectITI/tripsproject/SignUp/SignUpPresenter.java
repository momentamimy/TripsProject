package com.ProjectITI.tripsproject.SignUp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ProjectITI.tripsproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.victor.loading.rotate.RotateLoading;

public class SignUpPresenter implements SignUpContract.PresenterInterface{

    SignUpContract.ViewInterface viewInterface;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    AppCompatActivity activity;

    Dialog loadingDialog;

    public SignUpPresenter(SignUpContract.ViewInterface viewInterface, AppCompatActivity activity) {
        this.viewInterface = viewInterface;
        this.activity=activity;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void createUser(final String email, final String userName, final String pass) {
        MyCustomDialog();
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

                            Toast.makeText(activity, "Registered Succesfully",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(activity, "You Are Already Registered",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("CreationFailed:", "" + task.getException().getMessage());


                        }
                        loadingDialog.dismiss();
                    }

                });
    }


    public void MyCustomDialog() {
        loadingDialog = new Dialog(activity);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        Window window = loadingDialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RotateLoading rotateLoading;
        rotateLoading = loadingDialog.findViewById(R.id.rotateloading);
        rotateLoading.start();
        loadingDialog.show();
    }
}
