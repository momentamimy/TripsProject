package com.ProjectITI.tripsproject.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ProjectITI.tripsproject.HomeScreen;
import com.ProjectITI.tripsproject.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment implements LoginContract.ViewInterface {

    Button signUpButton;

    EditText EmailEditText;
    EditText PassEditText;

    Button loginButton;

    LoginContract.PresenterInterface presenterInterface;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupPresenter();
        setupView(view);
        return view;
    }

    public void setupView(View view)
    {
        EmailEditText = view.findViewById(R.id.Email);
        PassEditText = view.findViewById(R.id.Password);

        loginButton = view.findViewById(R.id.Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterInterface.Login(EmailEditText.getText().toString(),PassEditText.getText().toString());
            }
        });

        signUpButton = view.findViewById(R.id.SignUpActivity);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.gotoSignup();
            }
        });
    }

    public void setupPresenter()
    {
        presenterInterface = new LoginPresenter(this,getActivity());
    }

    @Override
    public void LoginSucceed(String email,String pass,String userName) {

        Toast.makeText(getContext(),userName,Toast.LENGTH_LONG).show();
        int LAUNCH_SECOND_ACTIVITY = 1 ;
        Intent i = new Intent(getActivity(), HomeScreen.class);
        getActivity().startActivity(i);
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
        void gotoSignup();
    }
}
