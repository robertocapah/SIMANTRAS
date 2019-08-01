package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Roberto on 7/15/2019
 */
@DatabaseTable
public class ClsExpedition implements Serializable {
    @DatabaseField(id = true)
    private int VENDOR_ID;
    @DatabaseField
    private String VENDOR_NAME;

    public int getVENDOR_ID() {
        return VENDOR_ID;
    }

    public void setVENDOR_ID(int VENDOR_ID) {
        this.VENDOR_ID = VENDOR_ID;
    }

    public String getVENDOR_NAME() {
        return VENDOR_NAME;
    }

    public void setVENDOR_NAME(String VENDOR_NAME) {
        this.VENDOR_NAME = VENDOR_NAME;
    }
}
