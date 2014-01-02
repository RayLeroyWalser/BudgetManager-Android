package com.jpintado.budgetmanager.library.provider;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.library.runnable.SearchAccountRunnable;

public class AccountProvider {
    public void searchAccount(InstitutionCredentials institutionCredentials, AccountListResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new SearchAccountRunnable(institutionCredentials, responseHandler));
    }
}
