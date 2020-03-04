package com.ProjectITI.tripsproject.SignUp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ProjectITI.tripsproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignupFragment extends Fragment implements SignUpContract.ViewInterface{

    private OnFragmentInteractionListener mListener;

    Button loginButton;
    Button signupButton;

    EditText UserNameEditText;
    EditText EmailEditText;
    EditText PassEditText;
    EditText ConfirmPassEditText;

    SignUpContract.PresenterInterface presenterInterface;

    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        setupPresinter();
        setupView(view);

        return view;
    }

    public void setupView(View view)
    {
        UserNameEditText = view.findViewById(R.id.UserName);;
        EmailEditText = view.findViewById(R.id.Email);
        PassEditText = view.findViewById(R.id.Password);
        ConfirmPassEditText = view.findViewById(R.id.PasswordConfirm);

        signupButton = view.findViewById(R.id.SignUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        loginButton = view.findViewById(R.id.LoginActivity);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.gotoLogin();
            }
        });
    }

    public void setupPresinter()
    {
        presenterInterface = new SignUpPresenter(this,getActivity());
    }

    public void signUp() {
        final String email = EmailEditText.getText().toString();
        final String pass = PassEditText.getText().toString();
        final String userName = UserNameEditText.getText().toString();

        presenterInterface.createUser(email,userName,pass);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void gotoLogin();
    }
}
