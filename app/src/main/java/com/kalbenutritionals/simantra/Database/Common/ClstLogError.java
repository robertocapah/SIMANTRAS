package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 11/23/2018.
 */

@DatabaseTable
public class ClstLogError implements Serializable {
    @DatabaseField(id = true)
    private String txtGuiId;
    @DatabaseField
    private String txtUserId;
    @DatabaseField
    private String txtDeviceName;
    @DatabaseField
    private String txtOs;
    @DatabaseField
    private String txtFileName;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] blobImg;
    @DatabaseField
    private int intFlagPush;
    @DatabaseField
    private String dtDateLog;


    public String Property_txtGuiId = "txtGuiId";
    public String Property_txtUserId = "txtUserId";
    public String Property_txtDeviceName = "txtDeviceName";
    public String Property_txtOs = "txtOs";
    public String Property_txtFileName = "txtFileName";
    public String Property_blobImg = "blobImg";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_dtDateLog = "dtDateLog";
    public String Property_ListOfDatatLogError = "ListOfDatatLogError";

    public String getTxtGuiId() {
        return txtGuiId;
    }

    public void setTxtGuiId(String txtGuiId) {
        this.txtGuiId = txtGuiId;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }

    public String getTxtDeviceName() {
        return txtDeviceName;
    }

    public void setTxtDeviceName(String txtDeviceName) {
        this.txtDeviceName = txtDeviceName;
    }

    public String getTxtOs() {
        return txtOs;
    }

    public void setTxtOs(String txtOs) {
        this.txtOs = txtOs;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public byte[] getBlobImg() {
        return blobImg;
    }

    public void setBlobImg(byte[] blobImg) {
        this.blobImg = blobImg;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }

    public String getDtDateLog() {
        return dtDateLog;
    }

    public void setDtDateLog(String dtDateLog) {
        this.dtDateLog = dtDateLog;
    }
}
