package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("refresh_url")
    public String number;

    @SerializedName("description")
    public String description;

    @SerializedName("institution_credentials")
    public InstitutionCredentials institution_credentials;
}
