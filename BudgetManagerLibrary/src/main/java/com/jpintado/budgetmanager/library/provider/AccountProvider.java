package com.jpintado.budgetmanager.library.provider;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.AccountListResponseHandler;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Account;
import com.jpintado.budgetmanager.library.model.InstitutionCredentials;
import com.jpintado.budgetmanager.library.runnable.AddAccountRunnable;
import com.jpintado.budgetmanager.library.runnable.GetAccountListRunnable;
import com.jpintado.budgetmanager.library.runnable.SearchAccountRunnable;

public class AccountProvider {
    public void searchAccount(InstitutionCredentials institutionCredentials, AccountListResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new SearchAccountRunnable(institutionCredentials, responseHandler));
    }

    public void addAccount(Account account, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new AddAccountRunnable(account, responseHandler));
    }

    public void getAccountList(AccountListResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new GetAccountListRunnable(responseHandler));
    }
}
