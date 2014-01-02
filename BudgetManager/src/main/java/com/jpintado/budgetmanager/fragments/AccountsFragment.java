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

import java.util.ArrayList;

public class AccountsFragment extends Fragment
        implements AccountListFragment.AccountListFragmentCallbacks {

    private static final String ARG_SECTION_NUMBER = "bundle_position";
    private AccountListResponseHandler accountListResponseHandler = new AccountListResponseHandler(){
        @Override
        public void onSuccess(ArrayList<Account> response) {
            super.onSuccess(response);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, AccountListFragment.newInstance(response))
                    .commit();
        }
    };

    public static AccountsFragment newInstance(int position) {
        AccountsFragment accountsFragment = new AccountsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        accountsFragment.setArguments(bundle);

        return accountsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_frame_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BMLibrary.accountProvider.getAccountList(accountListResponseHandler);
    }

    @Override
    public void onAccountClicked(Account account) {

    }
}