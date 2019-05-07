package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class VmListImageAdapter implements Serializable{
    private int intId;
    private int intPosition;
    private Bitmap bmpImage;

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }

    public int getIntPosition() {
        return intPosition;
    }

    public void setIntPosition(int intPosition) {
        this.intPosition = intPosition;
    }

    public Bitmap getBmpImage() {
        return bmpImage;
    }

    public void setBmpImage(Bitmap bmpImage) {
        this.bmpImage = bmpImage;
    }
}
