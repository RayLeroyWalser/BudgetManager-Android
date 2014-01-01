package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.R;

public class AccountListFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "bundle_position";

    public static AccountListFragment newInstance(int position) {
        AccountListFragment accountListFragment = new AccountListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        accountListFragment.setArguments(bundle);

        return accountListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_list, container, false);
    }
}
