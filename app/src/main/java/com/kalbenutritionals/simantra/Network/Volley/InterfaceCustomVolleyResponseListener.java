package com.kalbenutritionals.simantra.Network.Volley;

import android.app.ProgressDialog;

/**
 * Created by Dewi Oktaviani on 12/10/2018.
 */

public interface InterfaceCustomVolleyResponseListener {
    void onError(String message);

    void onResponse(String response, Boolean status, String strErrorMsg, ProgressDialog dialog);
}
