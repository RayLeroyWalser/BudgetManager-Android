package com.jpintado.budgetmanager.library.provider;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.BaseResponseHandler;
import com.jpintado.budgetmanager.library.runnable.GetAccountListRunnable;

public class InstitutionProvider {

    public void getAccountList(BaseResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new GetAccountListRunnable(responseHandler));
    }
}
