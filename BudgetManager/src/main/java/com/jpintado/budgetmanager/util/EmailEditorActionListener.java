package com.jpintado.budgetmanager.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailEditorActionListener extends EmptyEditorActionListener implements TextView.OnEditorActionListener {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final String errorMessage;
    private final Pattern pattern;
    private Matcher matcher;

    public EmailEditorActionListener(Context context, EditText editText) {
        this(context, context.getString(R.string.txt_invalid_email_error), editText);
    }

    public EmailEditorActionListener(Context context, String errorMessage, EditText editText) {
        super(context, editText);
        this.errorMessage = errorMessage;
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public EmailEditorActionListener(String emailErrorMessage, String emptyErrorMessage, EditText editText) {
        super(emptyErrorMessage, editText);
        this.errorMessage = emailErrorMessage;
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (!super.onEditorAction(textView, actionId, keyEvent)) {
            if (!isValidEmail(editText.getText().toString().trim())) {
                editText.setError(errorMessage);
                return true;
            } else {
                editText.setError(null);
            }
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String candidate) {
        matcher = pattern.matcher(candidate);
        return matcher.matches();
    }
}
