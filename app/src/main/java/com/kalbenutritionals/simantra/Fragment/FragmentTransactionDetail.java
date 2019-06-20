package com.kalbenutritionals.simantra.Fragment;

import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbenutritionals.simantra.ActivityLogin;
import com.kalbenutritionals.simantra.ActivityMainMenu;
import com.kalbenutritionals.simantra.BuildConfig;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworkingDownloadFile;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

/**
 * Created by dewi.oktaviani on 13/06/2019.
 */

public class FragmentTransactionDetail extends Fragment {
//    @BindView(R.id.coordinator_lyt_no_item)
//    CoordinatorLayout coordinatorLytNoItem;
    @BindView(R.id.btn_download)
    ImageButton btnDownload;

    Unbinder unbinder;
    View v;;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
//                Toast.makeText(getContext(), "Haiii", Toast.LENGTH_SHORT).show();
//                new FastNetworkingUtils().FNRequestDownloadAPKFile(getActivity(), txtLink, txtPathUserData, apkName, TAG_DOWNlOAD_APK, dialog, new InterfaceFastNetworkingDownloadFile() {
//                    @Override
//                    public void onProgress(final long bytesDownloaded, final long totalBytes) {
//                        // do anything with progress
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                double precentage = ((double) bytesDownloaded / (double) totalBytes) * 100;
//                                progressD.setProgress((int) precentage);
////                                tvProgress.setText("bytes downloaded :" + bytesDownloaded + ". total bytes : " + totalBytes + " precentage : " + precentage + " %");
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onDownloadComplete() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                Toast.makeText(getApplicationContext(), "Download Complete", Toast.LENGTH_SHORT).show();
//                                String txtPath = txtPathUserData + apkName;
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    Toast.makeText(getApplicationContext(), "File downloaded", Toast.LENGTH_LONG).show();
//                                    try {
//                                        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                        File file = new File(txtPath);
//                                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
//                                        intent.setData(uri);
//                                        startActivity(intent);
//                                        finish();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "File downloaded", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                    if (Build.VERSION.SDK_INT >= 24) {
//                                        intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(txtPath)), "application/vnd.android.package-archive");
//                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                    } else {
//                                        intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
//                                    }
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                            }
//                        });
//                    }
//                });
            }
        });
        //detail pemeriksaan


        return v;
    }

    private void downloadFile(){
        String strLinkAPI = new ClsHardCode().linkDownloadFilePDF + "KNS17112519";
        new FastNetworkingUtils().FNRequestPostDataWthBody(getActivity(), strLinkAPI, "Download, please wait...", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {
//                    final LoginMobileApps model = gson.fromJson(response.toString(), LoginMobileApps.class);
//                    boolean txtStatus = model.getResult().isStatus();
//                    String txtMessage = model.getResult().getMessage();
//                    String txtMethode_name = model.getResult().getMethodName();
//
//
//                    if (txtStatus == true) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent res = null;
//                                String accessToken = "dummy_access_token";
//                                loginRepo = new RepomUserLogin(getApplicationContext());
//                                ClsmUserLogin data = new ClsmUserLogin();
//                                data.setTxtGuID(model.getData().getTxtGuiID());
//                                data.setIntUserID(model.getData().getIntUserID());
//                                data.setTxtUserName(model.getData().getTxtUserName());
//                                data.setTxtNick(model.getData().getTxtNick());
//                                data.setTxtEmpID(model.getData().getTxtEmpID());
//                                data.setTxtEmail(model.getData().getTxtEmail());
//                                data.setIntDepartmentID(model.getData().getIntDepartmentID());
//                                data.setIntLOBID(model.getData().getIntLOBID());
//                                data.setTxtCompanyCode(model.getData().getTxtCompanyCode());
//                                if (model.getData().getMUserRole() != null) {
//                                    data.setIntRoleID(model.getData().getMUserRole().getIntRoleID());
//                                    data.setTxtRoleName(model.getData().getMUserRole().getTxtRoleName());
//                                }
//                                data.setDtLogIn(parseDate(model.getData().getDtDateLogin()));
//
//                                byte[] file = getByte(model.getData().getTxtLinkFotoProfile());
//                                if (file != null) {
//                                    data.setBlobImg(file);
//                                } else {
//                                    data.setBlobImg(null);
//                                }
//                                try {
//                                    loginRepo.createOrUpdate(data);
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                                Log.d("Data info", "Login Success");
//
//                                datum.putString(AccountManager.KEY_ACCOUNT_NAME, txtUsername);
//                                datum.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                                datum.putString(AccountManager.KEY_AUTHTOKEN, accessToken);
//                                datum.putString(PARAM_USER_PASS, txtPassword);
//                                datum.putString(ARG_AUTH_TYPE, mAuthTokenType);
//                                datum.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
//                                res = new Intent();
//                                res.putExtras(datum);
//                                finishLogin(res, mAccountManager);
//                                Intent intent = new Intent(ActivityLogin.this, ActivityMainMenu.class);
//                                finish();
//                                startActivity(intent);
//                            }
//                        }).start();

                    } else {
                        new ToastCustom().showToasty(getActivity(), "gagal :(", 4);
                    }
                }

                    @Override
                    public void onError(ANError error) {

                    }
                }

        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
