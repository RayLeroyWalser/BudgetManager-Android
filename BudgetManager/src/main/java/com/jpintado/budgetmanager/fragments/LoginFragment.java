package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.content.Context;
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
import com.jpintado.budgetmanager.util.EmptyEditorActionListener;
import com.jpintado.budgetmanager.util.UIUtils;

public class LoginFragment extends Fragment {

    //region Variables
    private LoginFragmentCallbacks mCallbacks;
    private EditText usernameEditText;
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

    private StringResponseHandler loginResponseHandler = new StringResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            UIUtils.showLoadingDialog(getChildFragmentManager(), getString(R.string.txt_login));
        }

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            clearFields();
            mCallbacks.onLoggedIn();
        }

        @Override
        public void onFailure(String message) {
            super.onFailure(message);
            Toast.makeText(getActivity(), getString(R.string.msg_unable_login_error), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtils.dismissLoadingDialog(getChildFragmentManager());
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
        usernameEditText        = (EditText) view.findViewById(R.id.login_email_editText);
        passwordEditText        = (EditText) view.findViewById(R.id.login_password_editText);
        loginButton             = (Button)   view.findViewById(R.id.login_button);
        registerAccountTextView = (TextView) view.findViewById(R.id.login_register_account_textView);
    }

    private void setUpListeners() {
        loginButton.setOnClickListener(loginButtonClickListener);
        registerAccountTextView.setOnClickListener(registerAccountClickListener);

        usernameEditText.setOnEditorActionListener(new EmptyEditorActionListener(getActivity(), usernameEditText));
        passwordEditText.setOnEditorActionListener(new CustomEmptyEditorActionListener(getActivity(), passwordEditText));
    }

    private void formAction() {
        if (validFields()) {
            BMLibrary.credentialManager.login(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    loginResponseHandler);
        } else {
            Toast.makeText(getActivity(), getString(R.string.txt_invalid_fields_error), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validFields() {
        return !usernameEditText.getText().toString().trim().equals("")
                && !passwordEditText.getText().toString().trim().equals("");
    }

    private void clearFields() {
        usernameEditText.setText("");
        passwordEditText.setText("");
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
                return false;
            }
            return true;
        }
    }
}
