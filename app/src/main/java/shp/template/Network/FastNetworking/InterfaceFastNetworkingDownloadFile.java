package shp.template.Network.FastNetworking;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

public interface InterfaceFastNetworkingDownloadFile {
    void onProgress(final long bytesDownloaded, final long totalBytes);
    void onDownloadComplete();
    void onError(ANError error);
}
