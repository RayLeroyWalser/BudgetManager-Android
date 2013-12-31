package com.jpintado.budgetmanager.library.util;

public class CustomHttpResponse {
    //region Variables
    private String data;
    private int responseCode = 0;
    //endregion

    public CustomHttpResponse(int responseCode, String data) {
        this.data = data;
        this.responseCode = responseCode;
    }

    public String getData() {
        return data;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
