package com.jpintado.budgetmanager.library.model;

import com.google.gson.annotations.SerializedName;

public class BankAccount extends Account {

    @SerializedName("routing_number")
    public String routing_number;

    @SerializedName("account_type")
    public String account_type;
}
