package com.kalbe.mobiledevknlibs.DeviceInformation;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by Robert on 04/01/2018.
 */

public class
DeviceInformation {
    SensorManager smm;
    List<Sensor> sensors;
    public static ModelDevice getDeviceInformation(){
        ModelDevice deviceInfo = new ModelDevice();
        if (android.os.Build.DEVICE != null){
            deviceInfo.setDevice(android.os.Build.DEVICE);
        }
        if (android.os.Build.MODEL != null){
            deviceInfo.setModel(android.os.Build.MODEL);
        }
        if(android.os.Build.PRODUCT != null){
            deviceInfo.setProduct(android.os.Build.PRODUCT);
        }
        if (android.os.Build.VERSION.SDK!=null){
            deviceInfo.setVersionSDK(android.os.Build.VERSION.SDK);
        }
        if (System.getProperty("os.version") != null){
            deviceInfo.setOsVersion(System.getProperty("os.version"));
        }
        return deviceInfo;
    }
    public List<Sensor> getSensorInformation(Context context){
        smm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensors = smm.getSensorList(Sensor.TYPE_ALL);
        return sensors;
    }
}
