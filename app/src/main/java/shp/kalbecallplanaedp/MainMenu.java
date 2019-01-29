package shp.kalbecallplanaedp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shp.kalbecallplanaedp.BL.clsActivity;
import shp.kalbecallplanaedp.BL.clsHelperBL;
import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.clsPhotoProfile;
import shp.kalbecallplanaedp.Common.clsPushData;
import shp.kalbecallplanaedp.Common.clsStatusMenuStart;
import shp.kalbecallplanaedp.Common.clsToken;
import shp.kalbecallplanaedp.Common.mMenuData;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Common.tNotification;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.VolleyResponseListener;
import shp.kalbecallplanaedp.Data.VolleyUtils;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Fragment.FragementInfoProgram;
import shp.kalbecallplanaedp.Fragment.FragementMaintenance;
import shp.kalbecallplanaedp.Fragment.FragmentAkuisisi;
import shp.kalbecallplanaedp.Fragment.FragmentDownloadData;
import shp.kalbecallplanaedp.Fragment.FragmentHeaderCallPlan;
import shp.kalbecallplanaedp.Fragment.FragmentHistory;
import shp.kalbecallplanaedp.Fragment.FragmentListCallPlan;
import shp.kalbecallplanaedp.Fragment.FragmentNotification;
import shp.kalbecallplanaedp.Fragment.FragmentPushData;
import shp.kalbecallplanaedp.Fragment.FragmentSetting;
import shp.kalbecallplanaedp.Repo.clsPhotoProfilRepo;
import shp.kalbecallplanaedp.Repo.clsTokenRepo;
import shp.kalbecallplanaedp.Repo.enumStatusMenuStart;
import shp.kalbecallplanaedp.Repo.mConfigRepo;
import shp.kalbecallplanaedp.Repo.mMenuRepo;
import shp.kalbecallplanaedp.Repo.mUserLoginRepo;
import shp.kalbecallplanaedp.Repo.tNotificationRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import shp.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import shp.kalbecallplanaedp.ResponseDataJson.responsePushData.ResponsePushData;
import shp.kalbecallplanaedp.Service.MyServiceNative;
import shp.kalbecallplanaedp.Utils.AuthenticatorUtil;
import shp.kalbecallplanaedp.Utils.CircularTextView;
import shp.kalbecallplanaedp.Utils.IOBackPressed;
import shp.kalbecallplanaedp.Utils.ReceiverDownloadManager;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class MainMenu extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    //    DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
    List<mUserLogin> dataLogin = null;
    List<mMenuData> dataMenu = null;
    List<clsPhotoProfile> dataImageProfile = null;
    mUserLoginRepo loginRepo;
    mMenuRepo menuRepo;
    clsPhotoProfilRepo repoUserImageProfile;

    PackageInfo pInfo = null;
    int selectedId;
    Boolean isSubMenu = false;
    String[] listMenu;
    String[] linkMenu;
    private static final int CAMERA_REQUEST_PROFILE = 120;
    private static final String IMAGE_DIRECTORY_NAME = "Image Personal";
    final int SELECT_FILE_PROFILE = 6;
    private static Bitmap photoProfile, mybitmapImageProfile;
    private static byte[] phtProfile;
    final int PIC_CROP_PROFILE = 5;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    MenuItem checkNavItem = null;
    private TextView tvUsername, tvEmail;
    public CircleImageView ivProfile;
    private Uri uriImage, selectedImage;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    tRealisasiVisitPlan dataCheckinActive;
    tRealisasiVisitPlanRepo realisasiVisitPlanRepo;
    String numberRealisasi;
    private String i_View ="Fragment";
    ProgressDialog pDialog;
    private Gson gson;
    List<clsToken> dataToken;
    clsTokenRepo tokenRepo;
    private AccountManager mAccountManager;
    boolean isOnCreate = false;

    @Override
    public void onBackPressed() {
        Fragment fragmentBack = getSupportFragmentManager().findFragmentById(R.id.frame);
        if ((fragmentBack instanceof IOBackPressed)){
            if (!((IOBackPressed)fragmentBack).onBackPressed())
                super.onBackPressed();
        } else {
            List<Fragment> dtFragment1= new ArrayList<>();
            if(getSupportFragmentManager().getFragments().size()<1){
                Fragment dtFragment=getSupportFragmentManager().getFragments().get(0);
                dtFragment1.add(dtFragment);
            }else{
                dtFragment1=getSupportFragmentManager().getFragments();
            }

            String bundleClass = getIntent().getStringExtra("FragmentClass");
            for (Fragment fragment : dtFragment1) {
                if(fragment!=null){
                    if(fragment.toString().contains("HomeFragment")){
//                    finish();
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

                        builder.setTitle("Exit");
                        builder.setMessage("Are you sure to exit?");

                        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                                System.exit(0);
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        android.app.AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        toolbar.setTitle("Home");
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame, homeFragment);
                        fragmentTransactionHome.commit();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate(savedInstanceState);
        selectedId = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!isMyServiceRunning(MyServiceNative.class)){
            startService(new Intent(MainMenu.this, MyServiceNative.class));
        }
//        List<Long> listId = null;
//        registerReceiver(new ReceiverDownloadManager(listId).receiver, new IntentFilter(
//                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        pDialog = new ProgressDialog(MainMenu.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        mAccountManager = AccountManager.get(getBaseContext());
        isOnCreate = true;
        if (getIntent().getStringExtra(i_View)!=null){
            if (getIntent().getStringExtra(i_View).equals("FragmentNotification")){
                toolbar.setTitle("Notification");
                setSupportActionBar(toolbar);

                FragmentNotification fragmentNotification = new FragmentNotification();
                FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                fragmentTransactionNotification.replace(R.id.frame, fragmentNotification);
                fragmentTransactionNotification.commit();
                selectedId = 99;
            }else if (getIntent().getStringExtra(i_View).equals("FragmentPushData")){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                toolbar.setTitle("Push Data");
                setSupportActionBar(toolbar);

//                toolbar.setTitle("Push Data");
//                toolbar.setSubtitle(null);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Bundle bundle = new Bundle();
                String myMessage = "notMainMenu";
                bundle.putString("message", myMessage );

                FragmentPushData fragmentPushData = new FragmentPushData();
                fragmentPushData.setArguments(bundle);
                FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                fragmentTransactionPushData.replace(R.id.frame, fragmentPushData);
                fragmentTransactionPushData.commit();
                selectedId = 99;
            }
        }else {
            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);


            HomeFragment homFragment = new HomeFragment();
            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.frame, homFragment);
            fragmentTransactionHome.commit();
            selectedId = 99;
        }

//        addProductAndOrder();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);
        ivProfile = (CircleImageView) vwHeader.findViewById(R.id.profile_image);
        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);

        realisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(getApplicationContext());

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(AppIndex.API).build();
        mGoogleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        phtProfile = null;

        if (photoProfile != null) {
            ivProfile.setImageBitmap(photoProfile);
            photoProfile.compress(Bitmap.CompressFormat.PNG, 100, output);
            phtProfile = output.toByteArray();
        }

        ivProfile.setEnabled(false);
        try {
            repoUserImageProfile = new clsPhotoProfilRepo(getApplicationContext());
            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dataImageProfile.size() > 0) {
            viewImageProfile();
        }

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageProfile();
            }
        });

        try {
            loginRepo = new mUserLoginRepo(getApplicationContext());
            dataLogin = (List<mUserLogin>) loginRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvUsername.setText(new clsActivity().greetings() + dataLogin.get(0).getTxtUserName().toString());
        tvEmail.setText(dataLogin.get(0).getTxtEmail().toString());
        if (dataLogin.get(0).getBlobImg()!=null){
            Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dataLogin.get(0).getBlobImg());
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ivProfile.setImageBitmap(bitmap1);
        }

        String linkAPI = new mConfigRepo(getApplicationContext()).APIToken;
        try {
            URL u = new URL(linkAPI);
            linkAPI = u.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Menu header = navigationView.getMenu();
        SubMenu subMenuVersion = header.addSubMenu(R.id.groupVersion, 0, 3, "Version");
        try {
            subMenuVersion.add(getPackageManager().getPackageInfo(getPackageName(), 0).versionName + " \u00a9 KN-IT").setIcon(R.mipmap.ic_android).setEnabled(false);
            subMenuVersion.add(linkAPI).setIcon(R.mipmap.ic_link).setEnabled(false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // get menu from db SQLite

        int menuActive = 0;
        menuActive = R.id.groupMenuDinamis;
        boolean isDataReady = new clsMainBL().isDataReady(getApplicationContext());

        if (!isDataReady){
            header.removeItem(R.id.mnCallPlan);
        }

        dataCheckinActive = new tRealisasiVisitPlanRepo(getApplicationContext()).getDataCheckinActive();
        if (dataCheckinActive==null){
            header.removeGroup(R.id.groupMenuDinamis);
            header.removeItem(R.id.mnCheckOut);
        } else {
            header.removeItem(R.id.mnCallPlan);
            header.removeItem(R.id.mnLogout);
        }

//        TextView view = (TextView) navigationView.getMenu().findItem(R.id.mnNotification).setTitle(" (" +String.valueOf(notificationList.size())+ ")");
        CircularTextView badge = (CircularTextView) header.findItem(R.id.mnNotification).getActionView().findViewById(R.id.text);

        try {
            List<tNotification> notificationList = (List<tNotification>)  new tNotificationRepo(getApplicationContext()).findOutletId();
            if (notificationList!=null){
                if (notificationList.size()>0){
                    badge.setText(String.valueOf(notificationList.size()));
                    badge.setSolidColor("#81C784");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            menuRepo = new mMenuRepo(getApplicationContext());
//            dataMenu = (List<mMenuData>) menuRepo.findAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        linkMenu = new String[dataMenu.size()];
//        listMenu = new String[dataMenu.size()];
//
//        for (int i = 0; i < dataMenu.size(); i++) {
//            int resId = getResources().getIdentifier(String.valueOf(dataMenu.get(i).txtDescription.toLowerCase()), "drawable", MainMenu.this.getPackageName());
//            Drawable icon = MainMenu.this.getResources().getDrawable(resId);
//
//            header.add(menuActive, i, 1, dataMenu.get(i).getTxtMenuName()).setIcon(icon).setCheckable(true);
//
//            linkMenu[i] = dataMenu.get(i).getTxtLink();
//            listMenu[i] = dataMenu.get(i).getTxtMenuName();
//        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                checkNavItem = menuItem;
                pDialog.setCancelable(false);
                pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                pDialog.show();
                drawerLayout.closeDrawers();

                return true;
            }
        });

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Fragment fragment = null;
                pDialog.dismiss();
                if(checkNavItem!= null){
                    switch (checkNavItem.getItemId()) {
                        case R.id.mnLogout:
                            checkNavItem = null;
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainMenu.this);

                            builder.setTitle("Confirm");
                            builder.setMessage("Logout Application?");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        pushData();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
//                                final ProgressDialog dialog2 = new ProgressDialog(MainMenu.this, ProgressDialog.STYLE_SPINNER);
//                                dialog2.setIndeterminate(true);
//                                dialog2.setMessage("Logging out...");
//                                dialog2.setCancelable(false);
//                                dialog2.show();
//
//                                new Handler().postDelayed(
//                                        new Runnable() {
//                                            public void run() {
//
//                                                List<Long> listId = new ArrayList<>();
////                                                unregisterReceiver(new ReceiverDownloadManager(listId).receiver);
//                                                // On complete call either onLoginSuccess or onLoginFailed
//                                                clearData();
//                                                // onLoginFailed();
//                                                dialog2.dismiss();
//                                            }
//                                        }, 3000);
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                            break;
                        case R.id.home:
                            checkNavItem = null;
                            toolbar.setTitle("Home");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            HomeFragment homFragment = new HomeFragment();
                            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionHome.replace(R.id.frame, homFragment);
                            fragmentTransactionHome.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnpushData:
                            checkNavItem = null;
                            toolbar.setTitle("Push Data");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentPushData fragmentPushData = new FragmentPushData();
                            FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionPushData.replace(R.id.frame, fragmentPushData);
                            fragmentTransactionPushData.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnDownloadData:
                            checkNavItem = null;
                            toolbar.setTitle("Download Data");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentDownloadData fragmentDownloadData = new FragmentDownloadData();
                            FragmentTransaction fragmentTransactionDownloadData = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionDownloadData.replace(R.id.frame, fragmentDownloadData);
                            fragmentTransactionDownloadData.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnCallPlan:
                            checkNavItem = null;
                            toolbar.setTitle("Call Plan");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//                        FragmentListCallPlan fragmentCallPlan = new FragmentListCallPlan();
                            FragmentHeaderCallPlan fragmentCallPlan = new FragmentHeaderCallPlan();
                            FragmentTransaction fragmentTransactionCallPlan = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionCallPlan.replace(R.id.frame, fragmentCallPlan);
                            fragmentTransactionCallPlan.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnAkusisi:
                            checkNavItem = null;
                            toolbar.setTitle("Akusisi");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentAkuisisi fragmentAkuisisi = new FragmentAkuisisi();
                            FragmentTransaction fragmentTransactionAkuisisi = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionAkuisisi.replace(R.id.frame, fragmentAkuisisi);
                            fragmentTransactionAkuisisi.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnMaintenance:
                            checkNavItem = null;
                            toolbar.setTitle("Maintenance");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragementMaintenance fragmentMaintenance = new FragementMaintenance();
                            FragmentTransaction fragmentTransactionMaintenance = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionMaintenance.replace(R.id.frame, fragmentMaintenance);
                            fragmentTransactionMaintenance.commit();
                            selectedId = 99;
                            break;

                        case R.id.mnInfoProgram:
                            checkNavItem = null;
                            toolbar.setTitle("Info Program");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragementInfoProgram fragementInfoProgram = new FragementInfoProgram();
                            FragmentTransaction fragmentTransactionInfoProgram = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionInfoProgram.replace(R.id.frame, fragementInfoProgram);
                            fragmentTransactionInfoProgram.commit();
                            selectedId = 99;

                            break;
                        case R.id.mnNotification:
                            checkNavItem = null;
                            toolbar.setTitle("Notification");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentNotification fragmentNotification = new FragmentNotification();
                            FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionNotification.replace(R.id.frame, fragmentNotification);
                            fragmentTransactionNotification.commit();
                            selectedId = 99;

                            break;
                        case R.id.mnSetting:
                            checkNavItem = null;
                            toolbar.setTitle("Setting");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentSetting fragmentSetting = new FragmentSetting();
                            FragmentTransaction fragmentTransactionSetting = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSetting.replace(R.id.frame, fragmentSetting);
                            fragmentTransactionSetting.commit();
                            selectedId = 99;
                            break;

                        case R.id.mnCheckOut:
                            checkNavItem = null;
                            showCustomDialog();
//                        selectedId = 99;
                            break;
                        default:
                            checkNavItem = null;
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                            try {
                                Class<?> fragmentClass = Class.forName(linkMenu[checkNavItem.getItemId()]);
                                try {
                                    toolbar.setTitle(checkNavItem.getTitle().toString());
                                    toolbar.setSubtitle(null);

                                    fragment = (Fragment) fragmentClass.newInstance();
                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.frame, fragment);
                                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                                    fragmentTransaction.commit();
                                    selectedId = checkNavItem.getItemId();
                                    isSubMenu = false;

                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                    }
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    // put image from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_PROFILE) {
            if (resultCode == -1) {
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    String uri = uriImage.getPath().toString();

                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropProfile();

//                    previewCaptureImage2(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "User cancel take image", Toast.LENGTH_SHORT).show();
            }  else {
                try {
                    photoProfile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == PIC_CROP_PROFILE) {
            if (resultCode == -1) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");

                previewCaptureImageProfile(thePic);
            } else if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "User cancel take image", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SELECT_FILE_PROFILE) {
            if(resultCode == RESULT_OK){
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    selectedImage = data.getData();
                    String uri = selectedImage.getPath().toString();
                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropGalleryProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if (requestCode == 100 || requestCode == 130 || requestCode==150) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void clearData() {
        Intent intent = new Intent(MainMenu.this, SplashActivity.class);
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataAfterLogout();
        finish();
        startActivity(intent);
    }

    private void pushData() throws JSONException {
        pDialog.setMessage("Please wait....");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        final clsPushData dtJson = new clsHelperBL().pushData(versionName, getApplicationContext());
        if (dtJson == null){
        }else {
            String linkPushData = new clsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushData(getApplicationContext(), linkPushData, dtJson, pDialog, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    new ToastCustom().showToasty(getApplicationContext(),message,4);
//                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
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
                                logout(false);
//                                ToastCustom.showToasty(getApplicationContext(),"Success Push Data",1);
                            }else {
                                new ToastCustom().showToasty(getApplicationContext(),txtMessage, 4);
                            }

                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        new ToastCustom().showToasty(getApplicationContext(),strErrorMsg,4);
                        pDialog.dismiss();
                    }
                }
            });
        }
    }

    private void logout(boolean isMustLogout) {
        String strLinkAPI = new clsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        mUserLogin dtLogin = new clsMainBL().getUserLogin(getApplicationContext());
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID() );
            tokenRepo = new clsTokenRepo(getApplicationContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", dataJson);
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new clsHelperBL().volleyLogin(MainMenu.this, strLinkAPI, mRequestBody, "Logout....", isMustLogout, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        LoginMobileApps model = gson.fromJson(jsonObject.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        if (txtStatus == true){

                            stopService(new Intent(getApplicationContext(), MyServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            deleteMediaStorage();
                            clearData();
                            ShortcutBadger.removeCountOrThrow(getApplicationContext());
//                            loginRepo = new mUserLoginRepo(getApplicationContext());
//                            mUserLogin data = new mUserLogin();
//                            data.setTxtGuID(model.getData().getTxtGuiID());
//                            data.setIntUserID(model.getData().getIntUserID());
//                            data.setTxtUserName(model.getData().getTxtUserName());
//                            data.setTxtNick(model.getData().getTxtNick());
//                            data.setTxtEmpID(model.getData().getTxtEmpID());
//                            data.setTxtEmail(model.getData().getTxtEmail());
//                            data.setIntDepartmentID(model.getData().getIntDepartmentID());
//                            data.setIntLOBID(model.getData().getIntLOBID());
//                            data.setTxtCompanyCode(model.getData().getTxtCompanyCode());
//                            if (model.getData().getMUserRole()!=null){
//                                data.setIntRoleID(model.getData().getMUserRole().getIntRoleID());
//                                data.setTxtRoleName(model.getData().getMUserRole().getTxtRoleName());
//                            }
//                            data.setDtLogIn(parseDate(model.getData().getDtDateLogin()));
//                            loginRepo.createOrUpdate(data);

                            Log.d("Data info", "logout Success");

                            Intent intent = new Intent(MainMenu.this, SplashActivity.class);
                            finish();
                            startActivity(intent);

                        } else {
                            new ToastCustom().showToasty(MainMenu.this,txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ShortcutBadgeException e) {
                        Intent intent = new Intent(MainMenu.this, SplashActivity.class);
                        finish();
                        startActivity(intent);
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void deleteMediaStorage (){
        File mediaStorageDir = new File(new clsHardCode().txtFolderData + File.separator);
        if (mediaStorageDir.exists()){
            if (mediaStorageDir.isDirectory()){
                for (File currentFile : mediaStorageDir.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDir.delete();
        }

        File akuisisi = new File(new clsHardCode().txtFolderAkuisisi + File.separator);
        if (akuisisi.exists()){
            if (akuisisi.isDirectory()){
                for (File currentFile : akuisisi.listFiles()){
                    currentFile.delete();
                }
            }
            akuisisi.delete();
        }

        File checkin = new File(new clsHardCode().txtFolderCheckIn + File.separator);
        if (checkin.exists()){
            if (checkin.isDirectory()){
                for (File currentFile : checkin.listFiles()){
                    currentFile.delete();
                }
            }
            checkin.delete();
        }
    }

    private void selectImageProfile() {
        final CharSequence[] items = { "Ambil Foto", "Pilih dari Galeri",
                "Batal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= new Utility().checkPermission(getApplicationContext());
                if (items[item].equals("Ambil Foto")) {
                    if(result)
                        captureImageProfile();
                } else if (items[item].equals("Pilih dari Galeri")) {
                    if(result)
                        galleryIntentProfile();
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void viewImageProfile() {
        try {
            repoUserImageProfile = new clsPhotoProfilRepo(getApplicationContext());
            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/data/data/KalbeFamily/tempdata/Foto_Profil");
        folder.mkdir();

        for (clsPhotoProfile imgDt : dataImageProfile){
            final byte[] imgFile = imgDt.getTxtImg();
            if (imgFile != null) {
                mybitmapImageProfile = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(mybitmapImageProfile, 150, 150, true);
                ivProfile.setImageBitmap(bitmap);
            }
        }
    }

    // preview image profile
    private void previewCaptureImageProfile(Bitmap photo){
        try {
            Bitmap bitmap = new clsActivity().resizeImageForBlob(photo);
            ivProfile.setVisibility(View.VISIBLE);
            output = null;
            try {
                output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null){
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            phtProfile = output.toByteArray();
            ivProfile.setImageBitmap(photo_view);

            saveImageProfile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void saveImageProfile() {
        try {
            repoUserImageProfile = new clsPhotoProfilRepo(getApplicationContext());
            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clsPhotoProfile data = new clsPhotoProfile();
        data.setTxtGuiId("1");
        data.setTxtDescription("Profile");
        data.setTxtImg(phtProfile);

        repoUserImageProfile.createOrUpdate(data);
        Toast.makeText(getApplicationContext(), "Image Profile Saved", Toast.LENGTH_SHORT).show();
    }

    protected void captureImageProfile() {
        uriImage = getOutputMediaFileUri();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_PROFILE);
    }

    private void galleryIntentProfile() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , SELECT_FILE_PROFILE);//one can be replaced with any action code
    }

    private void performCropProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void performCropGalleryProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(selectedImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new clsHardCode().txtFolderData + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_act"  + ".png");
        return mediaFile;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                // NO need to show the dialog;

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(MainMenu.this, REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    //failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }

    public class Utility {
        public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    public void checkout(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Checkout");
        builder.setMessage("Are you sure to check out?");

        builder.setPositiveButton("CHECK OUT", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                try {
                    mUserLogin dtLogin = new clsMainBL().getUserLogin(getApplicationContext());

                    DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                    data.setTxtRealisasiVisitId(dataCheckinActive.getTxtRealisasiVisitId());
                    data.setTxtProgramVisitSubActivityId(dataCheckinActive.getTxtProgramVisitSubActivityId());
                    data.setIntUserId(dataCheckinActive.getIntUserId());
                    data.setIntRoleID(dataCheckinActive.getIntRoleID());
                    data.setTxtDokterId(dataCheckinActive.getTxtDokterId());
                    data.setTxtDokterName(dataCheckinActive.getTxtDokterName());
                    data.setTxtApotekId(dataCheckinActive.getTxtApotekId());
                    data.setTxtApotekName(dataCheckinActive.getTxtApotekName());
                    data.setDtCheckIn(dataCheckinActive.getDtCheckIn());
                    data.setDtCheckOut(dateTimeFormat.format(cal.getTime()));
                    data.setDtDateRealisasi(dateFormat.format(dateTimeFormat.parse(dtLogin.getDtLogIn()))); ///tanggal login
                    data.setDtDatePlan(dataCheckinActive.getDtDatePlan());
                    long angka = Long.parseLong(numberRealisasi);
                    data.setIntNumberRealisasi(Long.parseLong(numberRealisasi)); //generate number
                    data.setTxtAcc(dataCheckinActive.getTxtAcc());
                    data.setTxtLat(dataCheckinActive.getTxtLat());
                    data.setTxtLong(dataCheckinActive.getTxtLong());
                    data.setTxtImgName1(dataCheckinActive.getTxtImgName1());
                    data.setBlobImg1(dataCheckinActive.getBlobImg1());
                    data.setTxtImgName2(dataCheckinActive.getTxtImgName2());
                    data.setBlobImg2(dataCheckinActive.getBlobImg2());
                    data.setIntStatusRealisasi(new clsHardCode().Realisasi);
                    data.setIntFlagPush(new clsHardCode().Save);
                    realisasiVisitPlanRepo.createOrUpdate(data);

                    tProgramVisitSubActivity dtVisit = new tProgramVisitSubActivityRepo(getApplicationContext()).findBytxtId(dataCheckinActive.getTxtProgramVisitSubActivityId());
                    dtVisit.setIntFlagPush(new clsHardCode().Save);
                    new tProgramVisitSubActivityRepo(getApplicationContext()).createOrUpdate(dtVisit);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                nextScreen.putExtra("keyMainMenu", "main_menu");
                finish();
                startActivity(nextScreen);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_checkout);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv_title = (TextView)dialog.findViewById(R.id.cd_title);
        TextView tv_subtitle = (TextView)dialog.findViewById(R.id.cd_subtitle);
        tv_title.setText("Realisasi");
        tv_subtitle.setText("Please fill number realization before you checkout");
        final EditText et_int_number_realisasi = (EditText) dialog.findViewById(R.id.et_int_number_realisasi);
        et_int_number_realisasi.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_int_number_realisasi.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        ((AppCompatButton) dialog.findViewById(R.id.btn_cancel_realisasi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.btn_submit_realisasi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberRealisasi = et_int_number_realisasi.getText().toString().trim();
                if (numberRealisasi.equals("")) {
                    new ToastCustom().showToasty(getApplicationContext(),"Please fill number realization...",4);
                } else if (numberRealisasi==null){
                    new ToastCustom().showToasty(getApplicationContext(),"Please fill number realization...",4);
                }else {
                    checkout();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        Account[] accounts = new AuthenticatorUtil().countingAccount(mAccountManager);
        if (!isOnCreate){
            try {
                clsStatusMenuStart _clsStatusMenuStart = new clsMainBL().checkUserActive(getApplicationContext());
                if (_clsStatusMenuStart.get_intStatus()!=null){
                    if (_clsStatusMenuStart.get_intStatus()== enumStatusMenuStart.FormLogin){
                        logout(true);
                    }else if (_clsStatusMenuStart.get_intStatus()==enumStatusMenuStart.PushDataMobile){
                        Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                        myIntent.putExtra(i_View, "FragmentPushData");
                        finish();
                        startActivity(myIntent);
                    }else if (_clsStatusMenuStart.get_intStatus()==enumStatusMenuStart.UserActiveLogin){
                        if (accounts.length>0){
                            boolean isExist = false;
                            for (int i=0; i<accounts.length; i++){
                                if (accounts[i].name.equals(dataLogin.get(0).getTxtUserName().toUpperCase().toString())){
                                    isExist = true;
                                    break;
                                }
                            }
                            if (!isExist){
                                Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                                myIntent.putExtra(i_View, "FragmentPushData");
                                finish();
                                startActivity(myIntent);
                            }
                        } else {
                            Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                            myIntent.putExtra(i_View, "FragmentPushData");
                            finish();
                            startActivity(myIntent);
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        isOnCreate = false;

        super.onResume();
    }
}
