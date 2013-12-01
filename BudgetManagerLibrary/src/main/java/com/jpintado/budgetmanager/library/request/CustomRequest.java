package com.jpintado.budgetmanager.library.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<String> {

    private Map<String, String> params;
    private final Response.Listener listener;

    public CustomRequest(int requestMethod, String url, Map<String, String> params,
                         Response.Listener responseListener, Response.ErrorListener errorListener) {
 
        super(requestMethod, url, errorListener); // Call parent constructor
        this.params = params;
        this.listener = responseListener;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
 
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(responseData,
            HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String s) {
        listener.onResponse(s);
    }
}