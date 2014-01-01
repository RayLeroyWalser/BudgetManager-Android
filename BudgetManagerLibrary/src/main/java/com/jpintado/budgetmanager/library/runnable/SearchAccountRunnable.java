package com.jpintado.budgetmanager.library.runnable;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.crypto.RSA;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class SearchAccountRunnable extends BaseRunnable implements Runnable {
    private final StringResponseHandler responseHandler;
    private final InstitutionCredentials institutionCredentials;

    public SearchAccountRunnable(InstitutionCredentials institutionCredentials, StringResponseHandler responseHandler) {
        this.institutionCredentials = institutionCredentials;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            String decryptedPassword = AESCBC.decrypt(BMLibrary.userInfoProvider.getPassword(), institutionCredentials.password);
            String encryptedPassword = RSA.encrypt(BMLibrary.userInfoProvider.getRsaPublic(), decryptedPassword);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("org", institutionCredentials.institution.org));
            params.add(new BasicNameValuePair("encrypted_password", encryptedPassword));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getAccountSearchUrl(), params);
            if (response.getResponseCode() == 200)
                callbackSuccessResponse("", responseHandler);
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }

    }
}
