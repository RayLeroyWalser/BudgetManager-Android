package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

public class InstitutionsFragment extends Fragment
        implements InstitutionListFragment.InstitutionListFragmentCallbacks {

    private static final String ARG_SECTION_NUMBER = "bundle_position";

    public static InstitutionsFragment newInstance(int position) {
        InstitutionsFragment institutionsFragment = new InstitutionsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        institutionsFragment.setArguments(bundle);

        return institutionsFragment;
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
    }
}