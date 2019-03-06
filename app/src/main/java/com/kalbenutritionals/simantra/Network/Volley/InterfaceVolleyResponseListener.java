package com.kalbenutritionals.simantra.Network.Volley;

/**
 * Created by arick.anjasmara on 22/06/2017.
 */

public interface InterfaceVolleyResponseListener {
    void onError(String message);
    void onResponse(String response, Boolean status, String strErrorMsg);
}
