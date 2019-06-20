package com.kalbenutritionals.simantra.Fragment;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbenutritionals.simantra.ActivityLogin;
import com.kalbenutritionals.simantra.ActivityMainMenu;
import com.kalbenutritionals.simantra.ActivitySplash;
import com.kalbenutritionals.simantra.BuildConfig;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.filePDF.FilePDF;
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
    View v;
    private Gson gson;
    private final String TAG_DOWNlOAD_FILE = "Download_file";
    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        unbinder = ButterKnife.bind(this, v);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_progress_download_apk);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();

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
                    final FilePDF model = gson.fromJson(response.toString(), FilePDF.class);
                    boolean txtStatus = model.getResult().isStatus();
                    String txtMessage = model.getResult().getMessage();
                    String txtMethode_name = model.getResult().getMethodName();

                    if (model.getData()!=null){
                        String txtLink = model.getData().getLinkFile();
                        final String fileName = model.getData().getFileName();
                        final String txtPathUserData = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS;
//                        new UriData().getOutputMediaUriFolder(getContext(), txtPathUserData);
//                        File mediaStorageDir = new File(txtPathUserData + File.separator);
//                        // Create the storage directory if it does not exist
//                        try {
//                            File yourFile = new File(txtPathUserData);
//                            if (!yourFile.exists()) {
//                                if (yourFile.mkdirs()) {
//                                    Log.d("DOWNLOAD File", "Successfully created the parent dir:" + yourFile.getName());
//                                } else {
//                                    Log.d("DOWNLOAD File", "Failed to create the parent dir:" + yourFile.getName());
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                new FastNetworkingUtils().FNRequestDownloadAPKFile(getActivity(), txtLink, txtPathUserData, fileName, TAG_DOWNlOAD_FILE, dialog, new InterfaceFastNetworkingDownloadFile() {
                    @Override
                    public void onProgress(final long bytesDownloaded, final long totalBytes) {
                        // do anything with progress
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double precentage = ((double) bytesDownloaded / (double) totalBytes) * 100;
//                                progressD.setProgress((int) precentage);
//                                tvProgress.setText("bytes downloaded :" + bytesDownloaded + ". total bytes : " + totalBytes + " precentage : " + precentage + " %");
                            }
                        });
                    }

                    @Override
                    public void onDownloadComplete() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                new ToastCustom().showToasty(getActivity(), "Download Complete", 1);
                                // location = "/sdcard/my_folder";
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                Uri mydir = new UriData().getOutputMediaUriFolder(getContext(), txtPathUserData);
////                                intent.setDataAndType(mydir,"application/*");    // or use */*
//                                intent.setDataAndType(mydir, "resource/folder");
//                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(ANError error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                    }
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
