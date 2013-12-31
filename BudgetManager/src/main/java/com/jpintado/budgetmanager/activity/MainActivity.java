package com.jpintado.budgetmanager.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.jpintado.budgetmanager.fragments.InstitutionAddProcessFragment;
import com.jpintado.budgetmanager.fragments.NavigationDrawerFragment;
import com.jpintado.budgetmanager.R;
import com.jpintado.budgetmanager.fragments.BudgetFragment;
import com.jpintado.budgetmanager.fragments.HomeFragment;
import com.jpintado.budgetmanager.fragments.SettingsFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                   InstitutionAddProcessFragment.InstitutionAddProcessFragmentCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String[] mPlanetTitles;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, getFragmentByPosition(position))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_institution:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, InstitutionAddProcessFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_add_account:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, InstitutionAddProcessFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void onSectionAttached(int number) {
        mTitle = mPlanetTitles[number];
    }

    private Fragment getFragmentByPosition(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = HomeFragment.newInstance(position);
                break;
            case 1:
            default:
                fragment = BudgetFragment.newInstance(position);
                break;
        }
        return fragment;
    }

    @Override
    public void onInstitutionAdded(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }
}
