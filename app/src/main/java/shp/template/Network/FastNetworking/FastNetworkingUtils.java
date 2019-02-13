package shp.template.Network.FastNetworking;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robert on 2/12/2019.
 */

public class FastNetworkingUtils {
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
                        // handle error
                        listener.onError(error);
                    }
                });
    }
}
