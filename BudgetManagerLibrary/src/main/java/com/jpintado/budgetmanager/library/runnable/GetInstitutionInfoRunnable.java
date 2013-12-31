package com.jpintado.budgetmanager.library.runnable;

import com.google.gson.Gson;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.handler.InstitutionResponseHandler;
import com.jpintado.budgetmanager.library.model.Institution;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class GetInstitutionInfoRunnable extends BaseRunnable implements Runnable {

    private final String id;
    private final InstitutionResponseHandler responseHandler;

    public GetInstitutionInfoRunnable(String id, InstitutionResponseHandler responseHandler) {
        this.id = id;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_GET, BMLibrary.urlHelper.getInstitutionInfoUrl(), params);
            if (response.getResponseCode() == 200) {
                Gson gson = new Gson();
                Institution institution = gson.fromJson(response.getData(), Institution.class);
                callbackSuccessResponse(institution, responseHandler);
            } else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
