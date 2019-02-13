package shp.template.Network.FastNetworking;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
                        writeErrorLog(error);
                        listener.onError(error);
                    }
                });
    }
    public void FNRequestPostData(final Context activity,JSONObject JObject, String progressBarType, final InterfaceFastNetworking listener){
        String access_token = "";
        //        List<clsToken> dataToken = new clsTokenRepo(context).findAll();
//        access_token = dataToken.get(0).txtUserToken.toString();
        AndroidNetworking.post("http://aedp.kalbenutritionals.web.id/api/api/downloadtAkuisisi")
                .addJSONObjectBody(JObject)
                .addHeaders("Authorization", access_token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int a = 2;
                    }

                    @Override
                    public void onError(ANError error) {
                        writeErrorLog(error);
                    }
                });
    }
    public void writeErrorLog(ANError error){
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        }
    }
}
