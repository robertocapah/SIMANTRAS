package com.kalbenutritionals.simantra.Network.FastNetworking;

import com.androidnetworking.error.ANError;

public interface InterfaceFastNetworkingDownloadFile {
    void onProgress(final long bytesDownloaded, final long totalBytes);
    void onDownloadComplete();
    void onError(ANError error);
}
