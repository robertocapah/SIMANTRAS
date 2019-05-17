package com.kalbenutritionals.simantra.ViewModel;

public class UserRequest {
    public String username;
    public String password;
    public int intRoleId;
    public String txtNameApp;
    public String txtUserToken;
    public String intOrgID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTxtUserToken() {
        return txtUserToken;
    }

    public void setTxtUserToken(String txtUserToken) {
        this.txtUserToken = txtUserToken;
    }

    public String getIntOrgID() {
        return intOrgID;
    }

    public void setIntOrgID(String intOrgID) {
        this.intOrgID = intOrgID;
    }
}
