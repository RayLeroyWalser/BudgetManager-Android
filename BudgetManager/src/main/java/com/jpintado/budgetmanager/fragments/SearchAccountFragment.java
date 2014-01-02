package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.util.UIUtils;

import java.util.ArrayList;

public class SearchAccountFragment extends Fragment
        implements AccountListFragment.AccountListFragmentCallbacks{

    //region Constants
    private static final String BUNDLE_ARG_INSTITUTION_CREDENTIALS = "bundle_arg_institution_credential";
    //endregion

    //region Variables
    private SearchAccountFragmentCallbacks mCallbacks;
    //endregion

    private AccountListResponseHandler searchAccountResponseHandler = new AccountListResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            UIUtils.showLoadingDialog(getChildFragmentManager(), getString(R.string.txt_searching));
        }

        @Override
        public void onSuccess(ArrayList<Account> response) {
            super.onSuccess(response);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, AccountListFragment.newInstance(response))
                    .commit();
        }

        @Override
        public void onFailure(String message) {
            super.onFailure(message);
            Toast.makeText(getActivity(), getString(R.string.msg_unable_retrieve_accounts), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtils.dismissLoadingDialog(getChildFragmentManager());
        }
    };

    private StringResponseHandler addAccountResponseHandler = new StringResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            UIUtils.showLoadingDialog(getChildFragmentManager(), getString(R.string.txt_adding));
        }

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            mCallbacks.onAccountAdded();
        }

        @Override
        public void onFailure(String message) {
            super.onFailure(message);
            Toast.makeText(getActivity(), getString(R.string.msg_unable_add), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtils.dismissLoadingDialog(getChildFragmentManager());
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof SearchAccountFragmentCallbacks)
            mCallbacks = (SearchAccountFragmentCallbacks) getParentFragment();
        else if (activity instanceof SearchAccountFragmentCallbacks)
            mCallbacks = (SearchAccountFragmentCallbacks) activity;
        else
            throw new ClassCastException("Parent container must implement SearchAccountFragmentCallbacks");
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

    @Override
    public void onAccountClicked(Account account) {
        BMLibrary.accountProvider.addAccount(account, addAccountResponseHandler);
    }

    public interface SearchAccountFragmentCallbacks {
        void onAccountAdded();
    }
}
