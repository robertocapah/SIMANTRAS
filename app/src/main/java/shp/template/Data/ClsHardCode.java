package shp.template.Data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.res.TypedArrayUtils;


import shp.template.Database.Repo.RepomConfig;
import shp.template.R;

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
    public String txtPathApp = "data" + File.separator + "data" + File.separator + "shp.template" + File.separator + "databases" + File.separator;
    public String txtPathTemp = Environment.getExternalStorageDirectory()+ File.separator;

    //    public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"image_Person"+File.separator;
    public String pathApp = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"shp.template"+File.separator;
    public String txtPathUserData = pathApp + "user_data"+File.separator;
    public String txtPathTempData = pathApp + "tempdata" + File.separator;
    public String txtApkName = "KN2019_Template_Mobile.apk";

    //    public String dbName = "KalbeCallPlanAEDP.db";
    public String dbName = "AEDP.db";
    //    public String dbName = "DP.db";
    public String txtFolderCheckIn = txtPathUserData + "Absen" + File.separator;
    public String txtFolderAkuisisi = txtPathUserData + "Akuisisi" + File.separator;
    public String txtFolderData = txtPathUserData + "Image_Person" + File.separator;
    public String txtFolderDownload = txtPathTempData + "Download" + File.separator;
    public String txtFolderTemp = txtPathTempData + "Acra" + File.separator;
    public String linkMaster = new RepomConfig(context).API + "mProduct";
    public String linkLogin = new RepomConfig(context).API + "loginMobileApps";
    public String linkChangeProfil = new RepomConfig(context).API + "uploadFotoProfil";
    public String linkLogout = new RepomConfig(context).API + "logoutMobileApps";
    public String linkToken = new RepomConfig(context).APIToken + "token";
    //    public String LinkUser = new RepomConfig(context).API + "loginMobileApps";
    public  String LinkMobileVersion = new RepomConfig(context).API + "getLatestAndroidVersion";
    public  String LinkUserRole = new RepomConfig(context).API + "getListRoleByUsername";
    public String linkDownloadAll = new RepomConfig(context).API + "downloadAllData";
    public String linkmActivity = new RepomConfig(context).API + "downloadmActivity";
    public String linkSubActivity = new RepomConfig(context).API + "downloadSubActivity";
    public String linkSubSubActivity = new RepomConfig(context).API + "downloadSubActivityDetail";
    public String linkDownloadArea = new RepomConfig(context).API + "downloadtMappingArea";
    public String linkProgramVisit = new RepomConfig(context).API + "downloadtProgramVisit";
    public String linkAkuisisi = new RepomConfig(context).API + "downloadtAkuisisi";
    public String linkMaintenance = new RepomConfig(context).API + "downloadtMaintenance";
    public String linkInfoProgram = new RepomConfig(context).API + "downloadtInfoProgram";
    public String linkCreateUnplan = new RepomConfig(context).API + "CreateUnplan";
    //    public String linkRealisasiVisit = new RepomConfig(context).API + "downloadTRealisasi";
    public String linkPushData = new RepomConfig(context).API +"PushAllData";
    public String linkPushDataError = new RepomConfig(context).API +"PushLogError";
    public String linkChangePassword = new RepomConfig(context).API +"gantiPassword";
    public String linkDokterAedp = new RepomConfig(context).API +"Dokter";
    public String linkApotekAedp = new RepomConfig(context).API +"Apotek";

    /*Link klik apotek*/
    public String linkApotek = new RepomConfig(context).APIKLB +"mae/apotek";
    public String linkDokter = new RepomConfig(context).APIKLB + "mae/dokter";

    public int Draft = 0;
    public int Save = 1;
    public int Sync = 2;

    public int TypeFoto = 1;
    public int TypeText = 2;

    public int VisitDokter = 1;
    public int VisitApotek = 2;

    public int VisitPlan = 0;
    public int Realisasi = 1;

    public int Plan = 1;
    public int UnPlan = 2;

    //yang di tampilin di info program
    public int AllInfo = 1;
    public int OnlyDesc = 2;
    public int OnlyFile = 3;


    public int int_akuisisi_dokter = 1;
    public int int_maintenance_dokter = 2;
    public int int_infoprogram_dokter = 3;
    public int int_akuisisi_apotek = 4;
    public int int_maintenance_apotek = 5;
    public int int_infoprogram_apotek = 6;

    //untuk download file
    public String AKUISISI = "akuisisi";
    public String INFO_PROGRAM = "info program";
    public String REALISASI_SATU = "realisasi pertama";
    public String REALISASI_DUA = "realisasi kedua";
    public String LOGIN =   "login";

    public String copydb(Context context) throws IOException {
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ new ClsHardCode().dbName;

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"backupDbKalbeCallPlanAEDP";
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
