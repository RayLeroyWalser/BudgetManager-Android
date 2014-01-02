package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.adapter.AccountAdapter;
import com.jpintado.budgetmanager.library.model.Account;

import java.util.ArrayList;

public class AccountListFragment extends Fragment {

    private static final String BUNDLE_ARG_ACCOUNT_LIST = "bundle_arg_account_list";

    private AccountListFragmentCallbacks mCallbacks;
    private ListView listView;
    private ArrayList<Account> accountArrayList;
    private AccountAdapter accountAdapter;

    private AdapterView.OnItemClickListener listViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallbacks.onAccountClicked(accountAdapter.getItem(position));
        }
    };

    public static AccountListFragment newInstance(ArrayList<Account> accountArrayList) {
        AccountListFragment accountListFragment = new AccountListFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ARG_ACCOUNT_LIST, accountArrayList);
        accountListFragment.setArguments(args);

        return accountListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountArrayList = (ArrayList<Account>) getArguments().getSerializable(BUNDLE_ARG_ACCOUNT_LIST);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof AccountListFragmentCallbacks)
            mCallbacks = (AccountListFragmentCallbacks) getParentFragment();
        else if (activity instanceof AccountListFragmentCallbacks)
            mCallbacks = (AccountListFragmentCallbacks) activity;
        else
            throw new ClassCastException("Parent container must implement AccountListFragmentCallbacks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListeners();
        setUpListViewAdapter();
    }

    private void bindUIElements(View view) {
        listView = (ListView) view.findViewById(R.id.account_listView);
    }

    private void setUpListeners() {
        listView.setOnItemClickListener(listViewClickListener);
    }

    private void setUpListViewAdapter() {
        accountAdapter = new AccountAdapter(getActivity(), accountArrayList);
        listView.setAdapter(accountAdapter);
    }

    public interface AccountListFragmentCallbacks {
        void onAccountClicked(Account account);
    }
}
