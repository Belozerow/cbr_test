package com.belozerov.cbrrate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belozerov.cbrrate.R;
import com.belozerov.cbrrate.activity.MainActivity;
import com.belozerov.cbrrate.databinding.FragmentCalendarBinding;
import com.belozerov.cbrrate.viewmodel.CalendarFragmentViewModel;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public class CalendarFragment extends BaseBindingFragment<FragmentCalendarBinding, CalendarFragmentViewModel> {
    private static final String ARG_DATE = "arg_date";

    public static CalendarFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        CalendarFragment fragment = new CalendarFragment();
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
    protected void onViewModelCreated(CalendarFragmentViewModel model) {
        super.onViewModelCreated(model);
        initCalendar();
        initToolbar();

        getBinding().getRate.setOnClickListener(v -> {
            getMainActivity().replaceFragment(RateInfoFragment.newInstance(getModel().currentDate.get()), true);
            getArguments().putSerializable(ARG_DATE, getModel().currentDate.get());
        });
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void initToolbar() {
        getAppCompatActivity().setSupportActionBar(getBinding().toolbar);
    }


    private void initCalendar() {
        getBinding().calendarView.setShowDatesOutsideMonth(true);

        getBinding().calendarView.setOnDateClickListener((year, month, day) -> getModel().setDate(year, month, day));

        getBinding().calendarView.setOnMonthChangeListener((year, month, direction) -> getModel().setDate(year, month, 1));

        getBinding().calendarView.setEventDataProvider((year, month, day) -> new ArrayList<>());

        getBinding().calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @SuppressWarnings("deprecation")
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (BaseCellView) inflater.inflate(R.layout.widget_calendar_date_cell, null);
                    switch (cellType) {
                        case BaseCellView.OUTSIDE_MONTH:
                            cellView.setTextColor(getResources().getColor(R.color.outOfMonth));
                            break;
                        default:
                            cellView.setTextColor(getResources().getColor(R.color.mainTextColor));
                    }
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        getBinding().calendarView.post(() -> {
            getModel().setDate(getDateArg());
            getBinding().calendarView.selectDate(getDateArg());
        });
    }
}
