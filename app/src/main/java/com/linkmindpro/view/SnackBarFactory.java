package com.linkmindpro.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import constraint.com.linkmindpro.R;

public class SnackBarFactory {

    public static Snackbar createSnackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();

        return snackbar;
    }

    public static Snackbar createSnackBarIndefinite(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        return snackbar;
    }

    public static Snackbar createSnackBarMultiLine(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        TextView snackBarTextView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackBarTextView.setMaxLines(999);
        snackbar.show();

        return snackbar;
    }

    public static void showNoInternetSnackBar(final Activity activity, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(activity.getString(R.string.setting), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        TextView snackBarTextView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackBarTextView.setMaxLines(999);
        snackbar.show();
    }
}
