package com.jpintado.budgetmanager.library.runnable;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Institution;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class AddInstitutionRunnable extends BaseRunnable implements Runnable {

    private final Institution institution;
    private final String username;
    private final String password;
    private final StringResponseHandler responseHandler;

    public AddInstitutionRunnable(Institution institution, String username, String password, StringResponseHandler responseHandler) {
        this.institution = institution;
        this.username = username;
        this.password = password;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("institution_id", institution.id));
            params.add(new BasicNameValuePair("institution_org", institution.org));
            params.add(new BasicNameValuePair("institution_url", institution.url));
            params.add(new BasicNameValuePair("institution_name", institution.name));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getInstitutionCredentialsUrl(), params);
            if (response.getResponseCode() == 200)
                callbackSuccessResponse("", responseHandler);
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
