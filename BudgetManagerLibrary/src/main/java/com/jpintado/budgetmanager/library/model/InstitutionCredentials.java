package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

public class InstitutionCredentials {

    @SerializedName("user")
    public String user;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("institution")
    public Institution institution;
}
