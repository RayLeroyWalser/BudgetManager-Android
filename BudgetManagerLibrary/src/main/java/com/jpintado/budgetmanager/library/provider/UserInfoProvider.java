package com.jpintado.budgetmanager.library.provider;

public class UserInfoProvider {
    private String rsaPublic;
    private String password;

    public void setRsaPublic(String rsaPublic) {
        this.rsaPublic = rsaPublic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
