package com.jpintado.budgetmanager.library.runnable;

import com.jpintado.budgetmanager.library.handler.BaseResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;

import java.util.ArrayList;

public class BaseRunnable {
    protected void callbackSuccessResponse(Object message, BaseResponseHandler responseHandler) {
        if (responseHandler != null)
        {
            responseHandler.sendSuccessMessage(message);
            responseHandler.sendFinishMessage();
        }
    }

    protected void callbackFailureResponse(String message, BaseResponseHandler responseHandler) {
        if (responseHandler != null)
        {
            responseHandler.sendFailureMessage(message);
            responseHandler.sendFinishMessage();
        }
    }
}
