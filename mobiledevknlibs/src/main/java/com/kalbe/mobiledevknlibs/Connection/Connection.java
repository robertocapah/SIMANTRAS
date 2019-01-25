package com.kalbe.mobiledevknlibs.Connection;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;

/**
 * Created by Dewi Oktaviani on 2/15/2018.
 */

public class Connection {
    public static String checkConnectionMobile(ConnectivityManager connectivityManager){
        String connection= null;
        //mobile
        NetworkInfo.State mobile = connectivityManager.getNetworkInfo(0).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING){
            connection = "connected";
        } else {
            connection = "no connectivity";
        }
        return connection;
    }
    public static String checkConnectionWifi(ConnectivityManager connectivityManager){
        String connection= null;
        //wifi
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(1).getState();
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){
            connection = "connected";
        }else {
            connection = "no connectivity";
        }
        return connection;
    }
}
