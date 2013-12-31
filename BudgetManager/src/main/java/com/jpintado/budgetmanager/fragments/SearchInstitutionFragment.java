package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.adapter.InstitutionAdapter;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.JSONArrayResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchInstitutionFragment extends Fragment {

    private static final String DEBUG_TAG = "[SearchInstitutionFragment]";

    private SearchInstitutionFragmentCallbacks mCallbacks;
    private EditText searchEditText;
    private ImageButton searchImageButton;
    private ListView resultsListView;
    private InstitutionAdapter institutionAdapter;
    private ArrayList<JSONObject> institutionArrayList = new ArrayList<JSONObject>();

    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String query = searchEditText.getText().toString();
            if (query.length() != 0) {
                BMLibrary.institutionProvider.searchInstitution(query, searchInstitutionResponseHandler);
            } else {
                Toast.makeText(getActivity(), getString(R.string.txt_invalid_institution_name), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private JSONArrayResponseHandler searchInstitutionResponseHandler = new JSONArrayResponseHandler()
    {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(JSONArray institutionJSONArray) {
            institutionArrayList.clear();
            super.onSuccess(institutionJSONArray);
            for (int i=0; i<institutionJSONArray.length(); i++) {
                try {
                    institutionArrayList.add(institutionJSONArray.getJSONObject(i));
                } catch (Exception ex) {
                    Log.e(DEBUG_TAG, ex.getMessage());
                }
            }
            institutionAdapter.notifyDataSetChanged();
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

    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                String institutionId = institutionAdapter.getItem(position).getString("id");
                String institutionName = institutionAdapter.getItem(position).getString("name");
                mCallbacks.onInstitutionClick(institutionName, institutionId);
            } catch (Exception ex) {
                Toast.makeText(getActivity(), getString(R.string.msg_unable_process_information), Toast.LENGTH_LONG).show();
            }
        }
    };

    public static Fragment newInstance() {
        return new SearchInstitutionFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof SearchInstitutionFragmentCallbacks)
            mCallbacks = (SearchInstitutionFragmentCallbacks) getParentFragment();
        else if (activity instanceof SearchInstitutionFragmentCallbacks)
            mCallbacks = (SearchInstitutionFragmentCallbacks) activity;
        else
            throw new ClassCastException(DEBUG_TAG + " Parent container must implement SearchInstitutionFragmentCallbacks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_institution, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListener();
        setUpListViewAdapter();
    }

    private void bindUIElements(View view) {
        searchEditText = (EditText) view.findViewById(R.id.search_institution_editText);
        searchImageButton = (ImageButton) view.findViewById(R.id.search_institution_imageButton);
        resultsListView = (ListView) view.findViewById(R.id.search_institution_listView);
    }

    private void setUpListener() {
        searchImageButton.setOnClickListener(searchClickListener);
        resultsListView.setOnItemClickListener(listViewItemClickListener);
    }

    private void setUpListViewAdapter() {
        institutionAdapter = new InstitutionAdapter(getActivity(), institutionArrayList);
        resultsListView.setAdapter(institutionAdapter);
    }

    public static interface SearchInstitutionFragmentCallbacks {
        void onInstitutionClick(String name, String id);
    }
}
