package com.kalbe.mobiledevknlibs.Service;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Robert on 03/01/2018.
 */

public class ServiceRunningChecker extends Activity{
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
