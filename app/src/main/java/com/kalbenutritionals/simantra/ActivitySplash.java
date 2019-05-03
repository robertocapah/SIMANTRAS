package com.kalbenutritionals.simantra;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import com.androidnetworking.error.ANError;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.kalbenutritionals.simantra.BL.BLMain;
import com.kalbenutritionals.simantra.Database.Common.ClsStatusMenuStart;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmConfigData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;
import com.kalbenutritionals.simantra.Database.Repo.EnumStatusMenuStart;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworkingDownloadFile;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbenutritionals.simantra.Service.ServiceNative;
import com.kalbenutritionals.simantra.CustomView.Utils.AuthenticatorUtil;

import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
    private static final String TAG = "Activity Spash Network";
    RepoclsToken tokenRepo;
    String clientId = "";
    ProgressDialog mProgressDialog;
    Dialog dialog;
    private Gson gson;
    private String i_View = "Fragment";
    private final String TAG_DOWNlOAD_APK = "Download_apk";

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
                    || hasReadPhoneState != PackageManager.PERMISSION_GRANTED) {
                boolean checkPermission = checkPermission();
            } else {
                StartAnimations();
                checkStatusMenu();
            }
        } else {
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
                        && !ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        && !ActivityCompat.shouldShowRequestPermissionRationale(ActivitySplash.this,
                        Manifest.permission.CAMERA)
                        ) {
                    ActivityCompat.requestPermissions(ActivitySplash.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    dialog.dismiss();

                } else {
                    ActivityCompat.requestPermissions(ActivitySplash.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
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
        iv2.setBackgroundResource(R.drawable.ic_simantra);
        iv2.clearAnimation();
        iv2.startAnimation(anim);
    }

    Intent myIntent = null;
    ClsStatusMenuStart _clsStatusMenuStart = null;


    private void checkStatusMenu() {
        try {
            new RepomConfig(getApplicationContext()).InsertDefaultmConfig();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            new ClsHardCode().copydb(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            _clsStatusMenuStart = new BLMain().checkUserActive(this);
            if (_clsStatusMenuStart.get_intStatus() != null) {
                if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.FormLogin.FormLogin) {
                    ClsmUserLogin dtLogin = new RepomUserLogin(getApplicationContext()).getUserLogin(getApplicationContext());
                    if (dtLogin != null) {
                        logout(ActivitySplash.this);
                    } else {
                        try {
                            tokenRepo = new RepoclsToken(getApplicationContext());
                            dataToken = (List<ClsToken>) tokenRepo.findAll();
                            if (dataToken.size() == 0) {
                                requestToken(this);
                            } else {
                                checkVersion(this, mAccountManager);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.UserActiveLogin) {
                    if (new AuthenticatorUtil().countingAccount(mAccountManager).length == 0) {
//                        myIntent = new Intent(getApplicationContext(), MainMenu.class);
//                        finish();
//                        startActivity(myIntent);
                        myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
                        myIntent.putExtra(i_View, "FragmentPushData");
                        finish();
                        startActivity(myIntent);
                    } else {
                        myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
                        finish();
                        startActivity(myIntent);
                    }
                } else if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.PushDataMobile) {
                    myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
                    myIntent.putExtra(i_View, "FragmentPushData");
                    finish();
                    startActivity(myIntent);
                }
            } else {
                try {
                    tokenRepo = new RepoclsToken(getApplicationContext());
                    dataToken = (List<ClsToken>) tokenRepo.findAll();
                    if (dataToken.size() == 0) {
                        requestToken(this);
                    } else {
                        checkVersion(this, mAccountManager);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void requestToken(final Context activity) {

        new FastNetworkingUtils().FNRequestToken(activity, "Request Token, Please Wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String accessToken = "";
                    String refreshToken = "";
                    accessToken = response.getString("access_token");
                    refreshToken = response.getString("refresh_token");
                    String dtIssued = response.getString(".issued");

                    ClsToken data = new ClsToken();
                    data.setIntId("1");
                    data.setDtIssuedToken(dtIssued);
                    data.setTxtUserToken(accessToken);
                    data.setTxtRefreshToken(refreshToken);
//                    RepoclsToken tokenRepo = new RepoclsToken(getApplicationContext());
                    new RepoclsToken(activity.getApplicationContext()).createOrUpdate(data);
                    checkVersion(activity, mAccountManager);
                    Log.d("Data info", "get access_token & refresh_token, Success");
                } catch (Exception ex) {
                    String a = "";
                }


            }

            @Override
            public void onError(ANError error) {
                int a = 0;
            }
        });
    }

    String txtVersionName = null;

    public void checkVersion(final Context context, final AccountManager accountManager) {

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
            jData.put("version_name", txtVersionName);
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
        new FastNetworkingUtils().FNRequestPostData(ActivitySplash.this, strLinkAPI, resJson, "Checking Version", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONObject jsn = response.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");

                        if (txtStatus == true) {
                            Boolean resUpdate = false;
                            String txtLink = "";
                            JSONObject objData = response.getJSONObject("data");
                            if (txtVersionName.equals(objData.getString("version_name"))) {
                                resUpdate = false;
                            } else {
                                resUpdate = true;
                                txtLink = objData.getString("link_app");
                            }
                            if (resUpdate) {
                                // execute this when the downloader must be fired
                                getDownloadAPK(txtLink);
                            } else {
                                new AuthenticatorUtil().showAccountPicker((Activity) context, accountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
                            }
                        } else {
                            Boolean resUpdate = false;
                            String txtLink = "";
                            JSONObject objData = response.getJSONObject("data");
                            if (txtVersionName.equals(objData.getString("version_name"))) {
                                resUpdate = false;
                            } else {
                                resUpdate = true;
                                txtLink = objData.getString("link_app");
                            }
                            resUpdate = false; //testing
                            if (resUpdate) {

                                DonutProgress donutProgress = new DonutProgress(context);
                                donutProgress.setFinishedStrokeColor(R.color.green_500);
                                donutProgress.setText("Downloading New App");
                                donutProgress.setUnfinishedStrokeColor(R.color.red_800);

                                // execute this when the downloader must be fired
                                getDownloadAPK(txtLink);
                            } else {
                                new AuthenticatorUtil().showAccountPicker((Activity) context, accountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;

            }
        });
    }

    private void getDownloadAPK(String txtLink) {
        final String txtPathUserData = Environment.getExternalStorageDirectory() + File.separator;
        final String apkName = new ClsHardCode().txtApkName;
        dialog = new Dialog(ActivitySplash.this);
        dialog.setContentView(R.layout.layout_progress_download_apk);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);

        try {
            File yourFile = new File(txtPathUserData);
            if (!yourFile.exists()) {
                if (yourFile.mkdirs()) {
                    Log.d(TAG, "Successfully created the parent dir:" + yourFile.getName());
                } else {
                    Log.d(TAG, "Failed to create the parent dir:" + yourFile.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FastNetworkingUtils().FNRequestDownloadAPKFile(this, txtLink, txtPathUserData, apkName, TAG_DOWNlOAD_APK, dialog, new InterfaceFastNetworkingDownloadFile() {
            @Override
            public void onProgress(final long bytesDownloaded, final long totalBytes) {
                // do anything with progress
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        double precentage = ((double) bytesDownloaded / (double) totalBytes) * 100;
                        progressD.setProgress((int) precentage);
//                                tvProgress.setText("bytes downloaded :" + bytesDownloaded + ". total bytes : " + totalBytes + " precentage : " + precentage + " %");
                    }
                });
            }

            @Override
            public void onDownloadComplete() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Download Complete", Toast.LENGTH_SHORT).show();
                        String txtPath = txtPathUserData + apkName;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Toast.makeText(getApplicationContext(), "File downloaded", Toast.LENGTH_LONG).show();
                            try {
                                Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                File file = new File(txtPath);
                                Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                                intent.setData(uri);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "File downloaded", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            if (Build.VERSION.SDK_INT >= 24) {
                                intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(txtPath)), "application/vnd.android.package-archive");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            } else {
                                intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                            }
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onError(ANError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void logout(final Activity activity) {
        String strLinkAPI = new ClsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        ClsmUserLogin dtLogin = null;
        try {
            dtLogin = new RepomUserLogin(activity.getApplicationContext()).getUserLogin(activity.getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID());
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

        new FastNetworkingUtils().FNRequestPostData(activity, strLinkAPI, resJson, "Checking Version", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gson = gsonBuilder.create();
                        LoginMobileApps model = gson.fromJson(response.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        mAccountManager = AccountManager.get(activity);
                        if (txtStatus == true) {

                            Intent intent = new Intent(activity, ServiceNative.class);
                            activity.stopService(intent);
                            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
//                            new BLMain().deleteMediaStorage();
                            helper.clearDataAfterLogout();
                            checkVersion(activity, mAccountManager);

                        } else {
                            activity.stopService(new Intent(activity, ServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                            helper.clearDataAfterLogout();
                            checkVersion(activity, mAccountManager);
                            new ToastCustom().showToasty(ActivitySplash.this, txtMessage, 4);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(ANError error) {

            }
        });

    }
}
