
package com.linkmindpro.http;

import android.app.Activity;
import android.view.View;

import com.linkmindpro.models.error.ErrorResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.view.SnackBarFactory;

public class ErrorManager implements AppConstant {

    private Activity mActivity;
    private View mView;
    private Object mObject;

    public ErrorManager(Activity activity, View view, Object object) {
        mActivity = activity;
        mView = view;
        mObject = object;
    }

    public void handleErrorResponse() {
        if (mObject == null) {
            return;
        }

        if (mObject instanceof ErrorResponse) {
            ErrorResponse response = ((ErrorResponse) mObject);
            if (response.getErrorMessage() != null)
                SnackBarFactory.createSnackBar(mActivity, mView, response.getErrorMessage());
        }

        if (mObject instanceof Throwable)
            SnackBarFactory.createSnackBar(mActivity, mView, ((Throwable) mObject).getLocalizedMessage());
    }
}

