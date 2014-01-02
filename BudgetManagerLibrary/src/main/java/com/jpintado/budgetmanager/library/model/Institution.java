package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Institution implements Serializable{

    @SerializedName("fid")
    public String fid;

    @SerializedName("id")
    public String id;

    @SerializedName("org")
    public String org;

    @SerializedName("url")
    public String url;

    @SerializedName("name")
    public String name;
}
