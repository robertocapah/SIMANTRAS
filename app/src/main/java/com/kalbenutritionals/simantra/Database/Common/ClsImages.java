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
    private int idPertanyaan;
    @DatabaseField
    private int intFillDetailId;
    public String txtLinkImages;

    public static String TXTFILLDTLID = "intFillDetailId";

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
