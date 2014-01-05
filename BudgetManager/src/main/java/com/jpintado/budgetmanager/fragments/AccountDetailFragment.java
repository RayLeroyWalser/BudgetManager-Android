package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.model.Account;

public class AccountDetailFragment extends Fragment {
    //region Constants
    private static final String BUNDLE_ARG_ACCOUNT = "bundle_arg_account";
    //endregion

    //region Variables
    private Account account;
    private TextView institutionNameTextView;
    private TextView descriptionTextView;
    private TextView numberTextView;
    //endregion

    public static AccountDetailFragment newInstance(Account account) {
        AccountDetailFragment accountDetailFragment = new AccountDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ARG_ACCOUNT, account);
        accountDetailFragment.setArguments(args);

        return accountDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = (Account) getArguments().getSerializable(BUNDLE_ARG_ACCOUNT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpView();
    }

    private void bindUIElements(View view) {
        institutionNameTextView = (TextView) view.findViewById(R.id.account_detail_institution_name_textView);
        descriptionTextView = (TextView) view.findViewById(R.id.account_detail_description_textView);
        numberTextView = (TextView) view.findViewById(R.id.account_detail_number_textView);
    }

    private void setUpView() {
        institutionNameTextView.setText(account.institution.name);
        descriptionTextView.setText(account.description);
        numberTextView.setText(account.number);
    }
}
