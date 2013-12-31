package com.jpintado.budgetmanager.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class InstitutionAdapter extends ArrayAdapter<JSONObject> {
    private static final int INSTITUTION_ROW_RESOURCE_ID = R.layout.row_institution;
    private static final String DEBUG_TAG = "[InstitutionAdapter]";

    private final Activity context;

    public InstitutionAdapter(Activity context, ArrayList<JSONObject> institutionArrayList) {
        super(context, INSTITUTION_ROW_RESOURCE_ID, institutionArrayList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject institutionJSON = getItem(position);
        ViewHolder viewHolder;

        try {
            if (institutionJSON != null) {
                viewHolder = (convertView != null) ? (ViewHolder) convertView.getTag() : null;
                if (viewHolder == null) {
                    convertView = context.getLayoutInflater().inflate(INSTITUTION_ROW_RESOURCE_ID, null);
                    viewHolder  = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }

                viewHolder.institutionNameTextView.setText(institutionJSON.getString("name"));
            }
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView institutionNameTextView;

        public ViewHolder(View convertView) {
            institutionNameTextView = (TextView) convertView.findViewById(R.id.row_institution_name_textView);
        }
    }
}
