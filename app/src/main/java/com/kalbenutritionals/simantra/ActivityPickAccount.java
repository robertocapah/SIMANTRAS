package com.kalbenutritionals.simantra;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.kalbenutritionals.simantra.Data.ResponseDataJson.getRole.ResponseGetRoles;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.ListExpeditionItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.ListORGItem;
import com.kalbenutritionals.simantra.Database.Common.ClsExpedition;
import com.kalbenutritionals.simantra.Database.Common.ClsOrganisation;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmConfigData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserRole;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsExpedition;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsOrganisation;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.Network.Volley.InterfaceVolleyResponseListener;
import com.kalbenutritionals.simantra.Network.Volley.VolleyUtils;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepomMenu;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserRole;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbenutritionals.simantra.CustomView.Utils.AuthenticatorUtil;
import com.kalbenutritionals.simantra.CustomView.Utils.SpacingItemDecoration;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;

import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral;
import com.oktaviani.dewi.mylibrary.authenticator.RecyclerGridPickAccountAdapter;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_AVAILABLE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

public class ActivityPickAccount extends Activity {

    Account availableAccounts[];
    String name[];
    private String mAuthTokenType;
    private AlertDialog mAlertDialog;
    private AccountManager mAccountManager;
    private final String TAG = this.getClass().getSimpleName();
    //batas aman
    private RecyclerGridPickAccountAdapter adapter;
    private static final int REQUEST_READ_PHONE_STATE = 0;
    EditText etUsername, etPassword;
    String txtUsername, txtPassword, imeiNumber, deviceName, access_token;
    String clientId = "";
    Button btnSubmit, btnExit;
    private HashMap<String, Integer> HMRole = new HashMap<>();
    private String single_choice_selected;
    private String[] role;
    private int intSet = 1;
    int intProcesscancel = 0;
    List<ClsToken> dataToken;
    RepomUserLogin loginRepo;
    RepoclsToken tokenRepo;

    RepoClsOrganisation RepoOrg;
    RepoClsExpedition RepoExp;
    RepomMenu menuRepo;
    //    ListView listView;
    RecyclerView lvRecycleview;
    View parent_view;
    Button btnAddAcc;
    private Gson gson;
    final List<String> account = new ArrayList<>();

    @Override
    public void onBackPressed() {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        setContentView(R.layout.activity_pick_account_v3);
        btnAddAcc = (Button) findViewById(R.id.btnAddAccount);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mAccountManager = AccountManager.get(getBaseContext());
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;

        lvRecycleview = (RecyclerView) findViewById(R.id.lv_pick_v3);
        lvRecycleview.setLayoutManager(new GridLayoutManager(this, 2));
        lvRecycleview.addItemDecoration(new SpacingItemDecoration(5, new ClsTools().dpToPx(this, 1), true));
        lvRecycleview.setHasFixedSize(true);
        parent_view = findViewById(android.R.id.content);

        String[] names = getIntent().getStringArrayExtra(ARG_ARRAY_ACCOUNT_NAME);
        Parcelable[] parceAccount = getIntent().getParcelableArrayExtra(ARG_ARRAY_ACCOUNT_AVAILABLE);
        account.clear();
        List<Integer> icon = new ArrayList<>();
        if (parceAccount!=null){
            availableAccounts = Arrays.copyOf(parceAccount, parceAccount.length, Account[].class);
            if (availableAccounts.length>0){
                name = new String[names.length+1];
                for (int i=0; i<names.length; i++){
                    account.add(names[i]);
                    icon.add(R.drawable.profile);
                }
            }
        }
        adapter = new RecyclerGridPickAccountAdapter(this, account,icon);
        lvRecycleview.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerGridPickAccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                if (account.get(position).equals("Add New Account")){
                    new AuthenticatorUtil().addNewAccount(ActivityPickAccount.this, mAccountManager, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, true);
                } else {
                    new AuthenticatorUtil().getExistingAccountAuthToken(ActivityPickAccount.this, mAccountManager,availableAccounts[position], AUTHTOKEN_TYPE_FULL_ACCESS);
                }
            }
        });
        adapter.setOnImageClickListener(new RecyclerGridPickAccountAdapter.OnImageClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                if (account.get(position).equals("Add New Account")){
                    new AuthenticatorUtil().addNewAccount(ActivityPickAccount.this, mAccountManager, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, true);
                } else {
                    new AuthenticatorUtil().getExistingAccountAuthToken(ActivityPickAccount.this, mAccountManager,availableAccounts[position], AUTHTOKEN_TYPE_FULL_ACCESS);
                }
            }
        });

        adapter.setOnImageTrashClickListener(new RecyclerGridPickAccountAdapter.OnImageTrashClickListener() {
            @Override
            public void onItemClick(View view, String obj, final int position) {
                final int pos = position;
                //ini isFromPickAccount di buat false karena biar nggak intent 2 kali
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPickAccount.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure to delete " + account.get(position) +" ?");

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new AuthenticatorUtil().RemoveAccount(mAccountManager, availableAccounts[position], ActivityPickAccount.this, adapter, account, availableAccounts, position,  AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, false);
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
        });

        btnAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AuthenticatorUtil().addNewAccount(ActivityPickAccount.this, mAccountManager, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, true);
            }
        });
    }
    private String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
    }

    public void getRole(final String[] data_token, final Activity activity, final AccountManager mAccountManager){
        String strLinkAPI = new ClsHardCode().LinkUserRole;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        txtUsername = data_token[0];
        txtPassword = data_token[1];
        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", 0);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new RepoclsToken(activity);
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
        new FastNetworkingUtils().FNRequestPostData(activity, strLinkAPI, resJson, "Getting your role", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    try {
//                        ResponseGetRoles
                        JSONObject jsn = response.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");
                        JSONArray arrayData = response.getJSONArray("data");
                        if (txtStatus==true){
                            if (arrayData != null) {
                                if (arrayData.length()>0){
                                    int index = 0;
                                    role = new String[arrayData.length()];
                                    if (arrayData.length()==1){
                                        JSONObject object = arrayData.getJSONObject(0);
                                        int intRoleId = object.getInt("intRoleId");
                                        popupSubmit(data_token, intRoleId, activity, mAccountManager);
                                    }else{
                                        for (int i = 0; i < arrayData.length(); i++){
                                            JSONObject object = arrayData.getJSONObject(i);
                                            String txtRoleName = object.getString("txtRoleName");
                                            int intRoleId = object.getInt("intRoleId");
                                            role[i] = txtRoleName;

                                            ClsmUserRole data = new ClsmUserRole();
                                            data.setTxtId(String.valueOf(index));
                                            data.setIntRoleId(intRoleId);
                                            data.setTxtRoleName(txtRoleName);;
                                            RepomUserRole userRoleRepo = new RepomUserRole(activity.getApplicationContext());
                                            userRoleRepo.createOrUpdate(data);

                                            HMRole.put(txtRoleName, intRoleId);
                                            index++;
                                        }

                                        single_choice_selected = "";
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                                        builder.setTitle("ROLE");
                                        builder.setSingleChoiceItems(role, -1, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                single_choice_selected = role[i];
                                            }
                                        });
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        final android.support.v7.app.AlertDialog alertD = builder.create();
                                        alertD.show();
//                                    builder.show();
                                        final Button btnPos = alertD.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                                        btnPos.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (!single_choice_selected.equals("")){
                                                    popupSubmit(data_token, HMRole.get(single_choice_selected), activity, mAccountManager);
                                                    alertD.dismiss();
                                                }else {
                                                    new ToastCustom().showToasty(activity,"Please select role",4);
                                                }
                                            }
                                        });
                                    }

                                }else {
//                                    spnRoleLogin.setEnabled(false);
                                }
                            }
                        }else {
                            new ToastCustom().showToasty(activity.getApplicationContext(),txtMessage,4);
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

            }
        });
    }

    private void popupSubmit(final String[] data_token, final int intRoleName, final Activity activity, final AccountManager mAccountManager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Confirm");
        builder.setMessage("Are You sure?");

        builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                login(data_token, intRoleName, activity, mAccountManager);
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

    public void login(final String[] data_token, int intRoleName, final Activity activity, final AccountManager mAccountManager) {
        txtUsername = data_token[0];
        txtPassword = data_token[1];
        int intRoleId = intRoleName;
        String strLinkAPI = new ClsHardCode().linkLogin;
        JSONObject resJson = new JSONObject();
        this.mAccountManager = mAccountManager;

        JSONObject jData = new JSONObject();

        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", intRoleId);
            jData.put("password", txtPassword);
            jData.put("txtNameApp", ClsHardCode.TXT_NAME_APP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            loginRepo = new RepomUserLogin(activity.getApplicationContext());
            tokenRepo = new RepoclsToken(activity.getApplicationContext());
            RepoOrg = new RepoClsOrganisation(activity.getApplicationContext());
            RepoExp = new RepoClsExpedition(activity.getApplicationContext());
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
        final String accountType = data_token[2];
        final boolean newAccount = false;
        final Bundle datum = new Bundle();
        new FastNetworkingUtils().FNRequestPostData(activity, strLinkAPI, resJson, "Logging in", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                Intent res = null;
                if (response != null) {
                    try {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gson = gsonBuilder.create();
                        LoginMobileApps model = gson.fromJson(response.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        String accessToken = "dummy_access_token";

                        if (txtStatus == true){
                            loginRepo = new RepomUserLogin(activity);
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
                            if (model.getData().getMUserRole()!=null){
                                data.setIntRoleID(model.getData().getMUserRole().getIntRoleID());
                                data.setTxtRoleName(model.getData().getMUserRole().getTxtRoleName());
                            }
                            data.setDtLogIn(parseDate(model.getData().getDtDateLogin()));
                            byte[] file = new ActivityLogin().getByte(model.getData().getTxtLinkFotoProfile());
                            if (file!=null){
                                data.setBlobImg(file);
                            }else {
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
                                        RepoExp.createOrUpdate(dtExp);
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
                                        RepoOrg.createOrUpdate(dtOrg);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            loginRepo.createOrUpdate(data);

                            Log.d("Data info", "Login Success");

                            datum.putString(AccountManager.KEY_ACCOUNT_NAME, txtUsername);
                            datum.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                            datum.putString(AccountManager.KEY_AUTHTOKEN, accessToken);
                            datum.putString(PARAM_USER_PASS, txtPassword);
                            datum.putString(ARG_AUTH_TYPE, data_token[3]);
                            datum.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                            res = new Intent();
                            res.putExtras(datum);
                            new ActivityLogin().finishLogin(res, mAccountManager);
                            Intent intent = new Intent(activity, ActivityMainMenu.class);
                            activity.finish();
                            activity.startActivity(intent);

                        } else {
                            new ToastCustom().showToasty(activity.getApplicationContext(),txtMessage,4);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(ANError error) {

            }
        });
    }


    /*private void volleyLogin(final Activity activity, String strLinkAPI, final String mRequestBody, final String[] data_token, final int intRoleName, String progressBarType, final InterfaceVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        RepomConfig configRepo = new RepomConfig(activity.getApplicationContext());
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = (List<ClsToken>) tokenRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new ClsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(activity, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
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
                                    Toast.makeText(activity.getApplicationContext(), "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        login(data_token, intRoleName, activity, mAccountManager);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    tokenRepo = new RepoclsToken(activity.getApplicationContext());
                    dataToken = (List<ClsToken>) tokenRepo.findAll();
                    access_token = dataToken.get(0).getTxtUserToken();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }*/

    private void logout() {
        Intent intent = new Intent(ActivityPickAccount.this, ActivitySplash.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Account[] accounts = new AuthenticatorUtil().countingAccount(mAccountManager);
        if (accounts.length>0){
            if (accounts.length<(account.size())){
                for (int i=0; i<account.size(); i++){
                    for (int j=0; j<accounts.length; j++){
                        if (accounts[j].name.equals(account.get(i))){
                            account.remove(i);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }else {
            Intent intent = new Intent(ActivityPickAccount.this, ActivitySplash.class);
            finish();
            startActivity(intent);
        }

        super.onResume();
    }
}
