package com.kalbenutritionals.simantra.ViewModel;

import java.util.List;

/**
 * Created by dewi.oktaviani on 21/06/2019.
 */

public class VmTJawabanUserHeader {
    private int intFillHeaderId;
    private int intStatusDisposisi;
    private String txtLoadingMessage;
    private List<VmAdapterBasicNotQualified> ListRejection;
    private List<VmTJawabanUser> listJawabanUser;

    public List<VmAdapterBasicNotQualified> getListRejection() {
        return ListRejection;
    }

    public void setListRejection(List<VmAdapterBasicNotQualified> listRejection) {
        ListRejection = listRejection;
    }

    public String getTxtLoadingMessage() {
        return txtLoadingMessage;
    }

    public void setTxtLoadingMessage(String txtLoadingMessage) {
        this.txtLoadingMessage = txtLoadingMessage;
    }

    public int getIntFillHeaderId() {
        return intFillHeaderId;
    }

    public void setIntFillHeaderId(int intFillHeaderId) {
        this.intFillHeaderId = intFillHeaderId;
    }

    public int getIntStatusDisposisi() {
        return intStatusDisposisi;
    }

    public void setIntStatusDisposisi(int intStatusDisposisi) {
        this.intStatusDisposisi = intStatusDisposisi;
    }

    public List<VmTJawabanUser> getListJawabanUser() {
        return listJawabanUser;
    }

    public void setListJawabanUser(List<VmTJawabanUser> listJawabanUser) {
        this.listJawabanUser = listJawabanUser;
    }
}
