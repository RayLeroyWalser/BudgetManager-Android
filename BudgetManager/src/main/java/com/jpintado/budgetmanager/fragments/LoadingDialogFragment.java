package com.jpintado.budgetmanager.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.jpintado.budgetmanager.R;

public class LoadingDialogFragment extends DialogFragment {
    //region Constants
    private static final String BUNDLE_ARG_MESSAGE = "bundle_arg_message";
    //endregion

    //region Variables
    private String message;
    //endregion

    public static LoadingDialogFragment newInstance(String message) {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_ARG_MESSAGE, message);
        loadingDialogFragment.setArguments(args);

        return loadingDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getArguments().getString(BUNDLE_ARG_MESSAGE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        return dialog;
    }
}
