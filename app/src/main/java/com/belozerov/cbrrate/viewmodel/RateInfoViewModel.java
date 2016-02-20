package com.belozerov.cbrrate.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.belozerov.cbrrate.Application;
import com.belozerov.cbrrate.R;
import com.belozerov.cbrrate.api.RetrofitFactory;
import com.belozerov.cbrrate.api.model.RateAnswer;
import com.belozerov.cbrrate.binding.ObservableString;
import com.belozerov.cbrrate.model.Valute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public class RateInfoViewModel extends BaseFragmentViewModel {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final String EURO = "EUR";
    private static final String USD = "USD";
    private ObservableArrayList<Valute> valutes = new ObservableArrayList<>();
    public ObservableField<Date> date = new ObservableField<>();
    public ObservableString dateText = new ObservableString();
    public ObservableField<Valute> euro = new ObservableField<>();
    public ObservableField<Valute> usd = new ObservableField<>();
    public ObservableString errorText = new ObservableString();
    public ObservableBoolean progressVisible = new ObservableBoolean(true);

    public RateInfoViewModel() {
        date.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                requestRate();
                dateText.set(DateFormat.getDateInstance().format(date.get()));
            }
        });
        valutes.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                findEuroAndUsd();
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {

            }
        });
    }

    private void findEuroAndUsd() {
        for (Valute valute : valutes) {
            if (EURO.equals(valute.getCharCode())) {
                euro.set(valute);
            } else if (USD.equals(valute.getCharCode())) {
                usd.set(valute);
            }
        }
    }

    public Observable<RateAnswer> requestRate() {
        Observable<RateAnswer> rateAnswerObservable = execute(RetrofitFactory.getCbrApiServiceInstance().getRate(dateFormat.format(date.get()))).delaySubscription(1, TimeUnit.SECONDS);
        rateAnswerObservable.subscribe(rateAnswer -> {
            clear();
            if (rateAnswer.getValutes() == null || rateAnswer.getValutes().isEmpty()) {
                errorText.set(Application.getContext().getString(R.string.noValutes));
            } else {
                valutes.addAll((rateAnswer.getValutes()));
            }
        }, throwable -> {
            clear();
            errorText.set(throwable.getMessage());
        });
        return rateAnswerObservable;
    }

    private void clear() {
        valutes.clear();
        euro.set(null);
        usd.set(null);
        errorText.set(null);
        progressVisible.set(false);
    }
}
