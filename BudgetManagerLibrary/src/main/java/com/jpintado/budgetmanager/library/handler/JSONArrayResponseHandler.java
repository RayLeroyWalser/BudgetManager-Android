package com.jpintado.budgetmanager.library.handler;

import org.json.JSONArray;

public class JSONArrayResponseHandler extends BaseResponseHandler {
    public void onSuccess(JSONArray jsonArray) {}

    @Override
    protected void handleSuccessMessage(Object response) {
        onSuccess((JSONArray) response);
    }
}
