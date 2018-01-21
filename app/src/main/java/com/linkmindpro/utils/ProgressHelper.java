package com.linkmindpro.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressHelper {

    private static ProgressDialog dialog;

    public static void start(Context context, String message) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void stop() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
