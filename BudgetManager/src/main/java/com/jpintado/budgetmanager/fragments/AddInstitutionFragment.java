package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.InstitutionResponseHandler;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Institution;

public class AddInstitutionFragment extends Fragment {

    private static final String BUNDLE_INSTITUTION_NAME = "bundle_institution_name";
    private static final String BUNDLE_INSTITUTION_ID = "bundle_institution_id";
    private String DEBUG_TAG = "[AddInstitutionFragment]";

    private AddInstitutionFragmentCallbacks mCallbacks;
    private Institution institution;
    private Button addButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private InstitutionResponseHandler institutionInfoResponseHandler = new InstitutionResponseHandler()
    {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(Institution institution) {
            super.onSuccess(institution);
            AddInstitutionFragment.this.institution = institution;
            addButton.setEnabled(true);
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

    private StringResponseHandler addInstitutionResponseHandler = new StringResponseHandler()
    {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            mCallbacks.onInstitutionAdded();
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

    private View.OnClickListener addButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (username.length() != 0 && password.length() != 0) {
                BMLibrary.institutionProvider.addInstitution(institution, username, password, addInstitutionResponseHandler);
            } else {
                Toast.makeText(getActivity(), getString(R.string.txt_invalid_fields_error), Toast.LENGTH_LONG).show();
            }
        }
    };

    public static AddInstitutionFragment newInstance(String name, String id) {
        AddInstitutionFragment addInstitutionFragment = new AddInstitutionFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_INSTITUTION_NAME, name);
        args.putString(BUNDLE_INSTITUTION_ID, id);
        addInstitutionFragment.setArguments(args);

        return addInstitutionFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(getParentFragment() instanceof AddInstitutionFragmentCallbacks)
            mCallbacks = (AddInstitutionFragmentCallbacks) getParentFragment();
        else if (activity instanceof AddInstitutionFragmentCallbacks)
            mCallbacks = (AddInstitutionFragmentCallbacks) activity;
        else
            throw new ClassCastException(DEBUG_TAG + " Parent container must implement SearchInstitutionFragmentCallbacks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_institution, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);
        setUpListeners();
        BMLibrary.institutionProvider.getInstitutionInfo(getArguments().getString(BUNDLE_INSTITUTION_ID), institutionInfoResponseHandler);
    }

    private void bindUIElements(View view) {
        addButton = (Button) view.findViewById(R.id.add_institution_button);
        usernameEditText = (EditText) view.findViewById(R.id.add_institution_username_editText);
        passwordEditText = (EditText) view.findViewById(R.id.add_institution_password_editText);
    }

    private void setUpListeners() {
        addButton.setOnClickListener(addButtonClickListener);
    }

    public static interface AddInstitutionFragmentCallbacks {
        void onInstitutionAdded();
    }
}
