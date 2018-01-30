package com.linkmindpro.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermissionClass {

    private Activity mActivity;

    public PermissionClass(Activity activity) {
        this.mActivity = activity;
    }

    public boolean checkPermission(String... permissions) {
        for(String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }

        return true;
    }

    public void requestPermission(int REQUEST_PERMISSION_CODE, String... permissions) {
        ArrayList<String> neededPermission = new ArrayList<>();

        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                neededPermission.add(permission);
            }
        }
        if(neededPermission.size() > 0) {
            ActivityCompat.requestPermissions(mActivity, neededPermission.toArray(new String[neededPermission.size()]), REQUEST_PERMISSION_CODE);
        }
    }
}