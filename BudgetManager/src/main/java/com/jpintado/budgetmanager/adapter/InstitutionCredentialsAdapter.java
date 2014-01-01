package com.jpintado.budgetmanager.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

import java.util.ArrayList;

public class InstitutionCredentialsAdapter extends ArrayAdapter<InstitutionCredentials> {
    private static final int INSTITUTION_CREDENTIAL_ROW_RESOURCE_ID = R.layout.row_institution_credentials;
    private static final String DEBUG_TAG = "[InstitutionCredentialsAdapter]";

    private final Activity context;

    public InstitutionCredentialsAdapter(Activity context, ArrayList<InstitutionCredentials> institutionCredentialsArrayList) {
        super(context, INSTITUTION_CREDENTIAL_ROW_RESOURCE_ID, institutionCredentialsArrayList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstitutionCredentials institutionCredentials = getItem(position);
        ViewHolder viewHolder;

        try {
            if (institutionCredentials != null) {
                viewHolder = (convertView != null) ? (ViewHolder) convertView.getTag() : null;
                if (viewHolder == null) {
                    convertView = context.getLayoutInflater().inflate(INSTITUTION_CREDENTIAL_ROW_RESOURCE_ID, null);
                    viewHolder  = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }

                viewHolder.institutionNameTextView.setText(institutionCredentials.institution.name);
                viewHolder.institutionUsernameTextView.setText(institutionCredentials.username);
            }
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView institutionNameTextView;
        private final TextView institutionUsernameTextView;

        public ViewHolder(View convertView) {
            institutionNameTextView = (TextView) convertView.findViewById(R.id.row_institution_credential_name_textView);
            institutionUsernameTextView = (TextView) convertView.findViewById(R.id.row_institution_credential_username_textView);
        }
    }

}
