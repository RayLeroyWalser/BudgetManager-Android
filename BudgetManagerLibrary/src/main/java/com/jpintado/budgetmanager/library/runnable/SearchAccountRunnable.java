package com.jpintado.budgetmanager.library.runnable;

import com.google.gson.Gson;
import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.crypto.AESCBC;
import com.jpintado.budgetmanager.library.crypto.RSA;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.library.model.factory.AccountFactory;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchAccountRunnable extends BaseRunnable implements Runnable {
    private final AccountListResponseHandler responseHandler;
    private final InstitutionCredentials institutionCredentials;

    public SearchAccountRunnable(InstitutionCredentials institutionCredentials, AccountListResponseHandler responseHandler) {
        this.institutionCredentials = institutionCredentials;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            String decryptedPassword = AESCBC.decrypt(BMLibrary.userInfoProvider.getPassword(), institutionCredentials.password);
            String encryptedPassword = RSA.encrypt(BMLibrary.userInfoProvider.getRsaPublic(), decryptedPassword);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("org", institutionCredentials.institution.org));
            params.add(new BasicNameValuePair("encrypted_password", encryptedPassword));

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getAccountSearchUrl(), params);
            if (response.getResponseCode() == 200) {
                JSONArray responseJSONArray = new JSONArray(response.getData());
                ArrayList<Account> accountArrayList = new ArrayList<Account>();
                for (int i=0; i<responseJSONArray.length(); i++) {
                    JSONObject accountJSON = responseJSONArray.getJSONObject(i).getJSONObject("account");
                    accountArrayList.add(AccountFactory.fromJSONFactory(accountJSON));
                }
                callbackSuccessResponse(accountArrayList, responseHandler);
            }
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }
    }
}
