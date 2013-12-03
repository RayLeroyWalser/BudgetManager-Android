package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.jpintado.budgetmanager.util.ConfirmPasswordEditorActionListener;
import com.jpintado.budgetmanager.util.EmailEditorActionListener;
import com.jpintado.budgetmanager.util.EmptyEditorActionListener;

public class RegistrationFragment extends Fragment {

    //region Variables
    private RegistrationFragmentInterface mCallbacks;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private TextView alreadyHasAccountTextView;
    //endregion

    //region Listeners
    private View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            formAction();
        }
    };

    private View.OnClickListener alreadyHasAccountClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCallbacks.onLoginClick();
        }
    };
    private Response.Listener registrationSuccessListener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            emailEditText.setText("");
            passwordEditText.setText("");
            confirmPasswordEditText.setText("");
            mCallbacks.onRegistered();
        }
    };

    private Response.ErrorListener registrationFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getActivity(), getString(R.string.msg_unable_register_error), Toast.LENGTH_LONG).show();
        }
    };
    //endregion

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    public RegistrationFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (RegistrationFragmentInterface) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException("Activity must implement RegistrationFragmentInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListeners();
    }

    private void bindUIElements(View view) {
        emailEditText             = (EditText) view.findViewById(R.id.registration_email_editText);
        passwordEditText          = (EditText) view.findViewById(R.id.registration_password_editText);
        confirmPasswordEditText   = (EditText) view.findViewById(R.id.registration_confirm_password_editText);
        registerButton            = (Button)   view.findViewById(R.id.registration_register_button);
        alreadyHasAccountTextView = (TextView) view.findViewById(R.id.registration_already_has_account_textView);
    }

    private void setUpListeners() {
        registerButton.setOnClickListener(registerButtonClickListener);
        alreadyHasAccountTextView.setOnClickListener(alreadyHasAccountClickListener);

        emailEditText.setOnEditorActionListener(new EmailEditorActionListener(getActivity(), emailEditText));
        passwordEditText.setOnEditorActionListener(new EmptyEditorActionListener(getActivity(), passwordEditText));
        confirmPasswordEditText.setOnEditorActionListener(new CustomConfirmPasswordEditorActionListener(getActivity(), confirmPasswordEditText, passwordEditText));
    }

    private void formAction() {
        if (validFields()) {
            BMLibrary.credentialManager.register(emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    registrationSuccessListener,
                    registrationFailureListener);
        } else {
            Toast.makeText(getActivity(), getString(R.string.txt_invalid_fields_error), Toast.LENGTH_LONG).show();
        }
    }

    public boolean validFields() {
        return !emailEditText.getText().toString().trim().equals("")
                && !passwordEditText.getText().toString().trim().equals("")
                && !confirmPasswordEditText.getText().toString().trim().equals("")
                && confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString());
    }

    public static interface RegistrationFragmentInterface {
        void onRegistered();
        void onLoginClick();

    }
    private class CustomConfirmPasswordEditorActionListener extends ConfirmPasswordEditorActionListener implements TextView.OnEditorActionListener {

        public CustomConfirmPasswordEditorActionListener(Activity activity, EditText confirmPasswordEditText, EditText passwordEditText) {
            super(activity, confirmPasswordEditText, passwordEditText);
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
