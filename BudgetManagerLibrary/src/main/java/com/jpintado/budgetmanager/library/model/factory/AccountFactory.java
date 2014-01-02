package com.jpintado.budgetmanager.library.model.factory;

import android.util.Log;

import com.google.gson.Gson;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.model.BankAccount;

import org.json.JSONObject;

public class AccountFactory {
    private static final String TYPE_BANK_ACCOUNT = "BankAccount";
    private static final String DEBUG_TAG = "[AccountFactory]";

    public static Account fromJSONFactory(JSONObject accountJSON) {
        try {
            Account account;
            String type = accountJSON.getString("type");
            Gson gson = new Gson();
            if (type.equals(TYPE_BANK_ACCOUNT)) {
                account = gson.fromJson(accountJSON.toString(), BankAccount.class);
            } else {
                account = gson.fromJson(accountJSON.toString(), Account.class);
            }
            return account;
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
        return null;
    }
}
