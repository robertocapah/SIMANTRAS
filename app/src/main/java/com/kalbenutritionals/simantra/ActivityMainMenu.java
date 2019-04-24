package com.kalbenutritionals.simantra;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
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
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import com.kalbenutritionals.simantra.BL.BLMain;
import com.kalbenutritionals.simantra.CustomView.Utils.AuthenticatorUtil;
import com.kalbenutritionals.simantra.CustomView.Utils.IOBackPressed;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbenutritionals.simantra.Database.Common.ClsStatusMenuStart;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmJenisPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;
import com.kalbenutritionals.simantra.Database.Repo.EnumStatusMenuStart;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Fragment.FragmentApprover;
import com.kalbenutritionals.simantra.Fragment.FragmentChecklist;
import com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker;
import com.kalbenutritionals.simantra.Fragment.FragmentHome;
import com.kalbenutritionals.simantra.Fragment.FragmentNotification;
import com.kalbenutritionals.simantra.Fragment.FragmentPushData;
import com.kalbenutritionals.simantra.Fragment.FragmentSPMSearch;
import com.kalbenutritionals.simantra.Fragment.FragmentSearch;
import com.kalbenutritionals.simantra.Fragment.FragmentSetting;
import com.kalbenutritionals.simantra.Fragment.FragmentTab;
import com.kalbenutritionals.simantra.Fragment.FragmentTestUI;
import com.kalbenutritionals.simantra.Fragment.FragmentTransactions;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.Service.ServiceNative;

/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class ActivityMainMenu extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    List<ClsmUserLogin> dataLogin = null;
    RepomUserLogin loginRepo;
    PackageInfo pInfo = null;
    int selectedId;
    Boolean isSubMenu = false;
    String[] linkMenu;
    MenuItem checkNavItem = null;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.ad_container)
    LinearLayout adContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private String i_View = "Fragment";
    ProgressDialog pDialog;
    private Gson gson;
    List<ClsToken> dataToken;
    RepoclsToken tokenRepo;
    private AccountManager mAccountManager;
    boolean isOnCreate = false;
    Unbinder unbinder;
    Toolbar toolbar;
    public CircleImageView ivProfile;
    TextView tvUsername, tvEmail;

    @Override
    public void onBackPressed() {
        Fragment fragmentBack = getSupportFragmentManager().findFragmentById(R.id.frame);
        if ((fragmentBack instanceof IOBackPressed)) {
            if (!((IOBackPressed) fragmentBack).onBackPressed())
                super.onBackPressed();
        } else {
            List<Fragment> dtFragment1 = new ArrayList<>();
            if (getSupportFragmentManager().getFragments().size() < 1) {
                Fragment dtFragment = getSupportFragmentManager().getFragments().get(0);
                dtFragment1.add(dtFragment);
            } else {
                dtFragment1 = getSupportFragmentManager().getFragments();
            }

            String bundleClass = getIntent().getStringExtra("FragmentClass");
            for (Fragment fragment : dtFragment1) {
                if (fragment != null) {
                    if (fragment.toString().contains("FragmentHome")) {
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
                    } else {
                        toolbar.setTitle("Home");
                        FragmentHome fragmentHome = new FragmentHome();
                        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame, fragmentHome);
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
        GenerateDataHardCode(getApplicationContext());
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

        if (!isMyServiceRunning(ServiceNative.class)) {
            startService(new Intent(ActivityMainMenu.this, ServiceNative.class));
        }
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerNav = navigationView.getHeaderView(0);
        tvEmail = (TextView) headerNav.findViewById(R.id.tvEmail);
        tvUsername = (TextView) headerNav.findViewById(R.id.tvUsername);
        pDialog = new ProgressDialog(ActivityMainMenu.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        mAccountManager = AccountManager.get(getBaseContext());
        isOnCreate = true;
        if (getIntent().getStringExtra(i_View) != null) {
            if (getIntent().getStringExtra(i_View).equals("FragmentNotification")) {
            } else {
                toolbar.setTitle("Home");
                setSupportActionBar(toolbar);
                FragmentHome homFragment = new FragmentHome();
                FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                fragmentTransactionHome.replace(R.id.frame, homFragment);
                fragmentTransactionHome.commit();
                selectedId = 99;
            }
        }
        if (getIntent().getStringExtra(new ClsHardCode().txtBundleKeyBarcode) != null) {
            FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
            FragmentTransaction fragmentTransactionInfoChecker = getSupportFragmentManager().beginTransaction();
            fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
            fragmentTransactionInfoChecker.commit();
        } else if (getIntent().getStringExtra(i_View) != null) {
            if (getIntent().getStringExtra(i_View).equals("FragmentNotification")) {
                toolbar.setTitle("Notification");
                setSupportActionBar(toolbar);

                FragmentNotification fragmentNotification = new FragmentNotification();
                FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                fragmentTransactionNotification.replace(R.id.frame, fragmentNotification);
                fragmentTransactionNotification.commit();
                selectedId = 99;
            } else if (getIntent().getStringExtra(i_View).equals("FragmentPushData")) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                toolbar.setTitle("Push Data");
                setSupportActionBar(toolbar);

//                toolbar.setTitle("Push Data");
//                toolbar.setSubtitle(null);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Bundle bundle = new Bundle();
                String myMessage = "notMainMenu";
                bundle.putString("message", myMessage);

                FragmentPushData fragmentPushData = new FragmentPushData();
                fragmentPushData.setArguments(bundle);
                FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                fragmentTransactionPushData.replace(R.id.frame, fragmentPushData);
                fragmentTransactionPushData.commit();
                selectedId = 99;
            }
        } else {
            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);


            FragmentHome homFragment = new FragmentHome();
            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.frame, homFragment);
            fragmentTransactionHome.commit();
            selectedId = 99;
        }


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);
        ivProfile = (CircleImageView) vwHeader.findViewById(R.id.profile_image);

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
        ivProfile.setEnabled(false);


        try {
            loginRepo = new RepomUserLogin(getApplicationContext());
            dataLogin = (List<ClsmUserLogin>) loginRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dataLogin.size() > 0) {
            tvUsername.setText(dataLogin.get(0).getTxtUserName());
            tvEmail.setText(dataLogin.get(0).getTxtEmail());
        } else {
            tvUsername.setText("Roberto Oloan Capah");
            tvEmail.setText("Roberto.capah@kalbenutritionals.com");
        }

        if (dataLogin.get(0).getBlobImg() != null) {
            Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dataLogin.get(0).getBlobImg());
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ivProfile.setImageBitmap(bitmap1);
        }

        String linkAPI = new RepomConfig(getApplicationContext()).APIToken;
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
                if (checkNavItem != null) {
                    switch (checkNavItem.getItemId()) {
                        case R.id.mnLogout:
                            checkNavItem = null;
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityMainMenu.this);

                            builder.setTitle("Confirm");
                            builder.setMessage("Logout Application?");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    logout(false);
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
                            FragmentHome homFragment = new FragmentHome();
                            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionHome.replace(R.id.frame, homFragment);
                            fragmentTransactionHome.commit();
                            selectedId = 99;
                            break;
                        case R.id.search:
                            checkNavItem = null;
                            toolbar.setTitle("Search");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            FragmentSearch searchFragment = new FragmentSearch();
                            FragmentTransaction fragmentTransactionSearch = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSearch.replace(R.id.frame, searchFragment);
                            fragmentTransactionSearch.commit();
                            selectedId = 99;
                            break;
                        case R.id.approver:
                            checkNavItem = null;
                            toolbar.setTitle("APPROVER LIST");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            FragmentApprover fragmentApprover = new FragmentApprover();
                            FragmentTransaction fragmentTransactionApprover = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionApprover.replace(R.id.frame, fragmentApprover);
                            fragmentTransactionApprover.commit();
                            selectedId = 99;
                            break;
                        case R.id.transporter:
                            checkNavItem = null;
                            toolbar.setTitle("SIMANTRA");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            /*FragmentTab fragmentTab = new FragmentTab();
                            FragmentTransactions fragmentTransactionTab = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                            fragmentTransactionTab.commit();*/

                            FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
                            FragmentTransaction fragmentTransactionSPMSearch = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
                            fragmentTransactionSPMSearch.commit();

                            /*FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
                            FragmentTransactions fragmentTransactionInfoChecker = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
                            fragmentTransactionInfoChecker.commit();*/

                            /*FragmentChecklist checklistFragment = new FragmentChecklist();
                            FragmentTransactions fragmentTransactionChecklist = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionChecklist.replace(R.id.frame, checklistFragment);
                            fragmentTransactionChecklist.commit();*/
                            selectedId = 99;
                            break;

                        case R.id.transaction:
                            FragmentTransactions fragmentTransactions = new FragmentTransactions();
                            FragmentTransaction fragmentTransactionsTrans = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionsTrans.replace(R.id.frame,fragmentTransactions);
                            fragmentTransactionsTrans.commit();
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
                        case R.id.mntestMenu:
                            checkNavItem = null;
                            toolbar.setTitle("Setting");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentTestUI fragmentTestUI = new FragmentTestUI();
                            FragmentTransaction fragmentTransactionTestUI = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTestUI.replace(R.id.frame, fragmentTestUI);
                            fragmentTransactionTestUI.commit();
                            selectedId = 99;
                            break;

                        default:
                            checkNavItem = null;
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                            /*try {
                                Class<?> fragmentClass = Class.forName(linkMenu[checkNavItem.getItemId()]);
                                try {
                                    toolbar.setTitle(checkNavItem.getTitle().toString());
                                    toolbar.setSubtitle(null);

                                    fragment = (Fragment) fragmentClass.newInstance();
                                    FragmentTransactions fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
                            }*/
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
    public void GenerateDataHardCode(Context context){
        ClsmPertanyaan pertanyaan = new ClsmPertanyaan();
        pertanyaan.setIntPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
        pertanyaan.setBolHaveAnswer(false);
        pertanyaan.setIntJenisPertanyaanId(1);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setTxtPertanyaan("Tuliskan beberapa hal di dalam textbox di bawah ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
        pertanyaan = new ClsmPertanyaan();
        pertanyaan.setBolHaveAnswer(true);
        pertanyaan.setIntPertanyaanId(2);
        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanCheckBox);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setTxtPertanyaan("pilih beberapa dari pertanyaan di checkbox di bawah ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
//        pertanyaan = new ClsmPertanyaan();
//        pertanyaan.setIntPertanyaanId(3);
//        pertanyaan.setBolHaveAnswer(true);
//        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanRadioButton);
//        pertanyaan.setIntLocationDocsId(1);
//        pertanyaan.setBolHavePhoto(true);
//        pertanyaan.setTxtPertanyaan("pilih satu dari beberapa di radioButton di bawah ya pak");
//        try{
//            new RepomPertanyaan(getApplicationContext()).createOrUpdate(pertanyaan);
//        }catch (Exception e){
//
//        }
//        pertanyaan = new ClsmPertanyaan();
//        pertanyaan.setIntPertanyaanId(4);
//        pertanyaan.setBolHaveAnswer(true);
//        pertanyaan.setBolHavePhoto(true);
//        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
//        pertanyaan.setIntLocationDocsId(1);
//        pertanyaan.setTxtPertanyaan("isi textbox dan ambil sebuah gambar ya pak");
//        try{
//            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
//        }catch (Exception e){
//
//        }
//        pertanyaan = new ClsmPertanyaan();
//        pertanyaan.setIntPertanyaanId(7);
//        pertanyaan.setBolHaveAnswer(false);
//        pertanyaan.setBolHavePhoto(true);
//        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
//        pertanyaan.setIntLocationDocsId(1);
//        pertanyaan.setTxtPertanyaan("Tuliskan beberapa hal di dalam textbox di bawah ya pak (2)");
//        try{
//            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
//        }catch (Exception e){
//
//        }

        ClsmJawaban clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(1);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja   1");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(2);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  2");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(3);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  3");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
//        clsmJawaban = new ClsmJawaban();
//        clsmJawaban.setBitActive(true);
//        clsmJawaban.setIdJawaban(4);
//        clsmJawaban.setIdPertanyaan(3);
//        clsmJawaban.setBitChoosen(false);
//        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja 1");
//        try{
//            new RepomJawaban(context).createOrUpdate(clsmJawaban);
//        }catch (Exception ex){
//
//        }
//        clsmJawaban = new ClsmJawaban();
//        clsmJawaban.setBitActive(true);
//        clsmJawaban.setIdJawaban(5);
//        clsmJawaban.setIdPertanyaan(3);
//        clsmJawaban.setBitChoosen(false);
//        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  2");
//        try{
//            new RepomJawaban(context).createOrUpdate(clsmJawaban);
//        }catch (Exception ex){
//
//        }
//        clsmJawaban = new ClsmJawaban();
//        clsmJawaban.setBitActive(true);
//        clsmJawaban.setIdJawaban(6);
//        clsmJawaban.setIdPertanyaan(3);
//        clsmJawaban.setBitChoosen(false);
//        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  3");
//        try{
//            new RepomJawaban(context).createOrUpdate(clsmJawaban);
//        }catch (Exception ex){
//
//        }

    }

    // put image from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || requestCode == 130 || requestCode == 150 ) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }else if(requestCode == FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION){
            new FragmentDetailInfoChecker().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void clearData() {
        Intent intent = new Intent(ActivityMainMenu.this, ActivitySplash.class);
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataAfterLogout();
        finish();
        startActivity(intent);
    }


    private void logout(boolean isMustLogout) {
        String strLinkAPI = new ClsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        ClsmUserLogin dtLogin = null;
        try {
            dtLogin = new RepomUserLogin(getApplicationContext()).getUserLogin(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID());
            tokenRepo = new RepoclsToken(getApplicationContext());
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
        new FastNetworkingUtils().FNRequestPostData(ActivityMainMenu.this, strLinkAPI, resJson, "Logging out", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                Intent res = null;
                if (response != null) {
                    try {
                        LoginMobileApps model = gson.fromJson(response.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        if (txtStatus == true) {

                            stopService(new Intent(getApplicationContext(), ServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
//                            deleteMediaStorage();
                            clearData();
                            ShortcutBadger.removeCountOrThrow(getApplicationContext());

                            Log.d("Data info", "logout Success");

                            Intent intent = new Intent(ActivityMainMenu.this, ActivitySplash.class);
                            finish();
                            startActivity(intent);

                        } else {
                            new ToastCustom().showToasty(ActivityMainMenu.this, txtMessage, 4);
                        }
                    } catch (ShortcutBadgeException e) {
                        Intent intent = new Intent(ActivityMainMenu.this, ActivitySplash.class);
                        finish();
                        startActivity(intent);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(ANError error) {

            }
        });
    }

    /*public static void deleteMediaStorage() {
        File mediaStorageDir = new File(new ClsHardCode().txtFolderData + File.separator);
        if (mediaStorageDir.exists()) {
            if (mediaStorageDir.isDirectory()) {
                for (File currentFile : mediaStorageDir.listFiles()) {
                    currentFile.delete();
                }
            }
            mediaStorageDir.delete();
        }

        File akuisisi = new File(new ClsHardCode().txtFolderAkuisisi + File.separator);
        if (akuisisi.exists()) {
            if (akuisisi.isDirectory()) {
                for (File currentFile : akuisisi.listFiles()) {
                    currentFile.delete();
                }
            }
            akuisisi.delete();
        }

        File checkin = new File(new ClsHardCode().txtFolderCheckIn + File.separator);
        if (checkin.exists()) {
            if (checkin.isDirectory()) {
                for (File currentFile : checkin.listFiles()) {
                    currentFile.delete();
                }
            }
            checkin.delete();
        }
    }*/


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

                    status.startResolutionForResult(ActivityMainMenu.this, REQUEST_CHECK_SETTINGS);

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
        public boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
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
        if (!isOnCreate) {
            try {
                ClsStatusMenuStart _clsStatusMenuStart = new BLMain().checkUserActive(getApplicationContext());
                if (_clsStatusMenuStart.get_intStatus() != null) {
                    if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.FormLogin) {
                        logout(true);
                    } else if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.PushDataMobile) {
                        Intent myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
                        myIntent.putExtra(i_View, "FragmentPushData");
                        finish();
                        startActivity(myIntent);
                    } else if (_clsStatusMenuStart.get_intStatus() == EnumStatusMenuStart.UserActiveLogin) {
                        if (accounts.length > 0) {
                            boolean isExist = false;
                            for (int i = 0; i < accounts.length; i++) {
                                if (accounts[i].name.equals(dataLogin.get(0).getTxtUserName().toUpperCase().toString())) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if (!isExist) {
                                Intent myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
                                myIntent.putExtra(i_View, "FragmentPushData");
                                finish();
                                startActivity(myIntent);
                            }
                        } else {
                            Intent myIntent = new Intent(getApplicationContext(), ActivityMainMenu.class);
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
