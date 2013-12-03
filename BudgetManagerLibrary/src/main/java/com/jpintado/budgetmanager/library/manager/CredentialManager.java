package com.jpintado.budgetmanager.library.manager;


import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.request.CustomRequest;
import com.jpintado.budgetmanager.library.runnable.LoginRunnable;
import com.jpintado.budgetmanager.library.util.RandomString;


import java.util.HashMap;
import java.util.Map;

public class CredentialManager {
    private static final String DEBUG_TAG = "CredentialManager";

    private String sessionCookie;
    private String rsa_public;

    public void login(final String email, final String password, Response.Listener listener, Response.ErrorListener errorListener) {
        BMLibrary.executeRunnable(new LoginRunnable(email, password, listener, errorListener));
    }

    public void register(String email, String password, Response.Listener listener, Response.ErrorListener errorListener) {
        try {
            String challenge = new RandomString(30).nextString();

            Map<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            params.put("decrypted_challenge", Base64.encodeToString(challenge.getBytes(), Base64.DEFAULT));
            params.put("encrypted_challenge", AESCBC.encrypt(password, challenge));

            CustomRequest customRequest = new CustomRequest(
                    Request.Method.POST,
                    BMLibrary.urlHelper.getRegistrationUrl(),
                    params,
                    listener,
                    errorListener);

            BMLibrary.addRequest(customRequest);
        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
            errorListener.onErrorResponse(new VolleyError("Unable to register"));
        }
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setRsaPublic(String rsa_public) {
        this.rsa_public = rsa_public;
    }
}
