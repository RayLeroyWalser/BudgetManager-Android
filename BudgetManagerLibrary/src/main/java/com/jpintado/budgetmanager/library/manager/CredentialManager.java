package com.jpintado.budgetmanager.library.manager;


import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.runnable.LoginRunnable;
import com.jpintado.budgetmanager.library.runnable.RegistrationRunnable;

public class CredentialManager {
    private static final String DEBUG_TAG = "CredentialManager";

    private String sessionCookie;
    private String rsa_public;

    public void login(final String username, final String password, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new LoginRunnable(username, password, responseHandler));
    }

    public void register(String username, String email, String password, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new RegistrationRunnable(username, email, password, responseHandler));
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setRsaPublic(String rsa_public) {
        this.rsa_public = rsa_public;
    }
}
