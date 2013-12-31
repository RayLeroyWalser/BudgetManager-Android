package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.activity.MainActivity;
import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "bundle_position";

    private AccountListResponseHandler accountListResponseHandler = new AccountListResponseHandler()
    {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(ArrayList<Account> response) {
            super.onSuccess(response);
        }

        @Override
        public void onFailure(String message) {
            super.onFailure(message);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    };

    public static HomeFragment newInstance(int position) {
        HomeFragment homeFragment = new HomeFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        homeFragment.setArguments(bundle);

        return homeFragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BMLibrary.institutionProvider.getAccountList(accountListResponseHandler);
    }
}
