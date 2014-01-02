package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InstitutionCredentials implements Serializable {

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("institution")
    public Institution institution;
}
