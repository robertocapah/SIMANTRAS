package com.kalbenutritionals.simantra.Database;

/**
 * Created by dewi.oktaviani on 21/06/2019.
 */

public enum EnumStatusDisposisi {
    Accept (1),
    Excalated (2),
    AccepetWithVariance (3),
    Reject (4)
    ;
    EnumStatusDisposisi(int idStatus){
        this.idStatus = idStatus;
    }
    public int getIdStatus(){return this.idStatus;}
    private final int idStatus;
}
