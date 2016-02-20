package com.belozerov.cbrrate.binding;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created: Belozerov
 * Date: 29.11.2015
 */
public class DataBindingClassUtils {
    @SuppressWarnings("unchecked")
    private static HashMap<Class, Method> methodHashMap = new HashMap();

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    public static ViewDataBinding getViewDataBinding(Class clazz, LayoutInflater inflater, ViewGroup container) {
        Method method = methodHashMap.get(clazz);
        try {
            if (method == null) {
                method = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                methodHashMap.put(clazz, method);
            }
            return (ViewDataBinding) method.invoke(null, inflater, container, false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
