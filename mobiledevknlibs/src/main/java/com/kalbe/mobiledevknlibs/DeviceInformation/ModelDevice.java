package com.kalbe.mobiledevknlibs.DeviceInformation;

/**
 * Created by Robert on 04/01/2018.
 */

public class ModelDevice {
    private String versionSDK;
    private String device;
    private String model;
    private String product;
    private String osVersion;

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

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
}
