package com.jpintado.budgetmanager.library.runnable;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.handler.JSONArrayResponseHandler;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;

public class SearchInstitutionRunnable extends BaseRunnable implements Runnable {

    private final JSONArrayResponseHandler responseHandler;
    private final String query;

    public SearchInstitutionRunnable(String query, JSONArrayResponseHandler responseHandler) {
        this.query = query;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("q", query));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_GET, BMLibrary.urlHelper.getInstitutionSearchUrl(), params);
            if (response.getResponseCode() == 200) {
                JSONArray responseJSONArray = new JSONArray(response.getData());
                callbackSuccessResponse(responseJSONArray, responseHandler);
            }
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
