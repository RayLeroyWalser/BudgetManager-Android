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

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.util.ConfirmPasswordEditorActionListener;
import com.jpintado.budgetmanager.util.EmailEditorActionListener;
import com.jpintado.budgetmanager.util.EmptyEditorActionListener;
import com.jpintado.budgetmanager.util.UIUtils;

public class RegistrationFragment extends Fragment {

    //region Variables
    private RegistrationFragmentInterface mCallbacks;
    private EditText usernameEditText;
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

    private StringResponseHandler registrationResponseHandler = new StringResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            UIUtils.showLoadingDialog(getChildFragmentManager(), getString(R.string.txt_registering));
        }

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            clearFields();
            mCallbacks.onRegistered();
        }

        @Override
        public void onFailure(String message) {
            super.onFailure(message);
            Toast.makeText(getActivity(), getString(R.string.msg_unable_register_error), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtils.dismissLoadingDialog(getChildFragmentManager());
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
        usernameEditText          = (EditText) view.findViewById(R.id.registration_username_editText);
        emailEditText             = (EditText) view.findViewById(R.id.registration_email_editText);
        passwordEditText          = (EditText) view.findViewById(R.id.registration_password_editText);
        confirmPasswordEditText   = (EditText) view.findViewById(R.id.registration_confirm_password_editText);
        registerButton            = (Button)   view.findViewById(R.id.registration_register_button);
        alreadyHasAccountTextView = (TextView) view.findViewById(R.id.registration_already_has_account_textView);
    }

    private void setUpListeners() {
        registerButton.setOnClickListener(registerButtonClickListener);
        alreadyHasAccountTextView.setOnClickListener(alreadyHasAccountClickListener);

        usernameEditText.setOnEditorActionListener(new EmptyEditorActionListener(getActivity(), usernameEditText));
        emailEditText.setOnEditorActionListener(new EmailEditorActionListener(getActivity(), emailEditText));
        passwordEditText.setOnEditorActionListener(new EmptyEditorActionListener(getActivity(), passwordEditText));
        confirmPasswordEditText.setOnEditorActionListener(new CustomConfirmPasswordEditorActionListener(getActivity(), confirmPasswordEditText, passwordEditText));
    }

    private void formAction() {
        if (validFields()) {
            BMLibrary.credentialManager.register(
                    usernameEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    registrationResponseHandler);
        } else {
            Toast.makeText(getActivity(), getString(R.string.txt_invalid_fields_error), Toast.LENGTH_LONG).show();
        }
    }

    public boolean validFields() {
        return !usernameEditText.getText().toString().trim().equals("")
                && !emailEditText.getText().toString().trim().equals("")
                && !passwordEditText.getText().toString().trim().equals("")
                && !confirmPasswordEditText.getText().toString().trim().equals("")
                && confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString());
    }

    private void clearFields() {
        usernameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
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
                return false;
            }
            return true;
        }
    }
}
