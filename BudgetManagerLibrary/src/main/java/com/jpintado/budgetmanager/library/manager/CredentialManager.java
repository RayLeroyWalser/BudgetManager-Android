package com.jpintado.budgetmanager.library.manager;


import com.android.volley.Request;
import com.android.volley.Response;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.helper.UrlHelper;
import com.jpintado.budgetmanager.library.request.CustomRequest;

import java.util.HashMap;
import java.util.Map;

public class CredentialManager {
    private static final String DEBUG_TAG = "CredentialManager";

    private String sessionCookie;

    public void login(final String username, final String password, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = BMLibrary.urlHelper.loginUrl();
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        final CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, listener, errorListener);

        BMLibrary.addRequest(jsObjRequest);
    }

    public void register(String username, String email, String password, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = BMLibrary.urlHelper.registrationUrl();
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
        final CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, listener, errorListener);

        BMLibrary.addRequest(jsObjRequest);
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }
}
