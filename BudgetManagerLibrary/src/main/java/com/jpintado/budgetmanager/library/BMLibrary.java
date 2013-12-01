package com.jpintado.budgetmanager.library;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jpintado.budgetmanager.library.helper.UrlHelper;
import com.jpintado.budgetmanager.library.manager.CredentialManager;

public class BMLibrary {

    private static final String BM_HOST = "192.168.1.126";
    private static final String BM_PORT = "8001";
    private static final String BM_API_PREFIX = "/api/1.0";
    private static final String BM_OFX_PREFIX = "/ofx";

    public static CredentialManager credentialManager;
    static RequestQueue queue;
    private static UrlHelper urlHelper;

    public BMLibrary(Application application) {
        credentialManager = new CredentialManager();
        urlHelper = new UrlHelper.UrlHelperBuilder(BM_HOST)
                .setProtocol(UrlHelper.PROTOCOL_HTTP)
                .setPort(BM_PORT)
                .setApiPath(BM_API_PREFIX)
                .setOfxPath(BM_OFX_PREFIX)
                .build();
        queue = Volley.newRequestQueue(application);
    }

    public static void addRequest(Request request) {
        queue.add(request);
    }
}
