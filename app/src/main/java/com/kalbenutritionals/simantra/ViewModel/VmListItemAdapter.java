package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/2/2018.
 */

public class VmListItemAdapter implements Serializable {

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public Integer getIntImgView() {
        return intImgView;
    }

    public void setIntImgView(Integer intImgView) {
        this.intImgView = intImgView;
    }

    public Drawable getDrwImg() {
        return drwImg;
    }

    public void setDrwImg(Drawable drwImg) {
        this.drwImg = drwImg;
    }

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

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public int getIntColor() {
        return intColor;
    }

    public void setIntColor(int intColor) {
        this.intColor = intColor;
    }
    public boolean isBoolSection() {
        return boolSection;
    }

    public void setBoolSection(boolean boolSection) {
        this.boolSection = boolSection;
    }
    public String getTxtImgName() {
        return txtImgName;
    }

    public void setTxtImgName(String txtImgName) {
        this.txtImgName = txtImgName;
    }

    public String getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(String txtStatus) {
        this.txtStatus = txtStatus;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public String getTxtLinkPdf() {
        return txtLinkPdf;
    }

    public void setTxtLinkPdf(String txtLinkPdf) {
        this.txtLinkPdf = txtLinkPdf;
    }

    public int getIntOrderId() {
        return intOrderId;
    }

    public void setIntOrderId(int intOrderId) {
        this.intOrderId = intOrderId;
    }

    public String getNoSPM() {
        return noSPM;
    }

    public void setNoSPM(String noSPM) {
        this.noSPM = noSPM;
    }

    public String txtId;
    public Integer intImgView;
    public Drawable drwImg;
    public String txtTittle;
    public String txtSubTittle;
    public String txtDesc;
    public String txtDate;
    public int intColor;
    public int intOrderId;
    public String noSPM;
    public boolean boolSection;
    public String txtImgName;
    public String txtStatus;
    public String txtLinkPdf;
    public int intStatus;
}
