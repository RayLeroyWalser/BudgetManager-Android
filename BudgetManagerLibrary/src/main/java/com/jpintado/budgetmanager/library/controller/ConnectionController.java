package com.jpintado.budgetmanager.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.os.Handler;

import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

public class ConnectionController {

    public static final int DEFAULT_SOCKET_TIMEOUT = 60 * 1000; // milliseconds

    private static HttpClient httpClient;
    private Handler mHandler;
    public static int METHOD_GET = 1;
    public static int METHOD_POST = 2;

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
            final HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, DEFAULT_SOCKET_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, DEFAULT_SOCKET_TIMEOUT);
            ConnManagerParams.setTimeout(params, DEFAULT_SOCKET_TIMEOUT);
        }
        return httpClient;
    }

    public static CustomHttpResponse executeHttpRequest(int method, String url, ArrayList<NameValuePair> params) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            int responseCode;
            if(method == METHOD_GET) {
                HttpGet getRequest = new HttpGet();
                getRequest.setURI(new URI(formUrlParameters(url, params)));
                HttpResponse getResponse = client.execute(getRequest);
                in = new BufferedReader(new InputStreamReader(getResponse.getEntity().getContent()));
                responseCode = getResponse.getStatusLine().getStatusCode();
            } else if(method == METHOD_POST) {
                HttpPost postRequest = new HttpPost(url);
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
                postRequest.setEntity(formEntity);
                HttpResponse postResponse = client.execute(postRequest);
                in = new BufferedReader(new InputStreamReader(postResponse.getEntity().getContent()));
                responseCode = postResponse.getStatusLine().getStatusCode();
            }
            else {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
            in.close();
            return new CustomHttpResponse(responseCode, stringBuilder.toString());
        } catch(Exception e) {
            return null;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String formUrlParameters(String url, ArrayList<NameValuePair> params) {
        if (params != null) {
            String paramSeparator = url.contains("?") ? "&" : "?";
            for (NameValuePair param : params) {
                url += paramSeparator + param.getName() + "=" + param.getValue();
                paramSeparator = "&";
            }
        }
        return url;
    }
}