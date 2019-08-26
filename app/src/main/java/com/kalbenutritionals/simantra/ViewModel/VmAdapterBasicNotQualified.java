package com.kalbenutritionals.simantra.ViewModel;

import java.util.List;

public class VmAdapterBasicNotQualified {
    private List<VmTJawabanUserDetail.imageModel> dtImages;
    private String title;
    private String subtitle;
    private String txtReason;
    private int intDtlId;
    private int intHdrId;
    private int intFormDtlId;

    public List<VmTJawabanUserDetail.imageModel> getDtImages() {
        return dtImages;
    }

    public void setDtImages(List<VmTJawabanUserDetail.imageModel> dtImages) {
        this.dtImages = dtImages;
    }

    public int getIntFormDtlId() {
        return intFormDtlId;
    }

    public void setIntFormDtlId(int intFormDtlId) {
        this.intFormDtlId = intFormDtlId;
    }

    public int getIntDtlId() {
        return intDtlId;
    }

    public void setIntDtlId(int intDtlId) {
        this.intDtlId = intDtlId;
    }

    public int getIntHdrId() {
        return intHdrId;
    }

    public void setIntHdrId(int intHdrId) {
        this.intHdrId = intHdrId;
    }

    public String getTxtReason() {
        return txtReason;
    }

    public void setTxtReason(String txtReason) {
        this.txtReason = txtReason;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
