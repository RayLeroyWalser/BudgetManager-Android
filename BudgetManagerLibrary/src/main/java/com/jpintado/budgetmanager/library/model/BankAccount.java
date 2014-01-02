package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class BankAccount extends Account {

    @SerializedName("routing_number")
    public String routing_number;

    @SerializedName("account_type")
    public String account_type;

    @Override
    public ArrayList<NameValuePair> formAddPostParams() {
        ArrayList<NameValuePair> params = super.formAddPostParams();
        params.add(new BasicNameValuePair("routing_number", routing_number));
        params.add(new BasicNameValuePair("account_type", account_type));
        return params;
    }
}
