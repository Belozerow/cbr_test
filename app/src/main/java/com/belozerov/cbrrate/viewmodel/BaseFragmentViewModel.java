package com.belozerov.cbrrate.viewmodel;

import android.content.Context;

import com.belozerov.cbrrate.Application;

import java.io.Serializable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created: Belozerov
 * Date: 23.12.2015
 */
public class BaseFragmentViewModel implements Serializable {

    public BaseFragmentViewModel() {
    }

    public Context getContext() {
        return Application.getContext();
    }

    public void onViewCreated() {

    }

    public void onDestroyView() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onModelAttached() {

    }

    public <T> Observable<T> execute(Observable<T> observable) {
        return observable.cache().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
