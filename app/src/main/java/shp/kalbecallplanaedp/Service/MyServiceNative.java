package shp.kalbecallplanaedp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shp.kalbecallplanaedp.BL.clsHelperBL;
import shp.kalbecallplanaedp.Common.clsPushData;
import shp.kalbecallplanaedp.Common.mConfigData;
import shp.kalbecallplanaedp.Common.mCounterData;
import shp.kalbecallplanaedp.Data.VolleyResponseListener;
import shp.kalbecallplanaedp.Data.VolleyUtils;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Data.enumCounterData;
import shp.kalbecallplanaedp.Repo.mConfigRepo;
import shp.kalbecallplanaedp.Repo.mCounterDataRepo;
import shp.kalbecallplanaedp.Repo.mUserLoginRepo;
import shp.kalbecallplanaedp.ResponseDataJson.responsePushData.ResponsePushData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class MyServiceNative extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("Not Implement yet");
    }

    @Override
    public void onCreate() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
//        super.onCreate();
    }

    private static long UPDATE_INTERVAL = 6*60*1000;  //default
    private static long UPDATE_INTERVAL_TESTING = 1*30*1000;//3000;  //default
    private static Timer timer = new Timer();
    private Gson gson;
    private void _startService(){
        long intInterval = 0;
        intInterval = UPDATE_INTERVAL_TESTING;
        if (timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    _doServiceWork();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 3000, intInterval);
    }

    private void _doServiceWork() throws JSONException{
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        final clsPushData dtJson = new clsHelperBL().pushData(versionName, getApplicationContext());
        if (dtJson == null){
            _shutdownService();
        }else {
            String linkPushData = new clsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushDataBackground(getApplicationContext(), linkPushData, dtJson, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response!=null){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            ResponsePushData model = gson.fromJson(jsonObject.toString(), ResponsePushData.class);
                            boolean isStatus = model.getResult().isStatus();
                            String txtMessage = model.getResult().getMessage();
                            String txtMethod = model.getResult().getMethodName();
                            if (isStatus==true){
                                if (model.getData().getModelData()!=null){
                                    if (model.getData().getModelData().size()>0){
                                        new  clsHelperBL().SavePushData(getApplicationContext(), dtJson.getDataJson(), model);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        mUserLoginRepo loginRepo = new mUserLoginRepo(getApplicationContext());
        if (loginRepo.getContactCount(getApplicationContext())>0){
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                mCounterData _mCounterData = new mCounterData();
                _mCounterData.setIntId(enumCounterData.MonitorScedule.getIdCounterData());
                _mCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _mCounterData.setTxtName("Monitor Service");
                _mCounterData.setTxtValue(dateFormat.format(cal.getTime()));
                new mCounterDataRepo(getApplicationContext()).createOrUpdate(_mCounterData);

                //new clsInit().PushData(db,versionName);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }else {
            _shutdownService();
        }
    }
    private void _shutdownService(){
        if (timer!=null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        _startService();
    }

    @Override
    public void onDestroy() {
        _shutdownService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _startService();
        return START_STICKY;
    }
}
