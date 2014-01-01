package com.jpintado.budgetmanager.library.handler;

import com.jpintado.budgetmanager.library.model.InstitutionCredentials;

import java.util.ArrayList;

public class InstitutionCredentialsListResponseHandler extends BaseResponseHandler {
    public void onSuccess(ArrayList<InstitutionCredentials> response) {}
    public void onProgressMessage(Integer response) {}

    @Override
    public void onProgressUpdate(Object statusCode) {
        onProgressMessage((Integer) statusCode);
    }

    @Override
    protected void handleSuccessMessage(Object response) {
        onSuccess((ArrayList<InstitutionCredentials>) response);
    }}
