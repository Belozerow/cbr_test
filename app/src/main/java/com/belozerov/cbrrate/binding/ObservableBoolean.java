package com.belozerov.cbrrate.binding;

/**
 * Created: Belozerov
 * Date: 18.01.2016
 */

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ObservableBoolean extends BaseObservable implements Parcelable, Serializable {
    public static final Creator<ObservableBoolean> CREATOR
            = new Creator<ObservableBoolean>() {

        @Override
        public ObservableBoolean createFromParcel(Parcel source) {
            return new ObservableBoolean(source.readInt() == 1);
        }

        @Override
        public ObservableBoolean[] newArray(int size) {
            return new ObservableBoolean[size];
        }
    };
    static final long serialVersionUID = 1L;
    private Boolean mValue;

    /**
     * Creates an ObservableBoolean with the given initial value.
     *
     * @param value the initial value for the ObservableBoolean
     */
    public ObservableBoolean(Boolean value) {
        mValue = value;
    }

    /**
     * Creates an ObservableBoolean with the initial value of <code>false</code>.
     */
    @SuppressWarnings("unused")
    public ObservableBoolean() {
    }

    @SuppressWarnings("unused")
    @BindingConversion
    public static Boolean convertToBoolean(ObservableBoolean observableBoolean) {
        return observableBoolean.get();
    }

    public Boolean get() {
        return mValue;
    }

    public void set(Boolean value) {
        if (value != mValue) {
            mValue = value;
            notifyChange();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mValue ? 1 : 0);
    }
}