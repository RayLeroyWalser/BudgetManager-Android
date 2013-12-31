package com.jpintado.budgetmanager.library.handler;

import com.jpintado.budgetmanager.library.model.Account;

import java.util.ArrayList;

public class AccountListResponseHandler extends BaseResponseHandler {
    public void onSuccess(ArrayList<Account> response) {}
    public void onProgressMessage(Integer response) {}

    @Override
    public void onProgressUpdate(Object statusCode)
    {
        onProgressMessage((Integer) statusCode);
    }

    @Override
    protected void handleSuccessMessage(Object response)
    {
        onSuccess((ArrayList<Account>) response);
    }
}
