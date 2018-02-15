package com.linkmindpro.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AppUtils implements AppConstant {

    private static AppUtils sInstance;

    public static AppUtils getInstance() {
        if (sInstance == null) {
            sInstance = new AppUtils();
        }

        return sInstance;
    }

    private AppUtils() {
    }

    public void display(Activity activity, String imgUri, ImageView imageView, int placeHolder) {
        if (!TextUtils.isEmpty(imgUri) && activity != null && imageView != null) {
            Picasso.with(activity).load(IMAGE_BASE_URL + imgUri.trim()).into(imageView);
        } else if (activity != null && imageView != null) {
            imageView.setImageResource(placeHolder);
        }
    }

    public void display(Activity activity, String imgUri, ImageView imageView, Integer placeHolder) {
        if (!TextUtils.isEmpty(imgUri) && activity != null && imageView != null) {
            Picasso.with(activity).load(IMAGE_BASE_URL + imgUri.trim()).into(imageView);
        } else if (placeHolder == null) {
            imageView.setVisibility(View.GONE);
        }
    }
}
