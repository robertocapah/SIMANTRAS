package com.kalbenutritionals.simantra.Database;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public enum EnumCounterData {
    MonitorScedule (1),
    MonitoringLocation(2);
    EnumCounterData(int idConfigData){
        this.idConfigData = idConfigData;
    }

    public int getIdCounterData(){
     return this.idConfigData;
    }
    private final  int idConfigData;
}
