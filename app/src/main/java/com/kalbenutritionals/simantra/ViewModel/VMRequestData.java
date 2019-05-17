package com.kalbenutritionals.simantra.ViewModel;

public class VMRequestData {
    public UserRequest data;
    public DeviceInfo device_info;

    public UserRequest getData() {
        return data;
    }

    public void setData(UserRequest data) {
        this.data = data;
    }

    public DeviceInfo getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfo device_info) {
        this.device_info = device_info;
    }
}
