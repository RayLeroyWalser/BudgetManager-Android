package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.adapter.InstitutionCredentialsAdapter;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.InstitutionCredentialsListResponseHandler;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.util.UIUtils;

import java.util.ArrayList;

public class InstitutionListFragment extends Fragment {
    //region Constants
    private InstitutionListFragmentCallbacks mCallbacks;
    private ListView institutionListView;
    private InstitutionCredentialsAdapter institutionCredentialsAdapter;
    private ArrayList<InstitutionCredentials> institutionCredentialsArrayList = new ArrayList<InstitutionCredentials>();
    //endregion

    private InstitutionCredentialsListResponseHandler institutionCredentialsListResponseHandler = new InstitutionCredentialsListResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            UIUtils.showLoadingDialog(getChildFragmentManager(), getString(R.string.txt_loading_institution_information));
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
            Toast.makeText(getActivity(), getString(R.string.msg_unable_retrieve_institution_information), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            UIUtils.dismissLoadingDialog(getChildFragmentManager());
        }
    };

    private AdapterView.OnItemClickListener institutionListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallbacks.onInstitutionItemClicked(institutionCredentialsAdapter.getItem(position));
        }
    };

    public static InstitutionListFragment newInstance() {
        return new InstitutionListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof InstitutionListFragmentCallbacks)
            mCallbacks = (InstitutionListFragmentCallbacks) getParentFragment();
        else if (activity instanceof InstitutionListFragmentCallbacks)
            mCallbacks = (InstitutionListFragmentCallbacks) activity;
        else
            throw new ClassCastException("Parent container must implement InstitutionListFragmentCallbacks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_institution_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListeners();
        setUpListViewAdapter();

        BMLibrary.institutionProvider.getInstitutionList(institutionCredentialsListResponseHandler);
    }

    private void bindUIElements(View view) {
        institutionListView = (ListView) view.findViewById(R.id.institution_listView);
    }

    private void setUpListeners() {
        institutionListView.setOnItemClickListener(institutionListViewItemClickListener);
    }

    private void setUpListViewAdapter() {
        institutionCredentialsAdapter = new InstitutionCredentialsAdapter(getActivity(), institutionCredentialsArrayList);
        institutionListView.setAdapter(institutionCredentialsAdapter);
    }

    public static interface InstitutionListFragmentCallbacks {
        void onInstitutionItemClicked(InstitutionCredentials institutionCredentials);
    }
}
