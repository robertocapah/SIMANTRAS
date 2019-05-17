package com.kalbenutritionals.simantra.Service;

import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.ViewModel.DeviceInfo;
import com.kalbenutritionals.simantra.ViewModel.UserRequest;
import com.kalbenutritionals.simantra.ViewModel.VMRequestData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dewi.oktaviani on 27/03/2019.
 */

public class MyFirebaseInstanceService extends FirebaseInstanceIdService {
    private static final String TAG="MyFirebaseInstanceServi";
    public MyFirebaseInstanceService() {
        super();
    }

//    @Override
//    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
//    }

    @Override
    public void onTokenRefresh() {
//        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        FirebaseMessaging.getInstance().subscribeToTopic("all");
        new BLHelper().updateTokenFirebase(getApplicationContext());
/* If you want to send messages to this application instance or manage this apps subscriptions on the server side, send the Instance ID token to your app server.*/

        sendRegistrationToServer(refreshedToken);
    }



    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN ", refreshedToken.toString());
    }


}

