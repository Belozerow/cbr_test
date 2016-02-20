package com.belozerov.cbrrate.binding;

import android.databinding.BindingAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.belozerov.cbrrate.R;


/**
 * Created: Belozerov
 * Date: 09.02.2016
 */
@SuppressWarnings("unused")
public class BindingTwoWayAttributes {
    @SuppressWarnings("unchecked")
    @BindingAdapter({"app:twoWayText"})
    public static void bindEditText(EditText view,
                                    final ObservableString observableString) {
        Pair<ObservableString, TextWatcher> pair =
                (Pair) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != observableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second);
            }
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                public void onTextChanged(CharSequence s,
                                          int start, int before, int count) {
                    observableString.set(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            view.setTag(R.id.bound_observable,
                    new Pair<>(observableString, watcher));
            view.addTextChangedListener(watcher);
        }
        if (observableString != null) {
            String newValue = observableString.get();
            if (!view.getText().toString().equals(newValue)) {
                view.setText(newValue);
            }
        }
    }

    @BindingAdapter({"app:twoWayBoolean"})
    public static void bindCheckBox(CheckBox view, final ObservableBoolean observableBoolean) {
        if (observableBoolean != null) {
            if (view.getTag(R.id.bound_observable) != observableBoolean) {
                view.setTag(R.id.bound_observable, observableBoolean);
                view.setOnCheckedChangeListener((buttonView, isChecked) -> observableBoolean.set(isChecked));
            }
            Boolean newValue = observableBoolean.get();
            if (newValue != null && view.isChecked() != newValue) {
                view.setChecked(newValue);
            }
        }
    }

    @BindingAdapter({"app:twoWaySpinner"})
    public static void bindSpinner(final Spinner spinner, final ObservableString observable) {
        if (observable != null) {
            if (spinner.getTag(R.id.bound_observable) != observable) {
                spinner.setTag(R.id.bound_observable, observable);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        observable.set((String) spinner.getItemAtPosition(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            String newValue = observable.get();
            if (newValue == null)
                return;
            if (!observable.equals(spinner.getSelectedItem())) {
                SpinnerAdapter spinnerAdapter = spinner.getAdapter();
                int selectedItem = spinner.getSelectedItemPosition();
                if (spinnerAdapter != null) {
                    for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                        if (newValue.equals(spinnerAdapter.getItem(i))) {
                            selectedItem = i;
                            break;
                        }
                    }
                }
                spinner.setSelection(selectedItem);
            }

        }
    }
}
