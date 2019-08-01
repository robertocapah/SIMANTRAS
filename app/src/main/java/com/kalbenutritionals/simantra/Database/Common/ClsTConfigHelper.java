package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Roberto on 7/11/2019
 */
@DatabaseTable
public class ClsTConfigHelper implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String txtKey;
    @DatabaseField
    private String txtValue;
    @DatabaseField
    private String txtDefaultValue;
    @DatabaseField
    private String txtKeterangan;

    public int getId() {
        return id;
    }

    public String getTxtKey() {
        return txtKey;
    }

    public void setTxtKey(String txtKey) {
        this.txtKey = txtKey;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public String getTxtDefaultValue() {
        return txtDefaultValue;
    }

    public void setTxtDefaultValue(String txtDefaultValue) {
        this.txtDefaultValue = txtDefaultValue;
    }

    public String getTxtKeterangan() {
        return txtKeterangan;
    }

    public void setTxtKeterangan(String txtKeterangan) {
        this.txtKeterangan = txtKeterangan;
    }
}
