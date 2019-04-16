package com.kalbenutritionals.simantra.ViewModel;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

public class VmItemGroupNotifAdapter {
    private String txtTittle;
    private String txtSubTittle;
    private String txtImgName;
    private int intTransaksiId;
    private String txtTitleTransaksi;
    private String txtDescTransaksi;
    private List<String> txtLinkImage;
    private int intColor;

    public String getTxtTittle() {
        return txtTittle;
    }

    public void setTxtTittle(String txtTittle) {
        this.txtTittle = txtTittle;
    }

    public String getTxtSubTittle() {
        return txtSubTittle;
    }

    public void setTxtSubTittle(String txtSubTittle) {
        this.txtSubTittle = txtSubTittle;
    }

    public String getTxtImgName() {
        return txtImgName;
    }

    public void setTxtImgName(String txtImgName) {
        this.txtImgName = txtImgName;
    }

    public int getIntTransaksiId() {
        return intTransaksiId;
    }

    public void setIntTransaksiId(int intTransaksiId) {
        this.intTransaksiId = intTransaksiId;
    }

    public String getTxtTitleTransaksi() {
        return txtTitleTransaksi;
    }

    public void setTxtTitleTransaksi(String txtTitleTransaksi) {
        this.txtTitleTransaksi = txtTitleTransaksi;
    }

    public String getTxtDescTransaksi() {
        return txtDescTransaksi;
    }

    public void setTxtDescTransaksi(String txtDescTransaksi) {
        this.txtDescTransaksi = txtDescTransaksi;
    }

    public List<String> getTxtLinkImage() {
        return txtLinkImage;
    }

    public void setTxtLinkImage(List<String> txtLinkImage) {
        this.txtLinkImage = txtLinkImage;
    }

    public int getIntColor() {
        return intColor;
    }

    public void setIntColor(int intColor) {
        this.intColor = intColor;
    }
}
