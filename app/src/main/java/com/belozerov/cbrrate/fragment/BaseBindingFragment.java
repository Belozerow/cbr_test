package com.belozerov.cbrrate.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.belozerov.cbrrate.binding.DataBindingClassUtils;
import com.belozerov.cbrrate.utils.ProgressDialogFragment;
import com.belozerov.cbrrate.utils.TypeResolver;
import com.belozerov.cbrrate.viewmodel.BaseFragmentViewModel;

import retrofit.HttpException;


/**
 * Created: Belozerov
 * Date: 10.11.2015
 */
public class BaseBindingFragment<B extends ViewDataBinding, M extends BaseFragmentViewModel> extends Fragment {
    public static final String ADD_TO_BACK_STACK = "add_to_back_stack";
    private final Class<B> bindingClass;
    private final Class<M> modelClass;
    private B binding;
    private M model;
    private static final String EXTRA_MODEL = "extra_model";


    @SuppressWarnings("unchecked")
    public BaseBindingFragment() {
        super();
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(BaseBindingFragment.class, getClass());
        this.bindingClass = (Class<B>) typeArguments[0];
        this.modelClass = (Class<M>) typeArguments[1];
    }

    @Override
    public void onResume() {
        super.onResume();
        model.onResume();
    }

    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    protected B getBinding() {
        return binding;
    }

    public M getModel() {
        return model;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = (B) DataBindingClassUtils.getViewDataBinding(bindingClass, inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createModel(savedInstanceState);
    }


    @SuppressWarnings("unchecked")
    private void createModel(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            model = (M) savedInstanceState.getSerializable(EXTRA_MODEL);
        }
        if (model == null) {
            try {
                model = modelClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        binding.setVariable(com.belozerov.cbrrate.BR.viewModel, model);
        model.onViewCreated();
        onViewModelCreated(model);
        model.onModelAttached();
    }

    protected void onViewModelCreated(M model) {

    }

    @Override
    public void onPause() {
        super.onPause();
        model.onPause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.onDestroyView();
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void hideProgress() {
        if (getActivity() == null)
            return;
        ProgressDialogFragment.hide((AppCompatActivity) getActivity());
    }

    protected void showProgress() {
        if (getActivity() == null)
            return;
        ProgressDialogFragment.show((AppCompatActivity) getActivity());
    }

    protected void showError(Throwable e) {
        if (getActivity() != null) {
            hideProgress();
            String message = e.getMessage();
            if (e instanceof HttpException) {
                try {
                    message = ((HttpException) e).response().errorBody().string();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    protected AppCompatActivity getAppCompatActivity(){
        return (AppCompatActivity) getActivity();
    }
}
