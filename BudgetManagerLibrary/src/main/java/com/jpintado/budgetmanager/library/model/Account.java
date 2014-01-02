package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("type")
    public String type;

    @SerializedName("number")
    public String number;

    @SerializedName("description")
    public String description;

    @SerializedName("institution_credentials")
    public InstitutionCredentials institution_credentials;
}
