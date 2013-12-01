package com.jpintado.budgetmanager.library.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.jpintado.budgetmanager.library.BMLibrary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends StringRequest {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    public static final String SESSION_COOKIE = "sessionid";

    private Map<String, String> params;

    public CustomRequest(int requestMethod, String url, Map<String, String> params,
                         Response.Listener responseListener, Response.ErrorListener errorListener) {
 
        super(requestMethod, url, responseListener, errorListener); // Call parent constructor
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        if (response.headers.containsKey(SET_COOKIE_KEY)
                && response.headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = response.headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                BMLibrary.credentialManager.setSessionCookie(splitSessionId[1]);
            }
        }
        return super.parseNetworkResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        String sessionId = BMLibrary.credentialManager.getSessionCookie();
        if (sessionId != null && sessionId.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }

        return headers;
    }
}