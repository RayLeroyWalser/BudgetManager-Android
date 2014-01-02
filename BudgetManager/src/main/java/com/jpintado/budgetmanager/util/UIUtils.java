package com.jpintado.budgetmanager.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jpintado.budgetmanager.fragments.LoadingDialogFragment;

public class UIUtils {
    //region Constants
    final static String LOADING_DIALOG_TAG = "progress_dialog";
    //endregion

    public static void showLoadingDialog(FragmentManager fm, String message) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(LOADING_DIALOG_TAG);

        if (prev != null) {
            ft.remove(prev);
        }

        LoadingDialogFragment newFragment = LoadingDialogFragment.newInstance(message);
        newFragment.show(ft, LOADING_DIALOG_TAG);
    }

    public static void dismissLoadingDialog(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(LOADING_DIALOG_TAG);

        if (prev != null) {
            ft.remove(prev);
            ft.commit();
        }
    }
}
