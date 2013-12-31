package com.jpintado.budgetmanager.library.handler;

import com.jpintado.budgetmanager.library.model.Institution;

public class InstitutionResponseHandler extends BaseResponseHandler {
    public void onSuccess(Institution jsonObject) {}

    @Override
    protected void handleSuccessMessage(Object response)
    {
        onSuccess((Institution) response);
    }
}