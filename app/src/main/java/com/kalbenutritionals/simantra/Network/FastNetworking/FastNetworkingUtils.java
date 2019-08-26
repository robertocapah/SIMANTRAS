package com.kalbenutritionals.simantra.Network.FastNetworking;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kalbenutritionals.simantra.ActivitySplash;
import com.kalbenutritionals.simantra.CustomView.Utils.ViewAnimation;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmConfigData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;
import com.kalbenutritionals.simantra.R;

/**
 * Created by Robert on 2/12/2019.
 */

public class FastNetworkingUtils {
    String TAG = "FastNetworking";
    RepoclsToken tokenRepo;
    String clientId = "";
    public void FNRequestToken(final Context activity,String progressBarType, final InterfaceFastNetworking listener) {
        String username = "";
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
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();
        String strLinkAPI = new ClsHardCode().linkToken;
        AndroidNetworking.post(strLinkAPI)
                .addBodyParameter("grant_type", "password")
                .addBodyParameter("username", username)
                .addBodyParameter("password", "")
                .addBodyParameter("client_id", clientId)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        ErrorHandlerFN(activity, error, TAG);
                        listener.onError(error);
                        Dialog.dismiss();
                    }
                });
    }
    public void FNRequestPostDataUpdateToken(final Context context, String txtLink, JSONObject JObject, final String progressBarType, final InterfaceFastNetworking listener) {
        String access_token = "";
        List<ClsToken> dataToken = null;
        try {
            dataToken = new RepoclsToken(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        access_token = dataToken.get(0).txtUserToken.toString();
        access_token = "abSRnV5k-WqJWkzEm_-Yi8GWV9wR7FqNAjrzGjw-25Ldbu_rH8_KD-TGYSdQH_Gbg98oOjR74ko0Q1OdO8OFryEzvOBS48MrEKfahY8Bv7cL9mRsctBUPOS6tzhSr3AMIBItRnb6CViP_ncOL9gcGQXYxnj14gttZ_ls_LQ2LhDm0VQYZheCzYu7itLJI-Notfr_3ei6sJfwrlBXCFfZ5pY90hMHwBSYf1HAyZgWStUmgDQx0U-ghHtB9qkZjY64oI2xjVCwpuVx0LFqVdcnIDCWDwZtKPJ_C_eWDoTTsGNIYPeWxeWwuREu3RbU1ewhS3_6heYzwm1vRfsf_YZgRELtlZA46mKo7om5drF9yP4eYf3fMxEXvIvMLkMMKSxP";
        AndroidNetworking.post(txtLink)
                .addJSONObjectBody(JObject)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        listener.onError(error);
                        ErrorHandlerFN(context, error, TAG);
                    }
                });
    }
    public void FNRequestPostData(final Activity activity, String txtLink, JSONObject JObject, final String progressBarType, final InterfaceFastNetworking listener) {
        String access_token = "";
        List<ClsToken> dataToken = null;
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();
        access_token = getToken(activity);
        AndroidNetworking.post(txtLink)
                .addJSONObjectBody(JObject)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        listener.onError(error);
                        Dialog.dismiss();
                        ErrorHandlerFN(activity, error, TAG);
                    }
                });
    }
    public void FNRequestPostDataNoProgress(final Activity activity, String txtLink, JSONObject JObject, final InterfaceFastNetworking listener) {
        String access_token = "";
        List<ClsToken> dataToken = null;
        access_token = getToken(activity);
        AndroidNetworking.post(txtLink)
                .addJSONObjectBody(JObject)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        listener.onError(error);
                        ErrorHandlerFN(activity, error, TAG);
                    }
                });
    }

    public void FNRequestPostDataWthBody(final Context context, String txtLink, final String progressBarType, final InterfaceFastNetworking listener) {
        String access_token = "";
        List<ClsToken> dataToken = null;
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();
        access_token = getToken(context);
        AndroidNetworking.post(txtLink)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        listener.onError(error);
                        Dialog.dismiss();
                        ErrorHandlerFN(context, error, TAG);
                    }
                });
    }

    public void FNRequestDownloadAPKFile(final Activity ctx, String strLinkAPI, final String txtPathUserData, final String apkName, final String tag, final Dialog dialog, final InterfaceFastNetworkingDownloadFile listener) {
        dialog.show();
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);
        AndroidNetworking.download(strLinkAPI, txtPathUserData, apkName)
                .setTag(tag)
                .setPriority(Priority.MEDIUM)
                .doNotCacheResponse()
                .setPercentageThresholdForCancelling(50) // even if at the time of cancelling it will not cancel if 50%
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(final long bytesDownloaded, final long totalBytes) {
                        // do anything with progress
                        listener.onProgress(bytesDownloaded, totalBytes);
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
                        listener.onDownloadComplete();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                        ErrorHandlerFN(ctx,error,tag);
                    }
                });
    }

    public void FNRequestUploadFotoProfile(final Activity ctx, String strLinkAPI, final String mRequestBody, final Dialog dialog, final String tag, final ClsmUserLogin dtLogin, final InterfaceFastNetworkingUploadFile listener) {
        dialog.show();
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);
        TextView tvProgressDesc = (TextView) dialog.findViewById(R.id.tvProgressDesc);
        tvProgressDesc.setText("Updating Profile");
        String access_token  = getToken(ctx);
        byte[] b = dtLogin.getBlobImg();
        String path = new ClsHardCode().txtPathTemp;
        File fileFoto = new PickFile().decodeByteArraytoFile(b, path, "FotoProfileName.jpg");
        AndroidNetworking.upload(strLinkAPI)
                .addMultipartFile("image", fileFoto)
                .addMultipartParameter("txtParam", mRequestBody)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(final long bytesUploaded, final long totalBytes) {
                        // do anything with progress
                        listener.onProgress(bytesUploaded, totalBytes);
                        ctx.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double precentage = ((double) bytesUploaded / (double) totalBytes) * 100;
                                progressD.setProgress((int) precentage);
                            }
                        });
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                        ErrorHandlerFN(ctx,error,tag);
                    }
                });
    }
    public String getToken(Context context){
        String token= "";
        try {
            token = new RepoclsToken(context).findToken();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }
    Dialog dialog;
    public void FNRequestUploadListImage(final Activity ctx, String strLinkAPI, final String mRequestBody, Map<String,File> files, final String progressBarType, final String tag, final InterfaceFastNetworking listener) {
        String access_token = getToken(ctx);
        final ProgressDialog Dialog = new ProgressDialog(ctx);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
//        Dialog.show();
        dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.layout_progress_download_apk);
        TextView tvProcessDesc = dialog.findViewById(R.id.tvProgressDesc);
        final TextView tvProgressText = dialog.findViewById(R.id.tvLoadingProgressText);
        final LinearLayout lyt_progress = dialog.findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.GONE);
        tvProcessDesc.setText("Pushing Datas ...");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);
        dialog.show();
        AndroidNetworking.upload(strLinkAPI)
                .addMultipartFile(files)
                .addMultipartParameter("txtParam", mRequestBody)
                .addHeaders("Authorization", "Bearer " + access_token)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(final long bytesUploaded, final long totalBytes) {
                        // do anything with progress
                        ctx.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double precentage = ((double) bytesUploaded / (double) totalBytes) * 100;
                                int pr = (int) precentage;
                                progressD.setProgress(pr);
                                if (pr==100){
                                    tvProgressText.setText("Processing Data");
                                    lyt_progress.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResponse(response);
                        Dialog.dismiss();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                        ErrorHandlerFN(ctx,error,tag);
                        Dialog.dismiss();
                        dialog.dismiss();
                    }
                });
    }

    private void ErrorHandlerFN(Context context, ANError error, String TAG) {
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        }
        new ToastCustom().showToasty(context, "Network Error "+error.getErrorBody() + " " + error.getErrorDetail(), 4);
        if (error.getErrorCode() == 401){
            new ActivitySplash().requestToken(context,1);
        }
        if (error.getErrorCode() == 408){
            new ToastCustom().showToasty(context, "Network Error Request Timeout " + error.getErrorDetail(), 4);
        }
    }
}
