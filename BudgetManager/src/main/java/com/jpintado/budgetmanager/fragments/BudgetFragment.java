package com.jpintado.budgetmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpintado.budgetmanager.activity.MainActivity;
import com.jpintado.budgetmanager.R;

public class BudgetFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "bundle_position";

    public static BudgetFragment newInstance(int position) {
        BudgetFragment budgetFragment = new BudgetFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, position);
        budgetFragment.setArguments(bundle);

        return budgetFragment;
    }

    public BudgetFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }
}
