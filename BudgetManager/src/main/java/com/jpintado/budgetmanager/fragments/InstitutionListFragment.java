package com.jpintado.budgetmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.adapter.InstitutionCredentialsAdapter;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.InstitutionCredentialsListResponseHandler;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

import java.util.ArrayList;

public class InstitutionListFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "bundle_position";

    private ListView institutionListView;
    private InstitutionCredentialsAdapter institutionCredentialsAdapter;
    private ArrayList<InstitutionCredentials> institutionCredentialsArrayList = new ArrayList<InstitutionCredentials>();

    private InstitutionCredentialsListResponseHandler institutionCredentialsListResponseHandler = new InstitutionCredentialsListResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(ArrayList<InstitutionCredentials> response) {
            super.onSuccess(response);
            institutionCredentialsArrayList.clear();
            institutionCredentialsArrayList.addAll(response);
            institutionCredentialsAdapter.notifyDataSetChanged();
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

    public static InstitutionListFragment newInstance(int position) {
        InstitutionListFragment institutionListFragment = new InstitutionListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        institutionListFragment.setArguments(bundle);

        return institutionListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_institution_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListViewAdapter();

        BMLibrary.institutionProvider.getInstitutionList(institutionCredentialsListResponseHandler);
    }

    private void bindUIElements(View view) {
        institutionListView = (ListView) view.findViewById(R.id.institution_listView);
    }

    private void setUpListViewAdapter() {
        institutionCredentialsAdapter = new InstitutionCredentialsAdapter(getActivity(), institutionCredentialsArrayList);
        institutionListView.setAdapter(institutionCredentialsAdapter);
    }
}
