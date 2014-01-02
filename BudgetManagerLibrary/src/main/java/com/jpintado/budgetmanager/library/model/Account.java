package com.jpintado.budgetmanager.library.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Account {

    @SerializedName("type")
    public String type;

    @SerializedName("number")
    public String number;

    @SerializedName("description")
    public String description;

    @SerializedName("institution")
    public Institution institution;

    @SerializedName("institution_credentials")
    public InstitutionCredentials institution_credentials;

    public ArrayList<NameValuePair> formAddPostParams() {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("number", number));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("type", type));
        Gson gson = new Gson();
        params.add(new BasicNameValuePair("institution", gson.toJson(institution)));
        return params;
    }
}
