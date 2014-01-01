package com.jpintado.budgetmanager.library.runnable;

import android.util.Base64;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginRunnable extends BaseRunnable implements Runnable {

    private static final String DEBUG_TAG = "LoginRunnable";
    private final String username;
    private final String password;
    private final StringResponseHandler responseHandler;

    public LoginRunnable(String username, String password, StringResponseHandler responseHandler) {
        this.username = username;
        this.password = password;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();
            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_GET, BMLibrary.urlHelper.getChallengeUrl(username), null);

            JSONObject responseJSON = new JSONObject(response.getData());
            String encryptedChallenge = responseJSON.getString("encrypted_challenge");
            String decryptedChallenge = AESCBC.decrypt(password, encryptedChallenge);
            String encodedDecryptedChallenge = Base64.encodeToString(decryptedChallenge.getBytes(), Base64.DEFAULT);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("decrypted_challenge", encodedDecryptedChallenge));

            response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getLoginUrl(), params);

            if (response.getResponseCode() == 200)
            {
                responseJSON = new JSONObject(response.getData());
                BMLibrary.userInfoProvider.setRsaPublic(responseJSON.getString("rsa_public"));

                BMLibrary.userInfoProvider.setPassword(password);
                callbackSuccessResponse("", responseHandler);
            }
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
