package shp.template.Network.FastNetworking;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

/**
 * Created by Robert on 2/13/2019.
 */

public interface InterfaceFastNetworking {
    void onResponse(JSONObject response);
    void onError(ANError error);
}
