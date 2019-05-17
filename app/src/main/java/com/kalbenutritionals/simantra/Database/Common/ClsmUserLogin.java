package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

@DatabaseTable
public class ClsmUserLogin implements Serializable {
    @DatabaseField(id = true)
    private String txtGuID;
    @DatabaseField
    private int IntUserID;
    @DatabaseField(columnName = "TxtUserName")
    private String TxtUserName;
    @DatabaseField(columnName = "TxtNick")
    private String TxtNick;
    @DatabaseField(columnName = "TxtEmpID")
    private String TxtEmpID;
    @DatabaseField(columnName = "TxtEmail")
    private String TxtEmail;
    @DatabaseField(columnName = "OrgId")
    private String OrgId;
    @DatabaseField(columnName = "Password")
    private String Password;
    @DatabaseField(columnName = "Token")
    private String Token;
    @DatabaseField(columnName = "intRoleID")
    private int intRoleID;
    @DatabaseField(columnName = "txtRoleName")
    private String txtRoleName;
    @DatabaseField(columnName = "IntDepartmentID")
    private int IntDepartmentID;
    @DatabaseField(columnName = "IntLOBID")
    private int IntLOBID;
    @DatabaseField(columnName = "TxtCompanyCode")
    private String TxtCompanyCode;
    @DatabaseField
    private String dtLogOut;
    @DatabaseField
    private String dtLogIn;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] BlobImg;
    @DatabaseField
    private String txtFileName;


    public String Property_IntUserID = "IntUserID";
    public String Property_TxtUserName = "TxtUserName";
    public String Property_TxtNick = "TxtNick";
    public String Property_TxtEmpID = "TxtEmpID";
    public String Property_TxtEmail = "TxtEmail";
    public String Property_intRoleID = "intRoleID";
    public String Property_txtRoleName = "txtRoleName";
    public String Property_IntDepartmentID = "IntDepartmentID";
    public String Property_IntLOBID = "IntLOBID";
    public String Property_TxtCompanyCode = "TxtCompanyCode";
    public String Property_ListDatamUserLogin = "ListDatamUserLogin";
    public String Property_txtGuID = "txtGuID";


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getTxtNick() {
        return TxtNick;
    }

    public void setTxtNick(String txtNick) {
        TxtNick = txtNick;
    }

    public String getTxtEmpID() {
        return TxtEmpID;
    }

    public void setTxtEmpID(String txtEmpID) {
        TxtEmpID = txtEmpID;
    }

    public String getTxtEmail() {
        return TxtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        TxtEmail = txtEmail;
    }

    public int getIntDepartmentID() {
        return IntDepartmentID;
    }

    public void setIntDepartmentID(int intDepartmentID) {
        IntDepartmentID = intDepartmentID;
    }

    public int getIntLOBID() {
        return IntLOBID;
    }

    public void setIntLOBID(int intLOBID) {
        IntLOBID = intLOBID;
    }

    public String getTxtCompanyCode() {
        return TxtCompanyCode;
    }

    public void setTxtCompanyCode(String txtCompanyCode) {
        TxtCompanyCode = txtCompanyCode;
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

    public String getDtLogOut() {
        return dtLogOut;
    }

    public void setDtLogOut(String dtLogOut) {
        this.dtLogOut = dtLogOut;
    }

    public String getDtLogIn() {
        return dtLogIn;
    }

    public void setDtLogIn(String dtLogIn) {
        this.dtLogIn = dtLogIn;
    }

    public String getTxtGuID() {
        return txtGuID;
    }

    public void setTxtGuID(String txtGuID) {
        this.txtGuID = txtGuID;
    }

    public byte[] getBlobImg() {
        return BlobImg;
    }

    public void setBlobImg(byte[] blobImg) {
        BlobImg = blobImg;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }
}
