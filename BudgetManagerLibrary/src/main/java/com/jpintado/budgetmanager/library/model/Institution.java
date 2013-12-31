package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

public class Institution {

    @SerializedName("id")
    public String id;

    @SerializedName("org")
    public String org;

    @SerializedName("url")
    public String url;

    @SerializedName("name")
    public String name;
}
