package com.belozerov.cbrrate.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;

import com.belozerov.cbrrate.R;
import com.belozerov.cbrrate.fragment.BaseBindingFragment;
import com.belozerov.cbrrate.fragment.CalendarFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public class MainActivity extends AppCompatActivity {
    private static final Date DEFAULT_DATE = new Date();

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2013);
        DEFAULT_DATE.setTime(calendar.getTime().getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            replaceFragment(CalendarFragment.newInstance(DEFAULT_DATE));
        }
    }

    public void replaceFragment(BaseBindingFragment fragment) {
        replaceFragment(fragment, false);
    }

    public void replaceFragment(BaseBindingFragment fragment, boolean addToBackStack) {
        Bundle arguments;
        if (fragment.getArguments() == null) {
            arguments = new Bundle();
        } else {
            arguments = fragment.getArguments();
        }
        arguments.putBoolean(BaseBindingFragment.ADD_TO_BACK_STACK, addToBackStack);
        fragment.setArguments(arguments);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 21) {
            fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right));
            fragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getFragmentTag());
        }
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragment.getFragmentTag());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
