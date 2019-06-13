package com.kalbenutritionals.simantra.ViewModel;

public class VMRequestDataSPM {
    public UserRequestSPM data;
    public DeviceInfo device_info;

    public UserRequestSPM getData() {
        return data;
    }

    public void setData(UserRequestSPM data) {
        this.data = data;
    }

    public DeviceInfo getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfo device_info) {
        this.device_info = device_info;
    }

    public class UserRequestSPM{
        public String username;
        public int intRoleId;
        public String txtNameApp;
        public String intOrgID;
        public String txtNoSPM;

        public String getTxtNoSPM() {
            return txtNoSPM;
        }

        public void setTxtNoSPM(String txtNoSPM) {
            this.txtNoSPM = txtNoSPM;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getIntRoleId() {
            return intRoleId;
        }

        public void setIntRoleId(int intRoleId) {
            this.intRoleId = intRoleId;
        }

        public String getTxtNameApp() {
            return txtNameApp;
        }

        public void setTxtNameApp(String txtNameApp) {
            this.txtNameApp = txtNameApp;
        }

        public String getIntOrgID() {
            return intOrgID;
        }

        public void setIntOrgID(String intOrgID) {
            this.intOrgID = intOrgID;
        }
    }
}
