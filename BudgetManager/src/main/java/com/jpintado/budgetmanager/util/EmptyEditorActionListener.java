package com.jpintado.budgetmanager.util;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;

public class EmptyEditorActionListener implements TextView.OnEditorActionListener {

    protected final EditText editText;
    private final String errorMessage;

    public EmptyEditorActionListener(Context context, EditText editText) {
        this(context.getString(R.string.txt_empty_field_error), editText);
    }

    public EmptyEditorActionListener(String errorMessage, EditText editText) {
        this.editText = editText;
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_NEXT
                || actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_SEND) {
            if (editText.getText().toString().trim().equalsIgnoreCase("")) {
                editText.setError(errorMessage);
                return true;
            } else {
                editText.setError(null);
            }
        }
        return false;
    }
}
