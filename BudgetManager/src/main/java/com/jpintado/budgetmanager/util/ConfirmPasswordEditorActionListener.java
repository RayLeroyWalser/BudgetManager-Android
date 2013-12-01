package com.jpintado.budgetmanager.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;

public class ConfirmPasswordEditorActionListener extends EmptyEditorActionListener implements TextView.OnEditorActionListener {

    private final EditText passwordEditText;
    private final String errorMessage;

    public ConfirmPasswordEditorActionListener(Context context, EditText confirmEditText, EditText passwordEditText) {
        this(context, context.getString(R.string.txt_password_mismatch_error), confirmEditText, passwordEditText);
    }

    public ConfirmPasswordEditorActionListener(Context context, String passwordErrorMessage, EditText confirmEditText, EditText passwordEditText) {
        super(context, confirmEditText);
        this.errorMessage = passwordErrorMessage;
        this.passwordEditText = passwordEditText;
    }

    public ConfirmPasswordEditorActionListener(String passwordErrorMessage, String emptyErrorMessage, EditText confirmEditText, EditText passwordEditText) {
        super(emptyErrorMessage, passwordEditText);
        this.errorMessage = passwordErrorMessage;
        this.passwordEditText = passwordEditText;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (!super.onEditorAction(textView, actionId, keyEvent)) {
            if (editText.getText().toString().equalsIgnoreCase(passwordEditText.getText().toString())) {
                editText.setError(errorMessage);
                return true;
            } else {
                editText.setError(null);
            }
        }
        return false;
    }


}
