package com.jpintado.budgetmanager.library.runnable;

import android.util.Base64;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;
import com.jpintado.budgetmanager.library.util.RandomString;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class RegistrationRunnable extends BaseRunnable implements Runnable {
    private final String username;
    private final String email;
    private final String password;
    private final StringResponseHandler responseHandler;

    public RegistrationRunnable(String username, String email, String password, StringResponseHandler responseHandler) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();
            String challenge = new RandomString(30).nextString();

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("decrypted_challenge", Base64.encodeToString(challenge.getBytes(), Base64.DEFAULT)));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("encrypted_challenge", AESCBC.encrypt(password, challenge)));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getRegistrationUrl(), params);
            if (response.getResponseCode() == 200)
                callbackSuccessResponse("", responseHandler);
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
