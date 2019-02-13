package shp.template;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import shp.template.BL.BLHelper;
import shp.template.BL.BLMain;
import shp.template.Database.Common.ClsStatusMenuStart;
import shp.template.Database.Common.ClsToken;
import shp.template.Database.Common.ClsmConfigData;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Data.ClsHardCode;
import shp.template.Database.DatabaseHelper;
import shp.template.Database.DatabaseManager;
import shp.template.Network.Volley.InterfaceVolleyResponseListener;
import shp.template.Network.Volley.VolleyUtils;
import shp.template.Database.Repo.RepoclsToken;
import shp.template.Database.Repo.RepomConfig;
import shp.template.Database.Repo.RepomUserLogin;
import shp.template.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import shp.template.Service.ServiceNative;
import shp.template.CustomView.Utils.AuthenticatorUtil;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

public class ActivitySplash extends AppCompatActivity {
    long delay = 5000;
    private TextView version;
    boolean firstStart;
    private static final int REQUEST_READ_PHONE_STATE = 0;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    PackageInfo pInfo = null;
    private AccountManager mAccountManager;
    List<ClsToken> dataToken;
    RepomUserLogin loginRepo;
    RepoclsToken tokenRepo;
    String clientId = "";
    ProgressDialog mProgressDialog;
    private Gson gson;
    private String i_View ="Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }
        setContentView(R.layout.activity_splash);
        mAccountManager = AccountManager.get(this);
        DatabaseManager.init(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        int a = 5/0;
        version = (TextView) findViewById(R.id.tv_version);
        version.setText(pInfo.versionName.toString());
        version.setGravity(Gravity.CENTER | Gravity.BOTTOM);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int hasWriteExternalStoragePermission = ContextCompat.checkSelfPermission(ActivitySplash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(ActivitySplash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(ActivitySplash.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCameraPermission = ContextCompat.checkSelfPermission(ActivitySplash.this,
                Manifest.permission.CAMERA);
        int hasReadPhoneState = ContextCompat.checkSelfPermission(ActivitySplash.this,
                Manifest.permission.READ_PHONE_STATE);

        if (Build.VERSION.SDK_INT >= 23) {
            if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED
                    || hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED
                    || hasAccessFineLocationPermission != PackageManager.PERMISSION_GRANTED
                    || hasCameraPermission != PackageManager.PERMISSION_GRANTED
                    || hasReadPhoneState != PackageManager.PERMISSION_GRANTED){
                boolean checkPermission = checkPermission();
            }else {
                StartAnimations();
                checkStatusMenu();
            }
        }
        else {
            StartAnimations();
            checkStatusMenu();
        }
    }

    private boolean checkPermission() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySplash.this);
        builder.setMessage("You need to allow access. . .");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && !ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        &&!ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        &&!ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.CAMERA)
                        ){
                    ActivityCompat.requestPermissions(ActivitySplash.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    dialog.dismiss();

                }else{
                    ActivityCompat.requestPermissions(ActivitySplash.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    dialog.dismiss();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        return true;
    }

    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        TextView iv = (TextView) findViewById(R.id.iv_anim);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        anim.reset();
        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
        iv2.setBackgroundResource(R.drawable.ic_kalbe);
        iv2.clearAnimation();
        iv2.startAnimation(anim);
    }
    Intent myIntent = null;
    ClsStatusMenuStart _clsStatusMenuStart = null;


    private void checkStatusMenu() {
        new AuthenticatorUtil().showAccountPicker(ActivitySplash.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
//        try {
//            new RepomConfig(getApplicationContext()).InsertDefaultmConfig();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            new ClsHardCode().copydb(getApplicationContext());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            _clsStatusMenuStart = new BLMain().checkUserActive(getApplicationContext());
//            if (_clsStatusMenuStart.get_intStatus() != null){
//                if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.FormLogin) {
//                    ClsmUserLogin dtLogin = new BLMain().getUserLogin(getApplicationContext());
//                    if (dtLogin!=null){
//                        logout(this);
//                    }else {
//                        try {
//                            tokenRepo = new RepoclsToken(getApplicationContext());
//                            dataToken = (List<ClsToken>) tokenRepo.findAll();
//                            if (dataToken.size() == 0) {
//                                requestToken(this);
//                            }else {
//                                checkVersion(this, mAccountManager);
//                            }
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
////                        new AuthenticatorUtil().showAccountPicker(ActivitySplash.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
//                    }
////                    logout();
////                    if (new AuthenticatorUtil().countingAccount(mAccountManager).length==0){
////                        myIntent = new Intent(getApplicationContext(), ActivityLogin.class);
////                        finish();
////                        startActivity(myIntent);
//////                            logout();
////                    } else {
////                        myIntent = new Intent(getApplicationContext(), Pi.class);
////                        finish();
////                        startActivity(myIntent);
////                    }
//
//                } else if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.UserActiveLogin) {
//                    if (new AuthenticatorUtil().countingAccount(mAccountManager).length==0){
////                        myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
////                        finish();
////                        startActivity(myIntent);
//                        myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
//                        myIntent.putExtra(i_View, "FragmentPushData");
//                        finish();
//                        startActivity(myIntent);
//                    } else {
//                        myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
//                        finish();
//                        startActivity(myIntent);
//                    }
//                }else if (_clsStatusMenuStart.get_intStatus()== EnumStatusMenuStart.PushDataMobile){
//                    myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
//                    myIntent.putExtra(i_View, "FragmentPushData");
//                    finish();
//                    startActivity(myIntent);
//                }
//            } else {
//                try {
//                    tokenRepo = new RepoclsToken(getApplicationContext());
//                    dataToken = (List<ClsToken>) tokenRepo.findAll();
//                    if (dataToken.size() == 0) {
//                        requestToken(this);
//                    }else {
//                        checkVersion(this, mAccountManager);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void requestToken(final Context activity){
        String username = "";
        String strLinkAPI = new ClsHardCode().linkToken;

        RepomConfig configRepo = new RepomConfig(activity.getApplicationContext());
        tokenRepo = new RepoclsToken(activity.getApplicationContext());
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
            ClsmConfigData configDataUser = (ClsmConfigData) configRepo.findById(5);
            username = configDataUser.getTxtDefaultValue().toString();
            clientId = configDataClient.getTxtDefaultValue().toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new VolleyUtils().makeJsonObjectRequestToken(activity, strLinkAPI, username, "", clientId, "Request Token, Please Wait", new InterfaceVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    try {
                        String accessToken = "";
                        String refreshToken = "";
                        JSONObject jsonObject = new JSONObject(response);
                        accessToken = jsonObject.getString("access_token");
                        refreshToken = jsonObject.getString("refresh_token");
                        String dtIssued = jsonObject.getString(".issued");

                        ClsToken data = new ClsToken();
                        data.setIntId("1");
                        data.setDtIssuedToken(dtIssued);
                        data.setTxtUserToken(accessToken);
                        data.setTxtRefreshToken(refreshToken);

                        tokenRepo.createOrUpdate(data);

                        Log.d("Data info", "get access_token & refresh_token, Success");

                        checkVersion(activity, mAccountManager);

//                        Toast.makeText(activity.getApplicationContext(), "Ready For Login", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    String txtVersionName = null;
    public void checkVersion(final Context context, final AccountManager accountManager){

        try {
            txtVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ClsmConfigData dtClsmConfigData = null;
        try {
            dtClsmConfigData = (ClsmConfigData) new RepomConfig(context).findById(7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String strLinkAPI = new ClsHardCode().LinkMobileVersion;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jData.put("version_name",txtVersionName );
            jData.put("application_name", dtClsmConfigData.getTxtValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new RepoclsToken(context);
            dataToken = (List<ClsToken>) tokenRepo.findAll();
            resJson.put("data", jData);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new VolleyUtils().volleyCheckVersion(context, strLinkAPI, mRequestBody, accountManager, "Checking your version......", new InterfaceVolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response!=null){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        jsonObject = new JSONObject(response);
                        JSONObject jsn = jsonObject.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");

                        if (txtStatus == true){
                            Boolean resUpdate = false;
                            String txtLink = "";
                            JSONObject objData = jsonObject.getJSONObject("data");
                            if (txtVersionName.equals(objData.getString("version_name"))) {
                                resUpdate = false;
                            }else {
                                resUpdate = true;
                                txtLink =  objData.getString("link_app");
                            }

                            if (resUpdate){
                                mProgressDialog = new ProgressDialog(context);
                                mProgressDialog.setMessage("Please Wait For Downloading File....");
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                mProgressDialog.setCancelable(false);

                                // execute this when the downloader must be fired
                                final DownloadTask downloadTask = new DownloadTask(context);
                                downloadTask.execute(txtLink);

                                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        downloadTask.cancel(true);
                                    }
                                });
                            }else {
                                new AuthenticatorUtil().showAccountPicker((Activity) context, accountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
                            }
                        } else {
                            Boolean resUpdate = false;
                            String txtLink = "";
                            JSONObject objData = jsonObject.getJSONObject("data");
                            if (txtVersionName.equals(objData.getString("version_name"))) {
                                resUpdate = false;
                            }else {
                                resUpdate = true;
                                txtLink =  objData.getString("link_app");
                            }

                            if (resUpdate){
                                mProgressDialog = new ProgressDialog(context);
                                mProgressDialog.setMessage("Please Wait For Downloading File....");
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                mProgressDialog.setCancelable(false);

                                // execute this when the downloader must be fired
                                final DownloadTask downloadTask = new DownloadTask(context);
                                downloadTask.execute(txtLink);

                                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        downloadTask.cancel(true);
                                    }
                                });
                            }else {
                                new AuthenticatorUtil().showAccountPicker((Activity) context, accountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
                            }
//                            ToastCustom.showToasty(getApplicationContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String txtPath = new ClsHardCode().txtPathUserData;
                File mediaStorageDir = new File(txtPath);
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null;
                    }
                }
                output = new FileOutputStream(txtPath + "kalbecallplanaedp.apk");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
//                new clsMainActivity().showToast(context, "Download error: " + result);
            else {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Toast.makeText(context, "File downloaded", Toast.LENGTH_LONG).show();
//                   new clsMainExlActivity().showToast(context,"File downloaded");
//                  new clsMainActivity().showToast(context, );
                    try {
                        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                        String txtPath = new ClsHardCode().txtPathUserData + "kalbecallplanaedp.apk";
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        File file = new File(txtPath);
                        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.setData(uri);
//                        intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(txtPath)), "application/vnd.android.package-archive");
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "File downloaded", Toast.LENGTH_LONG).show();
//                    new clsMainActivity().showToast(context, "File downloaded");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String txtPath = new ClsHardCode().txtPathUserData + "kalbecallplanaedp.apk";
                    intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (Build.VERSION.SDK_INT >= 24) {
                        intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(txtPath)), "application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                    }
                    //intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");

                    startActivity(intent);
                    finish();
                }
            }
        }
    }

//    private void logout() {
//        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
//        helper.clearDataAfterLogout();
////        new AuthenticatorUtil().addNewAccount(ActivitySplash.this, mAccountManager, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS);
//    }

    public void logout(final Activity activity) {
        String strLinkAPI = new ClsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        ClsmUserLogin dtLogin = new BLMain().getUserLogin(activity.getApplicationContext());
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID() );
            tokenRepo = new RepoclsToken(activity.getApplicationContext());
            dataToken = (List<ClsToken>) tokenRepo.findAll();
            resJson.put("data", dataJson);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new VolleyUtils().volleyLogin(activity, strLinkAPI, mRequestBody, "Please Wait.....", true, new InterfaceVolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(activity.getApplicationContext(),message,4);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gson = gsonBuilder.create();
                        JSONObject jsonObject = new JSONObject(response);
                        LoginMobileApps model = gson.fromJson(jsonObject.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        mAccountManager = AccountManager.get(activity);
                        if (txtStatus == true){

                            Intent intent =new Intent(activity, ServiceNative.class);
                            activity.stopService(intent);
                            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                            new BLMain().deleteMediaStorage();
                            helper.clearDataAfterLogout();
                            checkVersion(activity, mAccountManager);
//                            new AuthenticatorUtil().showAccountPicker(ActivitySplash.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
//                            Log.d("Data info", "logout Success");

                        } else {
                            activity.stopService(new Intent(activity, ServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                            helper.clearDataAfterLogout();
                            checkVersion(activity, mAccountManager);
//                            new AuthenticatorUtil().showAccountPicker(ActivitySplash.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
                            new ToastCustom().showToasty(ActivitySplash.this,txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
