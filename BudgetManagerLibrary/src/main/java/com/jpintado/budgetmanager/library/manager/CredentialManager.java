package com.jpintado.budgetmanager.library.manager;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.runnable.LoginRunnable;
import com.jpintado.budgetmanager.library.runnable.RegistrationRunnable;

public class CredentialManager {
    private static final String DEBUG_TAG = "CredentialManager";

    public void login(final String username, final String password, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new LoginRunnable(username, password, responseHandler));
    }

    public void register(String username, String email, String password, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new RegistrationRunnable(username, email, password, responseHandler));
    }
}
