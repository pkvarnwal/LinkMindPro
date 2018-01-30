package com.linkmindpro.utils;

import android.app.Activity;
import android.content.Intent;

import static android.content.Intent.ACTION_GET_CONTENT;

public class Gallery {
    private final Activity mActivity;

    public Gallery(final Activity activity) {
        this.mActivity = activity;
    }

    public Intent openPhoto(final int RequestCode) {
        final Intent intent = new Intent(ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select Image"), RequestCode);

        return intent;
    }
}
