package com.belozerov.cbrrate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.belozerov.cbrrate.activity.MainActivity;
import com.belozerov.cbrrate.databinding.FragmentRateInfoBinding;
import com.belozerov.cbrrate.viewmodel.RateInfoViewModel;

import java.util.Date;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public class RateInfoFragment extends BaseBindingFragment<FragmentRateInfoBinding, RateInfoViewModel> {
    private static final String ARG_DATE = "arg_date";

    public static RateInfoFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        RateInfoFragment fragment = new RateInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Date getDateArg() {
        if (getArguments() != null) {
            return (Date) getArguments().getSerializable(ARG_DATE);
        }
        return null;
    }

    @Override
    protected void onViewModelCreated(RateInfoViewModel model) {
        super.onViewModelCreated(model);
        model.date.set(getDateArg());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getMainActivity().setSupportActionBar(getBinding().toolbar);
        ActionBar actionBar = getMainActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
