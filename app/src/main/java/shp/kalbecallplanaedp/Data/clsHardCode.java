package shp.kalbecallplanaedp.Data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;


import shp.kalbecallplanaedp.Repo.mConfigRepo;

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

public class clsHardCode {
    Context context;
    //    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"data"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"app_database"+File.separator;
    /* path root */
    public String txtPathApp = "data" + File.separator + "data" + File.separator + "shpnutritionals.kalbecallplanaedp" + File.separator + "databases" + File.separator;

    //    public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"image_Person"+File.separator;
    public String pathApp = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"shpnutritionals.kalbecallplanaedp"+File.separator;
    public String txtPathUserData = pathApp + "user_data"+File.separator;
    public String txtPathTempData = pathApp + "tempdata" + File.separator;

    //    public String dbName = "KalbeCallPlanAEDP.db";
    public String dbName = "AEDP.db";
    //    public String dbName = "DP.db";
    public String txtFolderCheckIn = txtPathUserData + "Absen" + File.separator;
    public String txtFolderAkuisisi = txtPathUserData + "Akuisisi" + File.separator;
    public String txtFolderData = txtPathUserData + "Image_Person" + File.separator;
    public String txtFolderDownload = txtPathTempData + "Download" + File.separator;
    public String txtFolderTemp = txtPathTempData + "Acra" + File.separator;
    public String linkMaster = new mConfigRepo(context).API + "mProduct";
    public String linkLogin = new mConfigRepo(context).API + "loginMobileApps";
    public String linkChangeProfil = new mConfigRepo(context).API + "uploadFotoProfil";
    public String linkLogout = new mConfigRepo(context).API + "logoutMobileApps";
    public String linkToken = new mConfigRepo(context).APIToken + "token";
    //    public String LinkUser = new mConfigRepo(context).API + "loginMobileApps";
    public  String LinkMobileVersion = new mConfigRepo(context).API + "getLatestAndroidVersion";
    public  String LinkUserRole = new mConfigRepo(context).API + "getListRoleByUsername";
    public String linkDownloadAll = new mConfigRepo(context).API + "downloadAllData";
    public String linkmActivity = new  mConfigRepo(context).API + "downloadmActivity";
    public String linkSubActivity = new  mConfigRepo(context).API + "downloadSubActivity";
    public String linkSubSubActivity = new  mConfigRepo(context).API + "downloadSubActivityDetail";
    public String linkDownloadArea = new mConfigRepo(context).API + "downloadtMappingArea";
    public String linkProgramVisit = new mConfigRepo(context).API + "downloadtProgramVisit";
    public String linkAkuisisi = new mConfigRepo(context).API + "downloadtAkuisisi";
    public String linkMaintenance = new mConfigRepo(context).API + "downloadtMaintenance";
    public String linkInfoProgram = new mConfigRepo(context).API + "downloadtInfoProgram";
    public String linkCreateUnplan = new mConfigRepo(context).API + "CreateUnplan";
    //    public String linkRealisasiVisit = new mConfigRepo(context).API + "downloadTRealisasi";
    public String linkPushData = new mConfigRepo(context).API +"PushAllData";
    public String linkPushDataError = new mConfigRepo(context).API +"PushLogError";
    public String linkChangePassword = new mConfigRepo(context).API +"gantiPassword";
    public String linkDokterAedp = new mConfigRepo(context).API +"Dokter";
    public String linkApotekAedp = new mConfigRepo(context).API +"Apotek";

    /*Link klik apotek*/
    public String linkApotek = new mConfigRepo(context).APIKLB +"mae/apotek";
    public String linkDokter = new mConfigRepo(context).APIKLB + "mae/dokter";


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
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ new clsHardCode().dbName;

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
