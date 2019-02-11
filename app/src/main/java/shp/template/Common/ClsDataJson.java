package shp.template.Common;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class ClsDataJson {
    private List<ClsmUserLogin> listDatamUserLogin;


    private String txtUserId;
    private String txtSessionLogiId;
    private String intRoleId;
    private String intResult;
    private String txtDescription;
    private String txtMesagge;
    private String txtValue;
    private String txtMethod;
    private String txtVersionApp;
    private String dtLogin;
    private boolean isFromUnplan;

    public boolean isFromUnplan() {
        return isFromUnplan;
    }

    public void setFromUnplan(boolean fromUnplan) {
        isFromUnplan = fromUnplan;
    }

    public List<ClsmUserLogin> getListDatamUserLogin() {
        return listDatamUserLogin;
    }

    public void setListDatamUserLogin(List<ClsmUserLogin> listDatamUserLogin) {
        this.listDatamUserLogin = listDatamUserLogin;
    }



    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }

    public String getTxtSessionLogiId() {
        return txtSessionLogiId;
    }

    public void setTxtSessionLogiId(String txtSessionLogiId) {
        this.txtSessionLogiId = txtSessionLogiId;
    }


    public String getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(String intRoleId) {
        this.intRoleId = intRoleId;
    }

    public String getIntResult() {
        return intResult;
    }

    public void setIntResult(String intResult) {
        this.intResult = intResult;
    }

    public String getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription = txtDescription;
    }

    public String getTxtMesagge() {
        return txtMesagge;
    }

    public void setTxtMesagge(String txtMesagge) {
        this.txtMesagge = txtMesagge;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public String getTxtMethod() {
        return txtMethod;
    }

    public void setTxtMethod(String txtMethod) {
        this.txtMethod = txtMethod;
    }

    public String getTxtVersionApp() {
        return txtVersionApp;
    }

    public void setTxtVersionApp(String txtVersionApp) {
        this.txtVersionApp = txtVersionApp;
    }

    public String getDtLogin() {
        return dtLogin;
    }

    public void setDtLogin(String dtLogin) {
        this.dtLogin = dtLogin;
    }


    private String Property_txtUserId = "txtUserId";
    private String Property_txtSessionLogiId = "txtSessionLogiId";
    private String Property_intRoleId = "intRoleId";
    private String Property_intResult = "intResult";
    private String Property_txtDescription = "txtDescription";
    private String Property_txtMesagge = "txtMesagge";
    private String Property_txtValue = "txtValue";
    private String Property_txtMethod = "txtMethod";
    private String Property_txtVersionApp = "txtVersionApp";

}
