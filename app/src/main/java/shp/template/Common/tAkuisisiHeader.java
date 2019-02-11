package shp.template.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

@DatabaseTable
public class tAkuisisiHeader implements Serializable {
    @DatabaseField(id = true, columnName = "txtHeaderId")
    private String txtHeaderId;
    @DatabaseField(columnName = "intSubSubActivityId")
    private int intSubSubActivityId;
    @DatabaseField(columnName = "intSubSubActivityTypeId")
    private int intSubSubActivityTypeId;
    @DatabaseField(columnName = "txtNoDoc")
    private String txtNoDoc;
    @DatabaseField(columnName = "dtExpiredDate")
    private String dtExpiredDate;
    @DatabaseField(columnName = "intUserId")
    private int intUserId;
    @DatabaseField(columnName = "intRoleId")
    private int intRoleId;
    @DatabaseField(columnName = "intDokterId")
    private  String intDokterId;
    @DatabaseField(columnName = "intApotekID")
    private String intApotekID;
    @DatabaseField(columnName = "intAreaId")
    private String intAreaId;
    @DatabaseField(columnName = "intFlagPush")
    private int intFlagPush;
    @DatabaseField
    private String txtRealisasiVisitId;
    @DatabaseField
    private int  intFlagShow;
    @DatabaseField
    private String txtUserName;


    public String Property_intSubSubActivityId = "intSubSubActivityId";
    public String Property_txtHeaderId = "txtHeaderId";
    public String Property_intFlagPush = "intFlagPush";

    public String Property_intSubSubActivityTypeId = "intSubSubActivityTypeId";
    public String Property_txtNoDoc = "txtNoDoc";
    public String Property_dtExpiredDate = "dtExpiredDate";
    public String Property_intUserId = "intUserId";
    public String Property_intRoleId = "intRoleId";
    public String Property_intDokterId = "intDokterId";
    public String Property_intApotekID = "intApotekID";
//    public String Property_intOutletId = "intOutletId";
    public String Property_intAreaId = "intAreaId";
    public String Property_txtRealisasiVisitId = "txtRealisasiVisitId";
    public String Property_ListDataOftAkuisisiHeader = "ListDataOftAkuisisiHeader";
    public String Property_intFlagSow = "intFlagShow";
    public String Property_txtUserName = "txtUserName";

    public String getTxtHeaderId() {
        return txtHeaderId;
    }

    public void setTxtHeaderId(String txtHeaderId) {
        this.txtHeaderId = txtHeaderId;
    }

    //    public String getTxtName() {
//        return txtName;
//    }
//
//    public void setTxtName(String txtName) {
//        this.txtName = txtName;
//    }
//
//    public String getTxtDesc() {
//        return txtDesc;
//    }
//
//    public void setTxtDesc(String txtDesc) {
//        this.txtDesc = txtDesc;
//    }

    public int getIntSubSubActivityId() {
        return intSubSubActivityId;
    }

    public void setIntSubSubActivityId(int intSubSubActivityId) {
        this.intSubSubActivityId = intSubSubActivityId;
    }

    public String getDtExpiredDate() {
        return dtExpiredDate;
    }

    public void setDtExpiredDate(String dtExpiredDate) {
        this.dtExpiredDate = dtExpiredDate;
    }

    public int getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(int intUserId) {
        this.intUserId = intUserId;
    }

    public int getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(int intRoleId) {
        this.intRoleId = intRoleId;
    }

//    public int getIntOutletId() {
//        return intOutletId;
//    }
//
//    public void setIntOutletId(int intOutletId) {
//        this.intOutletId = intOutletId;
//    }


    public int getIntSubSubActivityTypeId() {
        return intSubSubActivityTypeId;
    }

    public void setIntSubSubActivityTypeId(int intSubSubActivityTypeId) {
        this.intSubSubActivityTypeId = intSubSubActivityTypeId;
    }

    public String getTxtNoDoc() {
        return txtNoDoc;
    }

    public void setTxtNoDoc(String txtNoDoc) {
        this.txtNoDoc = txtNoDoc;
    }

    public String getIntApotekID() {
        return intApotekID;
    }

    public void setIntApotekID(String intApotekID) {
        this.intApotekID = intApotekID;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }

    public String getTxtRealisasiVisitId() {
        return txtRealisasiVisitId;
    }

    public void setTxtRealisasiVisitId(String txtRealisasiVisitId) {
        this.txtRealisasiVisitId = txtRealisasiVisitId;
    }

    public String getIntDokterId() {
        return intDokterId;
    }

    public void setIntDokterId(String intDokterId) {
        this.intDokterId = intDokterId;
    }

    public String getIntAreaId() {
        return intAreaId;
    }

    public void setIntAreaId(String intAreaId) {
        this.intAreaId = intAreaId;
    }

    public int getIntFlagShow() {
        return intFlagShow;
    }

    public void setIntFlagShow(int intFlagShow) {
        this.intFlagShow = intFlagShow;
    }

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }
}
