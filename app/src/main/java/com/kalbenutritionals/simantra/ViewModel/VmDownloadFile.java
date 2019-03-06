package com.kalbenutritionals.simantra.ViewModel;

/**
 * Created by Roberto on 14/11/2018.
 */

public class VmDownloadFile {
    private String link;
    private String groupDownload;
    private String txtId;
    private int index;
    private byte[] blobFile;

    public VmDownloadFile(){

    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGroupDownload() {
        return groupDownload;
    }

    public void setGroupDownload(String groupDownload) {
        this.groupDownload = groupDownload;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getBlobFile() {
        return blobFile;
    }

    public void setBlobFile(byte[] blobFile) {
        this.blobFile = blobFile;
    }
}
