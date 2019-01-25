package shp.kalbecallplanaedp.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class clsDataJson {
    private List<mUserLogin> ListDatamUserLogin;
    private List<tAkuisisiHeader> ListDataOftAkuisisiHeader;
    private List<tAkuisisiDetail> ListDataOftAkuisisiDetail;
    private List<tProgramVisitSubActivity> ListOfDatatProgramVisitSubActivity;
    private List<tProgramVisit> ListDataOftProgramVisit;
    private List<tProgramVisitSubActivityAttachment> ListOfDatatProgramVisitSubActivityAttachment;
    private List<tRealisasiVisitPlan> ListOfDatatRealisasiVisitPlan;
    private List<tMaintenanceHeader> ListOfDatatMaintenanceHeader;
    private List<tMaintenanceDetail> ListOfDatatMaintenanceDetail;
    private List<tInfoProgramHeader> ListOfDatatInfoProogramHeader;
    private List<tInfoProgramDetail> ListOfDatatInfoProgramDetail;

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

    public List<mUserLogin> getListDatamUserLogin() {
        return ListDatamUserLogin;
    }

    public void setListDatamUserLogin(List<mUserLogin> listDatamUserLogin) {
        ListDatamUserLogin = listDatamUserLogin;
    }

    public List<tAkuisisiHeader> getListDataOftAkuisisiHeader() {
        return ListDataOftAkuisisiHeader;
    }

    public void setListDataOftAkuisisiHeader(List<tAkuisisiHeader> listDataOftAkuisisiHeader) {
        ListDataOftAkuisisiHeader = listDataOftAkuisisiHeader;
    }

    public List<tAkuisisiDetail> getListDataOftAkuisisiDetail() {
        return ListDataOftAkuisisiDetail;
    }

    public void setListDataOftAkuisisiDetail(List<tAkuisisiDetail> listDataOftAkuisisiDetail) {
        ListDataOftAkuisisiDetail = listDataOftAkuisisiDetail;
    }

    public List<tProgramVisitSubActivity> getListOfDatatProgramVisitSubActivity() {
        return ListOfDatatProgramVisitSubActivity;
    }

    public void setListOfDatatProgramVisitSubActivity(List<tProgramVisitSubActivity> listOfDatatProgramVisitSubActivity) {
        ListOfDatatProgramVisitSubActivity = listOfDatatProgramVisitSubActivity;
    }

    public List<tProgramVisit> getListDataOftProgramVisit() {
        return ListDataOftProgramVisit;
    }

    public void setListDataOftProgramVisit(List<tProgramVisit> listDataOftProgramVisit) {
        ListDataOftProgramVisit = listDataOftProgramVisit;
    }

    public List<tProgramVisitSubActivityAttachment> getListOfDatatProgramVisitSubActivityAttachment() {
        return ListOfDatatProgramVisitSubActivityAttachment;
    }

    public void setListOfDatatProgramVisitSubActivityAttachment(List<tProgramVisitSubActivityAttachment> listOfDatatProgramVisitSubActivityAttachment) {
        ListOfDatatProgramVisitSubActivityAttachment = listOfDatatProgramVisitSubActivityAttachment;
    }

    public List<tRealisasiVisitPlan> getListOfDatatRealisasiVisitPlan() {
        return ListOfDatatRealisasiVisitPlan;
    }

    public void setListOfDatatRealisasiVisitPlan(List<tRealisasiVisitPlan> listOfDatatRealisasiVisitPlan) {
        ListOfDatatRealisasiVisitPlan = listOfDatatRealisasiVisitPlan;
    }

    public List<tMaintenanceHeader> getListOfDatatMaintenanceHeader() {
        return ListOfDatatMaintenanceHeader;
    }

    public void setListOfDatatMaintenanceHeader(List<tMaintenanceHeader> listOfDatatMaintenanceHeader) {
        ListOfDatatMaintenanceHeader = listOfDatatMaintenanceHeader;
    }

    public List<tMaintenanceDetail> getListOfDatatMaintenanceDetail() {
        return ListOfDatatMaintenanceDetail;
    }

    public void setListOfDatatMaintenanceDetail(List<tMaintenanceDetail> listOfDatatMaintenanceDetail) {
        ListOfDatatMaintenanceDetail = listOfDatatMaintenanceDetail;
    }

    public List<tInfoProgramHeader> getListOfDatatInfoProogramHeader() {
        return ListOfDatatInfoProogramHeader;
    }

    public void setListOfDatatInfoProogramHeader(List<tInfoProgramHeader> listOfDatatInfoProogramHeader) {
        ListOfDatatInfoProogramHeader = listOfDatatInfoProogramHeader;
    }

    public List<tInfoProgramDetail> getListOfDatatInfoProgramDetail() {
        return ListOfDatatInfoProgramDetail;
    }

    public void setListOfDatatInfoProgramDetail(List<tInfoProgramDetail> listOfDatatInfoProgramDetail) {
        ListOfDatatInfoProgramDetail = listOfDatatInfoProgramDetail;
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

    public JSONObject txtJSON() throws JSONException{
        JSONObject resJson = new JSONObject();
        Collection<JSONObject> itemLIstQuery = new ArrayList<>();

        if (this.getListDatamUserLogin()!=null){
            mUserLogin dataLogin = new mUserLogin();
            itemLIstQuery = new ArrayList<>();
            for (mUserLogin data : this.getListDatamUserLogin()){
                JSONObject item = new JSONObject();
            }
        }

//        if (this.getListDataOftProgramVisit()!=null){
//            tProgramVisit dataHeader = new tProgramVisit();
//            itemLIstQuery = new ArrayList<>();
////            for (tProgramVisit data: this.getListDataOftProgramVisit()){
////                JSONObject item = new JSONObject();
////                item.put(dataHeader.Property_txtProgramVisitId, String.valueOf(data.getTxtProgramVisitId()));
////                item.put(dataHeader.Property_intUserId, String.valueOf(data.getIntUserId()));
////                item.put(dataHeader.Property_intRoleId, String.valueOf(data.getIntRoleId()));
////                item.put(dataHeader.Property_txtNotes, String.valueOf(data.getTxtNotes()));
////                item.put(dataHeader.Property_intType, String.valueOf(data.getIntType()));
////                item.put(dataHeader.Property_intStatus, String.valueOf(data.getIntStatus()));
//////                item.put(dataHeader.Property_dtStart, String.valueOf(data.getDtStart()));
//////                item.put(dataHeader.Property_dtEnd, String.valueOf(data.getDtEnd()));
////                item.put(dataHeader.Property_dtLogin, getDtLogin());
////                item.put(dataHeader.Property_intFlagPush, String.valueOf(data.getIntFlagPush()));
////                itemLIstQuery.add(item);
////            }
//            resJson.put(dataHeader.Property_ListDataOftProgramVisit, new JSONArray(itemLIstQuery));
//        }

        if(this.getListOfDatatProgramVisitSubActivity()!=null){
            tProgramVisitSubActivity dataVisit = new tProgramVisitSubActivity();
            itemLIstQuery = new ArrayList<>();
            for (tProgramVisitSubActivity data : this.getListOfDatatProgramVisitSubActivity()){
                JSONObject item = new JSONObject();
                item.put(dataVisit.Property_txtProgramVisitSubActivityId, String.valueOf(data.getTxtProgramVisitSubActivityId()));
                item.put(dataVisit.Property_txtApotekName, String.valueOf(data.getTxtApotekName()));
                item.put(dataVisit.Property_intType, String.valueOf(data.getIntType()));
                item.put(dataVisit.Property_txtAreaName, String.valueOf(data.getTxtAreaName()));
                item.put(dataVisit.Property_txtDokterId, String.valueOf(data.getTxtDokterId()));
                item.put(dataVisit.Property_txtNotes, String.valueOf(data.getTxtNotes()));
                item.put(dataVisit.Property_txtDokterName, String.valueOf(data.getTxtDokterName()));
                item.put(dataVisit.Property_txtProgramVisitId, String.valueOf(data.getTxtProgramVisitId()));
                item.put(dataVisit.Property_txtApotekId, String.valueOf(data.getTxtApotekId()));
                item.put(dataVisit.Property_intActivityId, String.valueOf(data.getIntActivityId()));
                item.put(dataVisit.Property_intSubActivityId, String.valueOf(data.getIntSubActivityId()));
                item.put(dataVisit.Property_txtAreaId, String.valueOf(data.getTxtAreaId()));
                item.put(dataVisit.Property_dtStart, String.valueOf(data.getDtStart()));
                item.put(dataVisit.Property_dtEnd, String.valueOf(data.getDtEnd()));
                itemLIstQuery.add(item);
            }
            resJson.put(dataVisit.Property_ListOfDatatProgramVisitSubActivity, new JSONArray(itemLIstQuery));
        }

        if (this.getListOfDatatRealisasiVisitPlan()!=null){
            tRealisasiVisitPlan dataRealisasi = new tRealisasiVisitPlan();
            itemLIstQuery = new ArrayList<>();
            for (tRealisasiVisitPlan data : this.getListOfDatatRealisasiVisitPlan()){
                JSONObject item = new JSONObject();
                item.put(dataRealisasi.Property_txtRealisasiVisitId, String.valueOf(data.getTxtRealisasiVisitId()));
                item.put(dataRealisasi.Property_txtProgramVisitSubActivityId, String.valueOf(data.getTxtProgramVisitSubActivityId()));
                item.put(dataRealisasi.Property_intUserId, String.valueOf(data.getIntUserId()));
                item.put(dataRealisasi.Property_intRoleID, String.valueOf(data.getIntRoleID()));
                item.put(dataRealisasi.Property_txtDokterId, String.valueOf(data.getTxtDokterId()));
                item.put(dataRealisasi.Property_txtDokterName, String.valueOf(data.getTxtDokterName()));
                item.put(dataRealisasi.Property_txtApotekId, String.valueOf(data.getTxtApotekId()));
                item.put(dataRealisasi.Property_txtApotekName, String.valueOf(data.getTxtApotekName()));
                item.put(dataRealisasi.Property_dtCheckIn, String.valueOf(data.getDtCheckIn()));
                item.put(dataRealisasi.Property_dtCheckOut, String.valueOf(data.getDtCheckOut()));
                item.put(dataRealisasi.Property_dtDateRealisasi, String.valueOf(data.getDtDateRealisasi()));
                item.put(dataRealisasi.Property_dtDatePlan, String.valueOf(data.getDtDatePlan()));
                item.put(dataRealisasi.Property_intNumberRealisasi, String.valueOf(data.getIntNumberRealisasi()));
                item.put(dataRealisasi.Property_txtAcc, String.valueOf(data.getTxtAcc()));
                item.put(dataRealisasi.Property_txtLong, String.valueOf(data.getTxtLong()));
                item.put(dataRealisasi.Property_txtLat, String.valueOf(data.getTxtLat()));
                item.put(dataRealisasi.Property_txtImgName1, String.valueOf(data.getTxtImgName1()));
                item.put(dataRealisasi.Property_txtImgName2, String.valueOf(data.getTxtImgName2()));
                item.put(dataRealisasi.Property_intStatusRealisasi, String.valueOf(data.getIntStatusRealisasi()));
                item.put(dataRealisasi.Property_blobImg1, String.valueOf(data.getBlobImg1()));
                item.put(dataRealisasi.Property_blobImg2, String.valueOf(data.getBlobImg2()));
                itemLIstQuery.add(item);
            }
            resJson.put(dataRealisasi.Property_ListOfDatatRealisasiVisitPlan, new JSONArray(itemLIstQuery));
        }
        if (this.getListDataOftAkuisisiHeader()!=null){
            tAkuisisiHeader dataAkuisisiHeader = new tAkuisisiHeader();
            itemLIstQuery  = new ArrayList<>();
            for (tAkuisisiHeader data : this.getListDataOftAkuisisiHeader()){
                JSONObject item = new JSONObject();
                item.put(dataAkuisisiHeader.Property_intSubSubActivityId, String.valueOf(data.getIntSubSubActivityId()));
                item.put(dataAkuisisiHeader.Property_txtHeaderId, String.valueOf(data.getTxtHeaderId()));
                item.put(dataAkuisisiHeader.Property_intSubSubActivityTypeId, String.valueOf(data.getIntSubSubActivityTypeId()));
                item.put(dataAkuisisiHeader.Property_txtNoDoc, String.valueOf(data.getTxtNoDoc()));
                item.put(dataAkuisisiHeader.Property_dtExpiredDate, String.valueOf(data.getDtExpiredDate()));
                item.put(dataAkuisisiHeader.Property_intUserId, String.valueOf(data.getIntUserId()));
                item.put(dataAkuisisiHeader.Property_intRoleId, String.valueOf(data.getIntRoleId()));
                item.put(dataAkuisisiHeader.Property_intDokterId, String.valueOf(data.getIntDokterId()));
                item.put(dataAkuisisiHeader.Property_intApotekID, String.valueOf(data.getIntApotekID()));
                item.put(dataAkuisisiHeader.Property_txtRealisasiVisitId, String.valueOf(data.getTxtRealisasiVisitId()));
                item.put(dataAkuisisiHeader.Property_intAreaId, String.valueOf(data.getIntAreaId()));
                item.put(dataAkuisisiHeader.Property_txtUserName, String.valueOf(data.getTxtUserName()));
                itemLIstQuery.add(item);
            }
            resJson.put(dataAkuisisiHeader.Property_ListDataOftAkuisisiHeader, new JSONArray(itemLIstQuery));
        }

        if (this.getListDataOftAkuisisiDetail()!=null){
            tAkuisisiDetail akuisisiDetailData = new tAkuisisiDetail();
            itemLIstQuery = new ArrayList<JSONObject>();
            for (tAkuisisiDetail data : this.getListDataOftAkuisisiDetail()){
                JSONObject item = new JSONObject();
                item.put(akuisisiDetailData.Property_intHeaderId, String.valueOf(data.getTxtHeaderId()));
                item.put(akuisisiDetailData.Property_txtDetailId, String.valueOf(data.getTxtDetailId()));
                item.put(akuisisiDetailData.Property_txtImgName, String.valueOf(data.getTxtImgName()));
                item.put(akuisisiDetailData.Property_txtImg, String.valueOf(data.getTxtImg()));
                itemLIstQuery.add(item);
            }
            resJson.put(akuisisiDetailData.Property_ListDataOftAkuisisiDetail, new JSONArray(itemLIstQuery));
        }

        if (this.getListOfDatatMaintenanceHeader()!=null){
            tMaintenanceHeader dataMaintenance = new tMaintenanceHeader();
            itemLIstQuery = new ArrayList<>();
            for (tMaintenanceHeader data : this.getListOfDatatMaintenanceHeader()){
                JSONObject item = new JSONObject();
                item.put(dataMaintenance.Property_txtHeaderId, data.getTxtHeaderId());
                item.put(dataMaintenance.Property_txtRealisasiVisitId, data.getTxtRealisasiVisitId());
                item.put(dataMaintenance.Property_intActivityId, data.getIntActivityId());
                item.put(dataMaintenance.Property_intUserId, data.getIntUserId());
                item.put(dataMaintenance.Property_intRoleId, data.getIntRoleId());
                item.put(dataMaintenance.Property_intDokterId, data.getIntDokterId());
                item.put(dataMaintenance.Property_intApotekID, data.getIntApotekID());
                item.put(dataMaintenance.Property_intAreaId, data.getIntAreaId());
                item.put(dataMaintenance.Property_intFlagPush, data.getIntFlagPush());
                itemLIstQuery.add(item);
            }
            resJson.put(dataMaintenance.Property_ListOfDatatMaintenanceHeader, new JSONArray(itemLIstQuery));
        }

        if (this.getListOfDatatMaintenanceDetail()!=null){
            tMaintenanceDetail dataMDetail = new tMaintenanceDetail();
            itemLIstQuery = new ArrayList<>();
            for (tMaintenanceDetail data : this.getListOfDatatMaintenanceDetail()){
                JSONObject item = new JSONObject();
                item.put(dataMDetail.Property_txtDetailId, data.getTxtDetailId());
                item.put(dataMDetail.Property_txtHeaderId, data.getTxtHeaderId());
                item.put(dataMDetail.Property_intSubDetailActivityId, data.getIntSubDetailActivityId());
                item.put(dataMDetail.Property_txtNoDoc, data.getTxtNoDoc());
                item.put(dataMDetail.Property_intFlagPush, data.getIntFlagPush());
                itemLIstQuery.add(item);
            }
            resJson.put(dataMDetail.Property_ListOfDataTMaintenanceDetail, new JSONArray(itemLIstQuery));
        }

        if (this.getListOfDatatInfoProogramHeader()!=null){
            tInfoProgramHeader dataInfoHeader = new tInfoProgramHeader();
            itemLIstQuery = new ArrayList<>();
            for (tInfoProgramHeader data : this.getListOfDatatInfoProogramHeader()){
                JSONObject item = new JSONObject();
                item.put(dataInfoHeader.Property_txtHeaderId, data.getTxtHeaderId());
                item.put(dataInfoHeader.Property_txtRealisasiVisitId, data.getTxtRealisasiVisitId());
                item.put(dataInfoHeader.Property_intActivityId, data.getIntActivityId());
                item.put(dataInfoHeader.Property_intUserId, data.getIntUserId());
                item.put(dataInfoHeader.Property_intRoleId, data.getIntRoleId());
                item.put(dataInfoHeader.Property_intDokterId, data.getIntDokterId());
                item.put(dataInfoHeader.Property_intApotekId, data.getIntApotekId());
                item.put(dataInfoHeader.Property_intAreaId, data.getIntAreaId());
                item.put(dataInfoHeader.Property_intFlagPush, data.getIntFlagPush());
                itemLIstQuery.add(item);
            }
            resJson.put(dataInfoHeader.Property_ListOfDatatInforProgramHeader, new JSONArray(itemLIstQuery));
        }

        if (this.getListOfDatatInfoProgramDetail()!=null){
            tInfoProgramDetail dataInfoDeatil = new tInfoProgramDetail();
            itemLIstQuery = new ArrayList<>();
            for (tInfoProgramDetail data : this.getListOfDatatInfoProgramDetail()){
                JSONObject item = new JSONObject();
                item.put(dataInfoDeatil.Property_txtDetailId, data.getTxtDetailId());
                item.put(dataInfoDeatil.Property_txtHeaderId, data.getTxtHeaderId());
                item.put(dataInfoDeatil.Property_intSubDetailActivityId, data.getIntSubDetailActivityId());
//                item.put(dataInfoDeatil.Property_txtFileName, data.getTxtFileName());
//                item.put(dataInfoDeatil.Property_blobFile, data.getBlobFile());
                item.put(dataInfoDeatil.Property_intFlagChecklist, data.isBoolFlagChecklist());
                item.put(dataInfoDeatil.Property_dtChecklist, data.getDtChecklist());
                item.put(dataInfoDeatil.Property_intFileAttachmentId, String.valueOf(data.getIntFileAttachmentId()));
                item.put(dataInfoDeatil.Property_intFlagPush, String.valueOf(data.getIntFlagPush()));
//                item.put(dataInfoDeatil.Property_Description, data.getDescription());
                itemLIstQuery.add(item);
            }
            resJson.put(dataInfoDeatil.Property_ListOfDatatInfoProgramDetail, new JSONArray(itemLIstQuery));
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
