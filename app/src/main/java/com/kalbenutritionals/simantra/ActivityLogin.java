package com.kalbenutritionals.simantra;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.InputFilter.InputFilters;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterSpinner;
import com.kalbenutritionals.simantra.CustomView.Utils.AuthenticatorUtil;
import com.kalbenutritionals.simantra.CustomView.Utils.DrawableClickListener;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.ListExpeditionItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.ListORGItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbenutritionals.simantra.Database.Common.ClsExpedition;
import com.kalbenutritionals.simantra.Database.Common.ClsOrganisation;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserRole;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsExpedition;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsOrganisation;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomMenu;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserRole;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.ViewModel.VmSpinner;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;


/**
 * Robert on 02/13/2019.
 */

public class ActivityLogin extends AccountAuthenticatorActivity {
    Account availableAccounts[];
    String name[];
    @BindView(R.id.editTextUsername)
    TextInputEditText editTextUsername;
    @BindView(R.id.spnRoleLogin)
    AppCompatSpinner spnRoleLogin;
    @BindView(R.id.editTextPass)
    TextInputEditText editTextPass;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.buttonRefreshApp)
    Button buttonRefreshApp;
    @BindView(R.id.tvVersionLogin)
    TextView tvVersionLogin;
    @BindView(R.id.ln_form_login2)
    LinearLayout lnFormLogin2;
    @BindView(R.id.cd_login)
    CardView cdLogin;
    private String mAuthTokenType;
    private AlertDialog mAlertDialog;
    private AccountManager mAccountManager;
    private final String TAG = this.getClass().getSimpleName();
    //batas aman

    private PackageInfo pInfo = null;
    private static final int REQUEST_READ_PHONE_STATE = 0;
    private String IS_FROM_PICK_ACCOUNT = "is from pick account";
    String txtUsername, txtPassword, imeiNumber, deviceName, access_token, selectedRole;
    private final List<String> roleName = new ArrayList<String>();
    private int intSet = 1;
    int intProcesscancel = 0;
    private HashMap<String, Integer> HMRole = new HashMap<>();
    List<ClsToken> dataToken;
    RepomUserLogin loginRepo;
    RepoclsToken tokenRepo;
    RepomMenu menuRepo;
    boolean isFromPickAccount = false;
    private Gson gson;
    ThreadPoolExecutor executor;
    ProgressDialog progress;
    Unbinder unbinder;
    List<VmSpinner> listRole = new ArrayList<>();
    AdapterSpinner dataAdapter;

    @Override
    public void onBackPressed() {
        if (isFromPickAccount) {
            new AuthenticatorUtil().showAccountPicker(ActivityLogin.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

            imeiNumber = tm.getDeviceId().toString();
            deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        } else {
            //TODO
            imeiNumber = tm.getDeviceId().toString();
            deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        }

        mAccountManager = AccountManager.get(getBaseContext());

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        isFromPickAccount = getIntent().getBooleanExtra(IS_FROM_PICK_ACCOUNT, false);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;

        tvVersionLogin = (TextView) findViewById(R.id.tvVersionLogin);
        tvVersionLogin.setText(pInfo.versionName);

        char[] chars = {'.'};
        new InputFilters().etCapsTextWatcherNoSpace(editTextUsername, null, chars);
        spnRoleLogin.setEnabled(false);
        if (accountName != null) {
            editTextUsername.setText(accountName);
        }
        // Spinner Drop down elements


//        roleName.add("Select One");
//        HMRole.put("Select One", 0);

        editTextUsername.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    intProcesscancel = 0;
                    txtUsername = editTextUsername.getText().toString();
                    txtPassword = editTextPass.getText().toString();
                    if (!txtUsername.equals("")) {
                        getRole();
                    } else {
                        editTextUsername.requestFocusFromTouch();
                        new ToastCustom().showToasty(ActivityLogin.this, "Please input username", 4);
                    }
                    return true;
                }
                return false;
            }
        });

        // Creating adapter for spinnerTelp

        VmSpinner dataSpn = new VmSpinner();
        dataSpn.setIntKey(0);
        dataSpn.setTxtValue("Select One");
        listRole.add(dataSpn);

        dataAdapter = new AdapterSpinner(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listRole){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.green_300));
                }
                return view;
            }
        };

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleName);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initializing an ArrayAdapter with initial text like select one
//        spinnerArrayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item, roleName) {
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                } else {
//                    tv.setTextColor(getResources().getColor(R.color.green_300));
//                }
//                return view;
//            }
//        };

        // attaching data adapter to spinner
        spnRoleLogin.setAdapter(dataAdapter);

        spnRoleLogin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VmSpinner role = dataAdapter.getItem(i);
                selectedRole = role.getTxtValue();
//                selectedRole = spnRoleLogin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                new ToastCustom().showToasty(ActivityLogin.this, "Please select role", 4);
                // put code here
            }
        });

        editTextPass.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(editTextPass) {
            public boolean onDrawableClick() {
                if (intSet == 1) {
                    editTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock_open, 0);
                    intSet = 0;
                } else {
                    editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock_close, 0);
                    intSet = 1;
                }

                return true;
            }
        });

        editTextPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                            keyCode == KeyEvent.KEYCODE_ENTER) {
                        buttonLogin.performClick();
                        return true;
                    }
                }

                return false;
            }
        });

        editTextPass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    buttonLogin.performClick();
                    return true;
                }
                return false;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupSubmit();

                /*if (editTextUsername.getText().toString().equals("")) {
                    new ToastCustom().showToasty(ActivityLogin.this, "Please fill Username", 4);
                } else if (editTextPass.getText().toString().equals("")) {
                    new ToastCustom().showToasty(ActivityLogin.this, "Please fill Password", 4);
                }
                else if (listRole.size()==1){
                    getRole();
                } else if (dataAdapter.getItem(spnRoleLogin.getSelectedItemPosition()).getIntKey()==0){
                    new ToastCustom().showToasty(LoginActivity.this,"Please select role",4);
                }
                 else
                {
                    popupSubmit();
                }*/
            }
        });
//
//        checkVersion();
        buttonRefreshApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ClsHardCode().copydb(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Ping", Toast.LENGTH_SHORT).show();
//                requestToken(ActivityLogin.this);
            }
        });

        buttonRefreshApp.setVisibility(View.GONE);
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

        executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
    }

    private void popupSubmit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are You sure?");

        builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                login();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // sesuaikan username dan password dengan data di server
    private void login() {
        txtUsername = editTextUsername.getText().toString();
        txtPassword = editTextPass.getText().toString();
        VmSpinner spinner = dataAdapter.getItem(spnRoleLogin.getSelectedItemPosition());
        int intRoleId = spinner.getIntKey();
        String strLinkAPI = new ClsHardCode().linkLogin;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        if(intRoleId == 0){
            new ToastCustom().showToasty(getApplicationContext(),"You need to choose role",4);
        }else{

            try {
                jData.put("username", txtUsername);
                jData.put("intRoleId", intRoleId);
                jData.put("password", txtPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                tokenRepo = new RepoclsToken(getApplicationContext());
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
            final String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
            final boolean newAccount = getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false);

            final Bundle datum = new Bundle();
            new FastNetworkingUtils().FNRequestPostData(ActivityLogin.this, strLinkAPI, resJson, "Logging in, please wait", new InterfaceFastNetworking() {
                @Override
                public void onResponse(JSONObject response) {

                    if (response != null) {
                        final LoginMobileApps model = gson.fromJson(response.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();


                        if (txtStatus == true) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent res = null;
                                    String accessToken = "dummy_access_token";
                                    loginRepo = new RepomUserLogin(getApplicationContext());
                                    ClsmUserLogin data = new ClsmUserLogin();
                                    data.setTxtGuID(model.getData().getTxtGuiID());
                                    data.setIntUserID(model.getData().getIntUserID());
                                    data.setTxtUserName(model.getData().getTxtUserName());
                                    data.setTxtNick(model.getData().getTxtNick());
                                    data.setTxtEmpID(model.getData().getTxtEmpID());
                                    data.setTxtEmail(model.getData().getTxtEmail());
                                    data.setIntDepartmentID(model.getData().getIntDepartmentID());
                                    data.setIntLOBID(model.getData().getIntLOBID());
                                    data.setTxtCompanyCode(model.getData().getTxtCompanyCode());
                                    if (model.getData().getMUserRole() != null) {
                                        data.setIntRoleID(model.getData().getMUserRole().getIntRoleID());
                                        data.setTxtRoleName(model.getData().getMUserRole().getTxtRoleName());
                                    }
                                    data.setDtLogIn(parseDate(model.getData().getDtDateLogin()));

                                    byte[] file = getByte(model.getData().getTxtLinkFotoProfile());
                                    if (file != null) {
                                        data.setBlobImg(file);
                                    } else {
                                        data.setBlobImg(null);
                                    }
                                    if(model.getData().getListExpedition()!=null){
                                        List<ListExpeditionItem> items = model.getData().getListExpedition();
                                        for (ListExpeditionItem item :
                                                items) {
                                            ClsExpedition dtExp = new ClsExpedition();
                                            dtExp.setVENDOR_ID(item.getVENDORID());
                                            dtExp.setVENDOR_NAME(item.getVENDORNAME());
                                            try {
                                                new RepoClsExpedition(getApplicationContext()).createOrUpdate(dtExp);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    if (model.getData().getListORG()!=null){
                                        List<ListORGItem> items = model.getData().getListORG();
                                        for (ListORGItem item :
                                                items) {
                                            ClsOrganisation dtOrg = new ClsOrganisation();
                                            dtOrg.setORGANIZATION_CODE(item.getORGANIZATIONCODE());
                                            dtOrg.setORGANIZATION_ID(item.getORGANIZATIONID());
                                            dtOrg.setORGANIZATION_NAME(item.getORGANIZATIONNAME());
                                            try {
                                                new RepoClsOrganisation(getApplicationContext()).createOrUpdate(dtOrg);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }


                                    try {
                                        loginRepo.createOrUpdate(data);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("Data info", "Login Success");

                                    datum.putString(AccountManager.KEY_ACCOUNT_NAME, txtUsername);
                                    datum.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                                    datum.putString(AccountManager.KEY_AUTHTOKEN, accessToken);
                                    datum.putString(PARAM_USER_PASS, txtPassword);
                                    datum.putString(ARG_AUTH_TYPE, mAuthTokenType);
                                    datum.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                                    res = new Intent();
                                    res.putExtras(datum);
                                    finishLogin(res, mAccountManager);
                                    Intent intent = new Intent(ActivityLogin.this, ActivityMainMenu.class);
                                    finish();
                                    startActivity(intent);
                                }
                            }).start();

                        } else {
                            new ToastCustom().showToasty(ActivityLogin.this, txtMessage, 4);
                        }
                    }
                }

                @Override
                public void onError(ANError error) {

                }
            });
        }

    }

    public byte[] getByte(String url) {
        if (url != null) {
            if (url.length() > 0) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL imageUrl = new URL(url);
                    URLConnection ucon = imageUrl.openConnection();
                    String contentType = ucon.getHeaderField("Content-Type");
                    boolean image = contentType.startsWith("image/");
                    boolean text = contentType.startsWith("application/");
                    if (image || text) {
                        byte[] data = null;
                        InputStream is = ucon.getInputStream();
                        int length = ucon.getContentLength();
                        data = new byte[length];
                        int bytesRead;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        while ((bytesRead = is.read(data)) != -1) {
                            output.write(data, 0, bytesRead);
                        }
                        return output.toByteArray();
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.d("ImageManager", "Error: " + e.toString());
                }
            }
        }
        return null;
    }

    private String parseDate(String dateParse) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse != null && dateParse != "")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    public void finishLogin(Intent intent, AccountManager mAccountManager) {
        Log.d("kalbe", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String authtokenType = intent.getStringExtra(ARG_AUTH_TYPE);
        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("kalbe", TAG + "> finishLogin > addAccountExplicitly");
            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            Log.d("kalbe", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getRole() {
        String strLinkAPI = new ClsHardCode().LinkUserRole;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        txtUsername = editTextUsername.getText().toString();
        txtPassword = editTextPass.getText().toString();
        try {
            jData.put("username", txtUsername);
            jData.put("intRoleId", 0);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new RepoclsToken(getApplicationContext());
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
        new FastNetworkingUtils().FNRequestPostData(ActivityLogin.this, strLinkAPI, resJson, "Getting your role", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONObject jsn = response.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");
                        JSONArray arrayData = response.getJSONArray("data");
                        listRole.clear();
                        if (txtStatus == true) {
                            VmSpinner dataSpn = new VmSpinner();
                            dataSpn.setIntKey(0);
                            dataSpn.setTxtValue("Select One");
                            listRole.add(dataSpn);
                            if (arrayData != null) {
                                if (arrayData.length() > 0) {
                                    int index = 0;
                                    for (int i = 0; i < arrayData.length(); i++) {
                                        JSONObject object = arrayData.getJSONObject(i);
                                        String txtRoleName = object.getString("txtRoleName");
                                        int intRoleId = object.getInt("intRoleId");
                                        roleName.add(txtRoleName);

                                        ClsmUserRole data = new ClsmUserRole();
                                        data.setTxtId(String.valueOf(index));
                                        data.setIntRoleId(intRoleId);
                                        data.setTxtRoleName(txtRoleName);
                                        RepomUserRole userRoleRepo = new RepomUserRole(getApplicationContext());
                                        userRoleRepo.createOrUpdate(data);

                                        VmSpinner dataSpn1 = new VmSpinner();
                                        dataSpn1.setIntKey(intRoleId);
                                        dataSpn1.setTxtValue(txtRoleName);
                                        listRole.add(dataSpn1);
                                        index++;
                                    }
                                    dataAdapter.notifyDataSetChanged();
                                    spnRoleLogin.setEnabled(true);
                                    if (arrayData.length()==1){
                                        spnRoleLogin.setSelection(1);
                                    }

                                } else {
                                    VmSpinner dataSpn2 = new VmSpinner();
                                    dataSpn2.setIntKey(0);
                                    dataSpn2.setTxtValue("-");
                                    listRole.add(dataSpn2);
                                    dataAdapter.notifyDataSetChanged();
                                    spnRoleLogin.setEnabled(false);
                                }
                            }
                        } else {
                            VmSpinner dataSpn2 = new VmSpinner();
                            dataSpn2.setIntKey(0);
                            dataSpn2.setTxtValue("-");
                            listRole.add(dataSpn2);
                            dataAdapter.notifyDataSetChanged();
                            spnRoleLogin.setEnabled(false);
                            editTextUsername.requestFocus();
                            new ToastCustom().showToasty(ActivityLogin.this, txtMessage, 4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(getApplicationContext(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler.Callback handler = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progress.dismiss();
            return true;
        }
    };
}
