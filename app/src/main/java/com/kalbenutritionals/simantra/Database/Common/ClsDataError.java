package com.kalbenutritionals.simantra.Database.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/26/2018.
 */

public class ClsDataError {
    private String txtUserId;
    private String txtSessionLogiId;
    private String intRoleId;
    private String intResult;
    private String txtDescription;
    private String txtMesagge;
    private String txtValue;
    private String txtMethod;
    private String txtVersionApp;
    private List<ClstLogError> ListOfDatatLogError;

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

    public List<ClstLogError> getListOfDatatLogError() {
        return ListOfDatatLogError;
    }

    public void setListOfDatatLogError(List<ClstLogError> listOfDatatLogError) {
        ListOfDatatLogError = listOfDatatLogError;
    }
    public JSONObject txtJSON() throws JSONException {
        JSONObject resJson = new JSONObject();
        Collection<JSONObject> itemLIstQuery = new ArrayList<>();

        if (this.getListOfDatatLogError()!=null){
            ClstLogError dataError = new ClstLogError();
            itemLIstQuery = new ArrayList<>();
            for (ClstLogError data : this.getListOfDatatLogError()){
                JSONObject item = new JSONObject();
                item.put(dataError.Property_txtGuiId, String.valueOf(data.getTxtGuiId()));
                item.put(dataError.Property_txtUserId, String.valueOf(data.getTxtUserId()));
                item.put(dataError.Property_txtDeviceName, String.valueOf(data.getTxtDeviceName()));
                item.put(dataError.Property_txtOs, String.valueOf(data.getTxtOs()));
                item.put(dataError.Property_txtFileName, String.valueOf(data.getTxtFileName()));
                item.put(dataError.Property_blobImg, String.valueOf(data.getBlobImg()));
                itemLIstQuery.add(item);
            }
            resJson.put(dataError.Property_ListOfDatatLogError, new JSONArray(itemLIstQuery));
        }

        resJson.put(Property_txtUserId, getTxtUserId());
        resJson.put(Property_intRoleId, getIntRoleId());
        resJson.put(Property_txtSessionLogiId, getTxtSessionLogiId());
        resJson.put(Property_intResult, getIntResult());
        resJson.put(Property_txtMesagge, getTxtMesagge());
        resJson.put(Property_txtDescription, getTxtDescription());
        resJson.put(Property_txtValue, getTxtValue());
        resJson.put(Property_txtMethod, getTxtMethod());
        resJson.put(Property_txtVersionApp, getTxtVersionApp());

        return resJson;
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
