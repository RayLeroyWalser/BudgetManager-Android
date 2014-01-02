package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

public class AccountAddProcessFragment extends Fragment
        implements InstitutionListFragment.InstitutionListFragmentCallbacks {

    public static AccountAddProcessFragment newInstance() {
        return new AccountAddProcessFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_frame_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, InstitutionListFragment.newInstance())
                .commit();
    }

    @Override
    public void onInstitutionItemClicked(InstitutionCredentials institutionCredentials) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, SearchAccountFragment.newInstance(institutionCredentials))
                .commit();
    }
}
