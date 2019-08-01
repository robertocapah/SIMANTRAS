package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Roberto on 7/11/2019
 */
@Generated("com.robohorse.robopojogenerator")
public class DataVehichleFixImage {
    @SerializedName("INT_IMG_ID")
    public int INT_IMG_ID;
    @SerializedName("INT_VHCL_MNT_ID")
    public int INT_VHCL_MNT_ID ;
    @SerializedName("TXT_FILENAME")
    public String TXT_FILENAME;
    @SerializedName("TXT_PATH")
    public String TXT_PATH;

    public int getINT_IMG_ID() {
        return INT_IMG_ID;
    }

    public void setINT_IMG_ID(int INT_IMG_ID) {
        this.INT_IMG_ID = INT_IMG_ID;
    }

    public int getINT_VHCL_MNT_ID() {
        return INT_VHCL_MNT_ID;
    }

    public void setINT_VHCL_MNT_ID(int INT_VHCL_MNT_ID) {
        this.INT_VHCL_MNT_ID = INT_VHCL_MNT_ID;
    }

    public String getTXT_FILENAME() {
        return TXT_FILENAME;
    }

    public void setTXT_FILENAME(String TXT_FILENAME) {
        this.TXT_FILENAME = TXT_FILENAME;
    }

    public String getTXT_PATH() {
        return TXT_PATH;
    }

    public void setTXT_PATH(String TXT_PATH) {
        this.TXT_PATH = TXT_PATH;
    }
}
