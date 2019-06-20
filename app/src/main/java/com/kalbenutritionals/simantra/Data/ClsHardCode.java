package com.kalbenutritionals.simantra.Data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;


import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created Dewi on 11/7/2017.
 * 
 */

public class ClsHardCode {
    Context context;
    //    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"data"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"app_database"+File.separator;
    /* path root */
    public String txtPathApp = "data" + File.separator + "data" + File.separator + "shp.simantra" + File.separator + "databases" + File.separator;
    public String txtPathTemp = Environment.getExternalStorageDirectory()+ File.separator;

//        public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"image_Person"+File.separator;
//    public String pathApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ File.separator+"Android"+File.separator+"data"+File.separator+"simantra"+File.separator;
    public String pathApp = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"simantra"+File.separator+"image"+File.separator;
    public String txtPathUserData = pathApp + "user_data"+File.separator;
    public String txtPathTempData = pathApp + "tempdata" + File.separator;
    public String txtApkName = "Simantra.apk";

    //    public String dbName = "KalbeCallPlanAEDP.db";
    public String dbName = "SIMANTRA.db";
    //    public String dbName = "DP.db";
    public String txtFolderCheckIn = txtPathUserData + "Absen" + File.separator;
    public String txtFolderData = txtPathUserData + "image_person" + File.separator;
    public String txtFolderDataQuest = txtPathUserData + "image_question" + File.separator;
    public String txtFolderDownload = txtPathTempData + "Download" + File.separator;
    public String txtFolderTemp = txtPathTempData + "Acra" + File.separator;
    public String linkLogin = new RepomConfig(context).API + "loginMobileApps";
    public String linkChangeProfil = new RepomConfig(context).API + "uploadFotoProfil";
    public String linkLogout = new RepomConfig(context).API + "logoutMobileApps";
    public String linkToken = new RepomConfig(context).APIToken + "token";
    public  String LinkMobileVersion = new RepomConfig(context).API + "getLatestAndroidVersion";
    public  String LinkUserRole = new RepomConfig(context).API + "getListRoleByUsername";
    public String linkPushData = new RepomConfig(context).API +"PushAllData";
    public String linkPushDataError = new RepomConfig(context).API +"PushLogError";
    public String linkChangePassword = new RepomConfig(context).API +"gantiPassword";
    public String linkGetDataMasterNew = new RepomConfig(context).API +"getDataMasterNew";
    public String linkGetUpdateToken = new RepomConfig(context).API +"updateUserToken";
    public String linkGetListFormByOrg = new RepomConfig(context).API +"getListFormByOrg";
    public String linkSetTransactionList = new RepomConfig(context).API +"setTransaksiMobile";
    public String linkDownloadFilePDF = new RepomConfig(context).API + "filePDF?txtSPMNo=";

    public static String txtMessage = "message";
    public static String txtBundleKeyBarcode = "BarcodeScanner";
    public static String txtBundleKeyBarcodeLoad = "BarcodeScannerLoad";
    public static String txtNoSPM = "NoSPM";
    public static String txtNoSPMActive = "NoSPMActive";
    public static String txtStatusLoading = "statusLoading";
    public static String StartTime = "StartTime";
    public static String EndTime = "EndTime";

    public static int JenisPertanyaanTextView = 7;
    public static int JenisPertanyaanTextBox = 1;
    public static int JenisPertanyaanCheckBox = 3;
    public static int JenisPertanyaanRadioButton = 4;
    public static int JenisPertanyaanSpinner = 5;

    public static int OPTIONAL = 2;
    public static int MANDATORY = 3;

    public static int BASIC = 1;
    public static int HEADER = 2;
    public static int BODY = 3;
    public static int FOOTER = 4;
    public static String nameApp = "SIMANTRAMOBILE";

    public static int INT_CHECKER = 1;
    public static int INT_VALIDATOR = 2;
    public static String TXT_STATUS_MENU = "STATUS_MENU";

    public static String TXT_DEFAULT = "000";
    public static String TXT_CREATION_DATE = "CREATION_DATE";
    public static String TXT_PLAN_DELIVERY_DATE = "PLAN_DELIVERY_DATE";
    public static String TXT_SPM_NO = "SPM_NO";
    public static String TXT_EXPEDITION_NAME = "EXPEDITION_NAME";
    public static String TXT_FIND_DETAIL_HCD = "FIND_DETAIL_HCD";
    public static String TXT_VEHICLE_TYPE = "VEHICLE_TYPE";
    public static String TXT_ITEM_TYPE = "ITEM_TYPE";
    public static String TXT_FIND_DETAIL = "FIND_DETAIL";

    public static String TXT_SHIP_FROM = "SHIP_FROM";
    public static String TXT_SHIP_TO = "SHIP_TO";

    public static String TXT_SHARED_PREF_KEY = "SimantraPref";

    public String copydb(Context context) throws IOException {
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ new ClsHardCode().dbName;

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"SIMANTRA";
            File yourFile = new File(txtPathUserData);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            String s= "hahaha";
        }

        return "cute";
    }

    public JSONObject pDeviceInfo(){
        String api =  android.os.Build.VERSION.SDK;      // API Level
        String device = android.os.Build.DEVICE;           // Device
        String model = android.os.Build.MODEL;            // Model
        String product = android.os.Build.PRODUCT;
        String osVersion = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            osVersion = Build.VERSION.BASE_OS;
        }

        JSONObject jDevInfo = new JSONObject();
        try {
            jDevInfo.put("os_version", osVersion);
            jDevInfo.put("version_sdk", api);
            jDevInfo.put("device", device);
            jDevInfo.put("model", model);
            jDevInfo.put("product", product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jDevInfo;
    }
}
