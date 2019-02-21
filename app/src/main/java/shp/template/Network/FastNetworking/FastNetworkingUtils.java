package shp.template.Network.FastNetworking;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

import shp.template.Database.Common.ClsToken;
import shp.template.Database.Repo.RepoclsToken;

/**
 * Created by Robert on 2/12/2019.
 */

public class FastNetworkingUtils {
    String TAG = "FastNetworking";
    public void FNRequestToken(final Context activity, String txtLink, final String username,  final String clientId, String progressBarType, final InterfaceFastNetworking listener ){

        AndroidNetworking.post(txtLink)
                .addBodyParameter("grant_type","password")
                .addBodyParameter("username",username)
                .addBodyParameter("password","")
                .addBodyParameter("client_id",clientId)
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
                        ErrorHandlerFN(activity,error,TAG);
                        listener.onError(error);
                    }
                });
    }
    public void FNRequestPostData(final Context context, String txtLink, JSONObject JObject, final String progressBarType, final InterfaceFastNetworking listener){
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
                .addHeaders("Authorization","Bearer " + access_token)
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
                        ErrorHandlerFN(context,error,TAG);
                    }
                });
    }
    public void ErrorHandlerFN(Context context,ANError error, String TAG){
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        }
        new ToastCustom().showToasty(context,error.getErrorBody()+" "+error.getErrorDetail(),4);
    }
}
