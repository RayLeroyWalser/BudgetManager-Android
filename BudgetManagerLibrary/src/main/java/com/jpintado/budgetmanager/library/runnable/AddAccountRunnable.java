package com.jpintado.budgetmanager.library.runnable;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.controller.ConnectionController;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.util.CustomHttpResponse;

public class AddAccountRunnable extends BaseRunnable implements Runnable {
    private final Account account;
    private final StringResponseHandler responseHandler;

    public AddAccountRunnable(Account account, StringResponseHandler responseHandler) {
        this.account = account;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            responseHandler.sendStartMessage();

            CustomHttpResponse response = ConnectionController.executeHttpRequest(ConnectionController.METHOD_POST, BMLibrary.urlHelper.getAddAccountUrl(), account.formAddPostParams());
            if (response.getResponseCode() == 200)
                callbackSuccessResponse("", responseHandler);
            else
                callbackFailureResponse("", responseHandler);
        } catch (Exception ex) {
            callbackFailureResponse(ex.getMessage(), responseHandler);
        }

    }
}
