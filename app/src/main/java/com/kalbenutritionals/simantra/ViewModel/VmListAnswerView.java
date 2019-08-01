package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.Bitmap;
import android.view.View;

public class VmListAnswerView {
    private int intPertanyaanId;
    private View vwJawaban;
    private int intPosition;
    private String txtReason;
    private Bitmap bitmap;
    private String pathImage;
    private int type;

    public String getTxtReason() {
        return txtReason;
    }

    public void setTxtReason(String txtReason) {
        this.txtReason = txtReason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIntPosition() {
        return intPosition;
    }

    public void setIntPosition(int intPosition) {
        this.intPosition = intPosition;
    }

    public int getIntPertanyaanId() {
        return intPertanyaanId;
    }

    public void setIntPertanyaanId(int intPertanyaanId) {
        this.intPertanyaanId = intPertanyaanId;
    }

    public View getVwJawaban() {
        return vwJawaban;
    }

    public void setVwJawaban(View vwJawaban) {
        this.vwJawaban = vwJawaban;
    }
}
