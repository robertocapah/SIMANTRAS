package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/2/2018.
 */
@DatabaseTable
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


    @DatabaseField(id = true, columnName = "txtId")
    public String txtId;
    @DatabaseField(columnName = "intImgView")
    public Integer intImgView;
    @DatabaseField(columnName = "drwImg")
    public Drawable drwImg;
    @DatabaseField(columnName = "txtTitle")
    public String txtTittle;
    @DatabaseField(columnName = "txtSubTittle")
    public String txtSubTittle;
    @DatabaseField(columnName = "txtDesc")
    public String txtDesc;
    @DatabaseField(columnName = "txtDate")
    public String txtDate;
    @DatabaseField(columnName = "intColor")
    public int intColor;
    @DatabaseField(columnName = "boolSection")
    public boolean boolSection;
    @DatabaseField(columnName = "txtImgName")
    public String txtImgName;
}
