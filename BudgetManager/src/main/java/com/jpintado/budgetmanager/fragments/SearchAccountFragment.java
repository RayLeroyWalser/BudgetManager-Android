package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

import java.util.ArrayList;

public class SearchAccountFragment extends Fragment {

    private static final String BUNDLE_ARG_INSTITUTION_CREDENTIALS = "bundle_arg_institution_credential";

    private AccountListResponseHandler searchAccountResponseHandler = new AccountListResponseHandler() {
        @Override
        public void onSuccess(ArrayList<Account> response) {
            super.onSuccess(response);
        }
    };

    public static SearchAccountFragment newInstance(InstitutionCredentials institutionCredentials) {
        SearchAccountFragment searchAccountFragment = new SearchAccountFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ARG_INSTITUTION_CREDENTIALS, institutionCredentials);
        searchAccountFragment.setArguments(args);

        return searchAccountFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_frame_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InstitutionCredentials institutionCredentials = (InstitutionCredentials) getArguments().getSerializable(BUNDLE_ARG_INSTITUTION_CREDENTIALS);
        BMLibrary.accountProvider.searchAccount(institutionCredentials, searchAccountResponseHandler);
    }
}
