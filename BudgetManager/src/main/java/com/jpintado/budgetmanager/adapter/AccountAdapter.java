package com.jpintado.budgetmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.library.model.Account;

import java.util.ArrayList;

public class AccountAdapter extends ArrayAdapter<Account> {
    private static final int ACCOUNT_ROW_RESOURCE_ID = R.layout.row_account;
    private static final String DEBUG_TAG = "[AccountAdapter]";

    private final Activity context;

    public AccountAdapter(Activity context, ArrayList<Account> accountList) {
        super(context, ACCOUNT_ROW_RESOURCE_ID, accountList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        ViewHolder viewHolder;

        try {
            if (account != null) {
                viewHolder = (convertView != null) ? (ViewHolder) convertView.getTag() : null;
                if (viewHolder == null) {
                    convertView = context.getLayoutInflater().inflate(ACCOUNT_ROW_RESOURCE_ID, null);
                    viewHolder  = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                }

                viewHolder.accountDescriptionTextView.setText(account.description);
                viewHolder.accountNumberTextView.setText(account.number);
            }
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView accountDescriptionTextView;
        private final TextView accountNumberTextView;

        public ViewHolder(View convertView) {
            accountDescriptionTextView = (TextView) convertView.findViewById(R.id.row_account_description_textView);
            accountNumberTextView = (TextView) convertView.findViewById(R.id.row_account_number_textView);
        }
    }
}
