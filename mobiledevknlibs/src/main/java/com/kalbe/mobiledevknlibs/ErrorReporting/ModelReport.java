package com.kalbe.mobiledevknlibs.ErrorReporting;

/**
 * Created by Dewi Oktaviani on 11/23/2018.
 */

public class ModelReport {
    private String versionSDK;
    private String device;
    private String model;
    private String product;
    private String osVersion;
    private int IntUserID;
    private String TxtUserName;
    private int intRoleID;
    private String txtRoleName;

    public String getVersionSDK() {
        return versionSDK;
    }

    public void setVersionSDK(String versionSDK) {
        this.versionSDK = versionSDK;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public int getIntUserID() {
        return IntUserID;
    }

    public void setIntUserID(int intUserID) {
        IntUserID = intUserID;
    }

    public String getTxtUserName() {
        return TxtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        TxtUserName = txtUserName;
    }

    public int getIntRoleID() {
        return intRoleID;
    }

    public void setIntRoleID(int intRoleID) {
        this.intRoleID = intRoleID;
    }

    public String getTxtRoleName() {
        return txtRoleName;
    }

    public void setTxtRoleName(String txtRoleName) {
        this.txtRoleName = txtRoleName;
    }
}
