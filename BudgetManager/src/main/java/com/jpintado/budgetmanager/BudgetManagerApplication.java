package com.jpintado.budgetmanager;

import android.app.Application;

import com.jpintado.budgetmanager.library.BMLibrary;

public class BudgetManagerApplication extends Application {
    private BMLibrary bmLibrary;

    @Override
    public void onCreate() {
        super.onCreate();

        bmLibrary = new BMLibrary(this);
    }
}
