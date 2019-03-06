package com.kalbenutritionals.simantra;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Network.Volley.InterfaceVolleyResponseListener;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.PushLogError.PushLogError;
import com.kalbenutritionals.simantra.CustomView.Utils.AuthenticatorUtil;
import com.kalbenutritionals.simantra.Network.Volley.VolleyUtils;

import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public class ActivityChangePasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText et_current_pw, et_new_pw, et_confirm_pw;
    Button btn_change_pw;
    RepoclsToken tokenRepo;
    List<ClsToken> dataToken;
    private Gson gson;
    ClsmUserLogin dtLogin;
    private AccountManager mAccountManager;
    boolean isOnCreate = false;
    private String i_View ="Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAccountManager = AccountManager.get(getBaseContext());
        et_current_pw = (TextInputEditText)findViewById(R.id.et_current_pw);
        et_new_pw = (TextInputEditText)findViewById(R.id.et_new_pw);
        et_confirm_pw = (TextInputEditText)findViewById(R.id.et_confirm_pw);
        btn_change_pw = (Button)findViewById(R.id.btn_change_pw);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        try {
            dtLogin = new RepomUserLogin(getApplicationContext()).getUserLogin(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        et_current_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (et_new_pw.getText().length()<6&&et_new_pw.getText().length()>0){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords must be at least 6 characters long",4);
                    }else if (!et_new_pw.getText().toString().equals(et_confirm_pw.getText().toString())){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords do not match",4);
                    }
            }
        });

        et_new_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (et_new_pw.getText().length()>6&&!et_new_pw.getText().toString().equals(et_confirm_pw.getText().toString())){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords do not match",4);
                    }
            }
        });

        et_confirm_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (et_new_pw.getText().length()<6&&et_new_pw.getText().length()>0){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords must be at least 6 characters long",4);
                    }
            }
        });

        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = false;
                Account[] accounts = new AuthenticatorUtil().countingAccount(mAccountManager);
                if (accounts!=null)
                    if (accounts.length>0){
                        for (int i=0; i<accounts.length; i++){
                            if (accounts[i].name.equals(dtLogin.getTxtUserName().toUpperCase().toString())){
                                isExist = true;
                            }
                        }
                    }

                if (isExist){
                    if (et_current_pw.getText().length()==0){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"Please fill current password",4);
                    }else if (et_new_pw.getText().length()<6){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords must be at least 6 characters long",4);
                    }else if (!et_new_pw.getText().toString().equals(et_confirm_pw.getText().toString())){
                        new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"New passwords do not match",4);
                    }else if (et_new_pw.getText().toString().equals(et_confirm_pw.getText().toString())&&et_current_pw.getText().length()>0){
//                        ubah bagian method ini aja
                        ChangePassword();
                    }
                }else {
                    onBackPressed();
                    new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"Your Account Manager has been deleted",4);
                }
            }
        });
    }

    private void ChangePassword() {
        String strLinkAPI = new ClsHardCode().linkChangePassword;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();

        try {
            jData.put("username",dtLogin.getTxtUserName() );
            jData.put("intRoleId", dtLogin.getIntRoleID());
            jData.put("oldPassword", et_current_pw.getText().toString());
            jData.put("newPassword", et_new_pw.getText().toString());
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
        new VolleyUtils().volleyLogin(ActivityChangePasswordActivity.this, strLinkAPI, mRequestBody, "Please Wait....", false, new InterfaceVolleyResponseListener() {
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
                        PushLogError model = gson.fromJson(jsonObject.toString(), PushLogError.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        Account[] accounts = new AuthenticatorUtil().countingAccount(mAccountManager);

                        if (txtStatus == true){
                            for (int i=0; i<accounts.length; i++){
                                if (accounts[i].name.equals(dtLogin.getTxtUserName().toUpperCase().toString())){
                                    mAccountManager.setPassword(accounts[i], et_new_pw.getText().toString());
                                }
                            }
                            onBackPressed();
                            new ToastCustom().showToasty(ActivityChangePasswordActivity.this,"Success Change Password",1);

                        } else {
                            new ToastCustom().showToasty(ActivityChangePasswordActivity.this,txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(parentIntent);
        finish();
    }
}
