package com.kalbenutritionals.simantra.ViewModel;

/**
 * Created by dewi.oktaviani on 20/06/2019.
 */

public class VmTJawabanUserDetail {
    private String txtJawaban;
    private int intmJawabanId;
    private boolean isQualified;

    public String getTxtJawaban() {
        return txtJawaban;
    }

    public void setTxtJawaban(String txtJawaban) {
        this.txtJawaban = txtJawaban;
    }

    public int getIntmJawabanId() {
        return intmJawabanId;
    }

    public void setIntmJawabanId(int intmJawabanId) {
        this.intmJawabanId = intmJawabanId;
    }

    public boolean isQualified() {
        return isQualified;
    }

    public void setQualified(boolean qualified) {
        isQualified = qualified;
    }
}
