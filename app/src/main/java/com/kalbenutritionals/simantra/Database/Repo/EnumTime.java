package com.kalbenutritionals.simantra.Database.Repo;

/**
 * Created by Roberto on 6/24/2019
 */
public enum EnumTime {
    ScanBarcodeStarted(1),
    CheckingFinish(2),
    StartLoading(3),
    FinishLoading(4);

    EnumTime(int idStatus) {
        this.idStatus = idStatus;
    }
    public int getIdStatus(){return this.idStatus;}
    private final int idStatus;
}
