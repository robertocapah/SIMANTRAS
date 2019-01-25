package shp.kalbecallplanaedp.Data;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public enum enumCounterData {
    MonitorScedule (1),
    MonitoringLocation(2);
    enumCounterData(int idConfigData){
        this.idConfigData = idConfigData;
    }

    public int getIdCounterData(){
     return this.idConfigData;
    }
    private final  int idConfigData;
}
