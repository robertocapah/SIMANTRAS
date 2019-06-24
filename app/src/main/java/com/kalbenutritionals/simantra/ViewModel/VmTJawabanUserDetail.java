package com.kalbenutritionals.simantra.ViewModel;

import java.util.List;

/**
 * Created by dewi.oktaviani on 20/06/2019.
 */

public class VmTJawabanUserDetail {
    private String txtJawaban;
    private int intmJawabanId;
    private boolean isQualified;
    private List<imageModel> dtImageModels;

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

    public List<imageModel> getDtImageModels() {
        return dtImageModels;
    }

    public void setDtImageModels(List<imageModel> dtImageModels) {
        this.dtImageModels = dtImageModels;
    }

    public class imageModel{
        public String imgName;
        public String imgPath;

        public imageModel(String imgName, String imgPath) {
            this.imgName = imgName;
            this.imgPath = imgPath;
        }
    }
}
