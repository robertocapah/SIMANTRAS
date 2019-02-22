package shp.template.Network.FastNetworking;


import com.androidnetworking.error.ANError;

import org.json.JSONObject;

public interface InterfaceFastNetworkingUploadFile {
    void onProgress(final long bytesDownloaded, final long totalBytes);
    void onResponse(JSONObject response);
    void onError(ANError error);
}
