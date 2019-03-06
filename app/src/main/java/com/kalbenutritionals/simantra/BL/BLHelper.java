package com.kalbenutritionals.simantra.BL;

import android.content.Context;

import com.kalbenutritionals.simantra.Database.Common.ClsDataError;
import com.kalbenutritionals.simantra.Database.Common.ClsDataJson;
import com.kalbenutritionals.simantra.Database.Common.ClsPushData;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClstLogError;
import com.kalbenutritionals.simantra.Database.EnumCounterData;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepotLogError;
import com.kalbenutritionals.simantra.Database.Repo.RepomCounterData;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class BLHelper {

    String access_token,clientId = "";
    List<ClsToken> dataToken;

    public ClsPushData pushData(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataJson dtPush = new ClsDataJson();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        List<Boolean> isDataNull = new ArrayList<>();
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setDtLogin(dataLogin.getDtLogIn());
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }



//            List<ClstLogError>

            FileUpload = new HashMap<>();
            dtPush.setFromUnplan(false);

//            if ()
        }else {
            dtPush = null;
        }
        dtclsPushData.setDataJson(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public ClsPushData pushDataError(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataError dtPush = new ClsDataError();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RepotLogError _repotLogError = new RepotLogError(context);
            List<ClstLogError> ListOfDataError = _repotLogError.getAllPushData();

            FileUpload = new HashMap<>();
            if (ListOfDataError!=null){
                dtPush.setListOfDatatLogError(ListOfDataError);
                for (ClstLogError data : ListOfDataError){
                    if (data.getBlobImg()!=null){
                        FileName.add(data.getTxtGuiId());
                        FileUpload.put(data.getTxtGuiId(), data.getBlobImg());
                    }
                }
            }

        }else {
            dtPush = null;
        }
        dtclsPushData.setDataError(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }





}


