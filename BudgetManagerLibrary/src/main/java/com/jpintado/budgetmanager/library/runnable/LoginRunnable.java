package com.jpintado.budgetmanager.library.runnable;

 import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.request.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRunnable implements Runnable {

    private static final String DEBUG_TAG = "LoginRunnable";
    private final String email;
    private final String password;
    private final Response.Listener listener;
    private final Response.ErrorListener errorListener;

    public LoginRunnable(String email, String password, Response.Listener listener, Response.ErrorListener errorListener) {
        this.email = email;
        this.password = password;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    public void run() {
        try {
            RequestFuture<String> requestFuture = RequestFuture.newFuture();
            CustomRequest customRequest = new CustomRequest(
                    Request.Method.GET,
                    BMLibrary.urlHelper.getChallengeUrl(email),
                    null,
                    requestFuture,
                    requestFuture);
            BMLibrary.addRequest(customRequest);

            JSONObject responseJSON = new JSONObject(requestFuture.get());
            String encryptedChallenge = responseJSON.getString("encrypted_challenge");
            String decryptedChallenge = AESCBC.decrypt(password, encryptedChallenge);
            String encodedDecryptedChallenge = Base64.encodeToString(decryptedChallenge.getBytes(), Base64.DEFAULT);

            Map<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            params.put("decrypted_challenge", encodedDecryptedChallenge);

            customRequest = new CustomRequest(
                    Request.Method.POST,
                    BMLibrary.urlHelper.getLoginUrl(),
                    params,
                    listener,
                    errorListener) {
                @Override
                protected void deliverResponse(String response) {
                    try {
                        JSONObject responseJSON = new JSONObject(response);
                        BMLibrary.credentialManager.setRsaPublic(responseJSON.getString("rsa_public"));
                        super.deliverResponse(response);
                    } catch (Exception ex) {
                        Log.e(DEBUG_TAG, ex.getMessage());
                        errorListener.onErrorResponse(new VolleyError());
                    }
                }
            };

            BMLibrary.addRequest(customRequest);

        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
            errorListener.onErrorResponse(new VolleyError("Unable to login"));
        }
    }
}
