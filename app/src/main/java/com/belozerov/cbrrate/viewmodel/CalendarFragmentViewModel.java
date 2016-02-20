package com.belozerov.cbrrate.viewmodel;

import android.annotation.SuppressLint;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.belozerov.cbrrate.binding.ObservableString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public class CalendarFragmentViewModel extends BaseFragmentViewModel {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
    public ObservableString currentMonth = new ObservableString();
    public ObservableField<Date> currentDate = new ObservableField<>(new Date());

    public CalendarFragmentViewModel() {
        currentDate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                currentMonth.set(dateFormat.format(currentDate.get()));
            }
        });
    }

    public void setDate(Date date) {
        currentDate.set(date);
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        setDate(calendar.getTime());
    }
}
