package shp.template.Network.FastNetworking;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import shp.template.Data.ClsHardCode;
import shp.template.Database.Common.ClsToken;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Database.Repo.RepoclsToken;
import shp.template.R;

/**
 * Created by Robert on 2/12/2019.
 */

public class FastNetworkingUtils {
    String TAG = "FastNetworking";

    public void FNRequestToken(final Context activity, String txtLink, final String username, final String clientId, String progressBarType, final InterfaceFastNetworking listener) {

        AndroidNetworking.post(txtLink)
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
                    }

                    @Override
                    public void onError(ANError error) {
                        ErrorHandlerFN(activity, error, TAG);
                        listener.onError(error);
                    }
                });
    }

    public void FNRequestPostData(final Context context, String txtLink, JSONObject JObject, final String progressBarType, final InterfaceFastNetworking listener) {
        String access_token = "";
        List<ClsToken> dataToken = null;

        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();
        try {
            dataToken = new RepoclsToken(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access_token = dataToken.get(0).txtUserToken.toString();
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

    public void FNRequestDownloadAPKFile(final Activity ctx, String strLinkAPI, final String txtPathUserData, final String apkName, String tag, final Dialog dialog, final InterfaceFastNetworkingDownloadFile listener) {
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
                    }
                });
    }

    public void FNRequestUploadFotoProfile(final Context ctx, String strLinkAPI, final String mRequestBody, final Dialog dialog, final String tag, final ClsmUserLogin dtLogin, final InterfaceFastNetworkingUploadFile listener) {
        dialog.show();
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);
        byte[] b = dtLogin.getBlobImg();
        String path = new ClsHardCode().txtPathTemp;
        File fileFoto = new PickFile().decodeByteArraytoFile(b, path, "FotoProfileName.jpg");
        AndroidNetworking.upload(strLinkAPI)
                .addMultipartFile("image", fileFoto)
                .addMultipartParameter("txtParam", mRequestBody)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(final long bytesUploaded, final long totalBytes) {
                        // do anything with progress
                        listener.onProgress(bytesUploaded, totalBytes);
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

    public void ErrorHandlerFN(Context context, ANError error, String TAG) {
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        }
        new ToastCustom().showToasty(context, error.getErrorBody() + " " + error.getErrorDetail(), 4);
    }
}
