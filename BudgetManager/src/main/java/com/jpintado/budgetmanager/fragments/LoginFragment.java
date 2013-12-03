package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.util.EmptyEditorActionListener;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    //region Variables
    private LoginFragmentCallbacks mCallbacks;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerAccountTextView;
    //endregion

    //region Listeners
    private View.OnClickListener loginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            formAction();
        }
    };
    private View.OnClickListener registerAccountClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCallbacks.onRegisterClick();
        }
    };
    private Response.Listener<String> loginSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            emailEditText.setText("");
            passwordEditText.setText("");
            mCallbacks.onLoggedIn();
        }
    };

    private Response.ErrorListener loginFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getActivity(), getString(R.string.msg_unable_login_error), Toast.LENGTH_LONG).show();
        }
    };
    //endregion

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (LoginFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement LoginFragmentCallbacks.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListeners();
    }

    private void bindUIElements(View view) {
        emailEditText = (EditText) view.findViewById(R.id.login_email_editText);
        passwordEditText        = (EditText) view.findViewById(R.id.login_password_editText);
        loginButton             = (Button)   view.findViewById(R.id.login_button);
        registerAccountTextView = (TextView) view.findViewById(R.id.login_register_account_textView);
    }

    private void setUpListeners() {
        loginButton.setOnClickListener(loginButtonClickListener);
        registerAccountTextView.setOnClickListener(registerAccountClickListener);

        emailEditText.setOnEditorActionListener(new EmptyEditorActionListener(getActivity(), emailEditText));
        passwordEditText.setOnEditorActionListener(new CustomEmptyEditorActionListener(getActivity(), passwordEditText));
    }


    private void formAction() {
        if (validFields()) {
            BMLibrary.credentialManager.login(
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    loginSuccessListener,
                    loginFailureListener);
        } else {
            Toast.makeText(getActivity(), getString(R.string.txt_invalid_fields_error), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validFields() {
        return !emailEditText.getText().toString().trim().equals("")
                && !passwordEditText.getText().toString().trim().equals("");
    }

    public static interface LoginFragmentCallbacks {
        void onLoggedIn();
        void onRegisterClick();
    }

    private class CustomEmptyEditorActionListener extends EmptyEditorActionListener implements TextView.OnEditorActionListener {

        public CustomEmptyEditorActionListener(Context context, EditText editText) {
            super(context, editText);
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (!super.onEditorAction(textView, actionId, keyEvent)) {
                formAction();
            }
            return false;
        }
    }
}
