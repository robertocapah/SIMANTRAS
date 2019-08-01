package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Roberto on 7/9/2019
 */
@DatabaseTable
public class ClsImages implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idImage;
    @DatabaseField
    private int idPertanyaan;
    @DatabaseField
    private int intFillDetailId;
    @DatabaseField
    public String txtLinkImages;
    @DatabaseField
    public String txtDesc;
    @DatabaseField
    public int intFlag;

    public static String TXTFILLDTLID = "intFillDetailId";
    public static String TXTintFlag = "intFlag";

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public int getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(int intFlag) {
        this.intFlag = intFlag;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getTxtLinkImages() {
        return txtLinkImages;
    }

    public void setTxtLinkImages(String txtLinkImages) {
        this.txtLinkImages = txtLinkImages;
    }

    public int getIdPertanyaan() {
        return idPertanyaan;
    }

    public void setIdPertanyaan(int idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public int getIntFillDetailId() {
        return intFillDetailId;
    }

    public void setIntFillDetailId(int intFillDetailId) {
        this.intFillDetailId = intFillDetailId;
    }
}
