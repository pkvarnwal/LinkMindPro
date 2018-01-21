package com.linkmindpro.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class PopUpHelper {

    public interface ConfirmPopUp {
        void onConfirm(boolean isConfirm);

        void onDismiss(boolean isDismiss);
    }

    public interface InfoPopupListener {
        void onConfirm();
    }

    public static AlertDialog showInfoAlertPopup(Activity activity, String message, final InfoPopupListener confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        confirmPopup.onConfirm();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
