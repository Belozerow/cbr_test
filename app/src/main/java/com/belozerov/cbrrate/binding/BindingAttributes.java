package com.belozerov.cbrrate.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created: Belozerov
 * Date: 11.10.2015
 */
@SuppressWarnings("unused")
public class BindingAttributes {
    private static HashMap<String, Typeface> typefaceHashMap = new HashMap<>();


    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        if (url == null)
            return;
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("bind:customFont")
    public static void setCustomFont(TextView textView, String font) {
        Typeface tf = typefaceHashMap.get(font);
        if (tf == null) {
            tf = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + font);
            typefaceHashMap.put(font, tf);
        }
        textView.setTypeface(tf);
    }

    @BindingAdapter("android:src")
    public static void setBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }


    @BindingAdapter({"bind:imageRes"})
    public static void setImageRes(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }

    @BindingAdapter({"bind:imageResName"})
    public static void setImageResName(ImageView imageView, String resName) {
        Context context = imageView.getContext();
        if (TextUtils.isEmpty(resName)) {
            return;
        }
        int id = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
        imageView.setImageResource(id);
    }

    @BindingAdapter({"app:error"})
    public static void setTextInputError(TextInputLayout textInputLayout, String error) {
        if (TextUtils.isEmpty(error)) {
            textInputLayout.setError(null);
        } else {
            textInputLayout.setError(error);
        }
    }

    @BindingAdapter({"app:onActionDone"})
    public static void setOnDoneListener(EditText editText, TextView.OnEditorActionListener listener) {
        editText.setOnEditorActionListener(listener);
    }

    @BindingAdapter({"android:visibility"})
    public static void setOnDoneListener(View view, boolean val) {
        view.setVisibility(val ? View.VISIBLE : View.GONE);
    }
}
