package com.kalbenutritionals.simantra.Database.Repo;

/**
 * Created by Roberto on 6/24/2019
 */
public enum EnumTime {
    ScanBarcodeStarted(1),//DARI API
    CheckingFinish(2),
    StartLoading(3),
    FinishLoading(4),
    ScanBarcodeUnloading(5),//DARI API
    StartUnloading(6),
    FinishUnloading(7);

    EnumTime(int idStatus) {
        this.idStatus = idStatus;
    }
    public int getIdStatus(){return this.idStatus;}
    private final int idStatus;
}
