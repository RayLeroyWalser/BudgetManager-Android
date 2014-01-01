package com.jpintado.budgetmanager.library.runnable;

import com.google.gson.Gson;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.handler.InstitutionCredentialsListResponseHandler;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.json.JSONArray;

import java.util.ArrayList;

public class GetInstitutionListRunnable extends BaseRunnable implements Runnable {
    private final InstitutionCredentialsListResponseHandler responseHandler;

    public GetInstitutionListRunnable(InstitutionCredentialsListResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_GET, BMLibrary.urlHelper.getInstitutionListUrl(), null);
            if (response.getResponseCode() == 200)
            {
                JSONArray responseJSONArray = new JSONArray(response.getData());
                ArrayList<InstitutionCredentials> accountArrayList = new ArrayList<InstitutionCredentials>();
                Gson gson = new Gson();
                for (int i=0; i<responseJSONArray.length(); i++) {
                    InstitutionCredentials institutionCredentials = gson.fromJson(responseJSONArray.getJSONObject(i).toString(), InstitutionCredentials.class);
                    accountArrayList.add(institutionCredentials);
                }
                callbackSuccessResponse(accountArrayList, responseHandler);
            }
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
