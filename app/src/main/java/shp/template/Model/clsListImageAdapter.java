package shp.template.Model;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class clsListImageAdapter implements Serializable{
    public String txtId;
    public String txtImgName;
    public String txtUrl;
    public byte[] blobImg;

    public String getTxtImgName() {
        return txtImgName;
    }

    public void setTxtImgName(String txtImgName) {
        this.txtImgName = txtImgName;
    }

    public byte[] getBlobImg() {
        return blobImg;
    }

    public void setBlobImg(byte[] blobImg) {
        this.blobImg = blobImg;
    }

    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }
}
