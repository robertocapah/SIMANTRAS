package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Roberto on 7/15/2019
 */
@DatabaseTable
public class ClsOrganisation implements Serializable {
    @DatabaseField(id = true)
    private int ORGANIZATION_ID;
    @DatabaseField
    private String ORGANIZATION_CODE;
    @DatabaseField
    private String ORGANIZATION_NAME;

    public int getORGANIZATION_ID() {
        return ORGANIZATION_ID;
    }

    public void setORGANIZATION_ID(int ORGANIZATION_ID) {
        this.ORGANIZATION_ID = ORGANIZATION_ID;
    }

    public String getORGANIZATION_CODE() {
        return ORGANIZATION_CODE;
    }

    public void setORGANIZATION_CODE(String ORGANIZATION_CODE) {
        this.ORGANIZATION_CODE = ORGANIZATION_CODE;
    }

    public String getORGANIZATION_NAME() {
        return ORGANIZATION_NAME;
    }

    public void setORGANIZATION_NAME(String ORGANIZATION_NAME) {
        this.ORGANIZATION_NAME = ORGANIZATION_NAME;
    }
}
