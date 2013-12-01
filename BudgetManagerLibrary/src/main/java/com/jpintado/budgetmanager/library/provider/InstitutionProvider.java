package com.jpintado.budgetmanager.library.provider;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class InstitutionProvider {

    public void getAccounts(Response.Listener listener, Response.ErrorListener errorListener) {
        errorListener.onErrorResponse(new VolleyError("Not Implemented"));
    }
}
