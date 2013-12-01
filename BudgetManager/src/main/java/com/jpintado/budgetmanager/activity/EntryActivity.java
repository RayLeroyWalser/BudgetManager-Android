package com.jpintado.budgetmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.fragments.LoginFragment;
import com.jpintado.budgetmanager.fragments.RegistrationFragment;

public class EntryActivity extends FragmentActivity
        implements LoginFragment.LoginFragmentCallbacks, RegistrationFragment.RegistrationFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        LoginFragment loginFragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.entry_container_linearLayout, loginFragment)
                .commit();
    }

    @Override
    public void onLoggedIn() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onRegisterClick() {
        RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.entry_container_linearLayout, registrationFragment)
                .commit();
    }

    @Override
    public void onRegistered() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onLoginClick() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.entry_container_linearLayout, loginFragment)
                .commit();
    }
}
