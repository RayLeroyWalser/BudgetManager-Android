package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.R;

public class InstitutionAddProcessFragment extends Fragment
        implements SearchInstitutionFragment.SearchInstitutionFragmentCallbacks,
                   AddInstitutionFragment.AddInstitutionFragmentCallbacks {

    private static final String DEBUG_TAG = "[InstitutionAddProcessFragment]";

    private InstitutionAddProcessFragmentCallbacks mCallbacks;

    public static Fragment newInstance() {
        return new InstitutionAddProcessFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof InstitutionAddProcessFragmentCallbacks)
            mCallbacks = (InstitutionAddProcessFragmentCallbacks) getParentFragment();
        else if (activity instanceof InstitutionAddProcessFragmentCallbacks)
            mCallbacks = (InstitutionAddProcessFragmentCallbacks) activity;
        else
            throw new ClassCastException(DEBUG_TAG + " Parent container must implement SearchInstitutionFragmentCallbacks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_institution_add_process, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, SearchInstitutionFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onInstitutionClick(String name, String id) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, AddInstitutionFragment.newInstance(name, id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onInstitutionAdded() {
        mCallbacks.onInstitutionAdded(this);
    }

    public static interface InstitutionAddProcessFragmentCallbacks {
        void onInstitutionAdded(Fragment fragment);
    }
}