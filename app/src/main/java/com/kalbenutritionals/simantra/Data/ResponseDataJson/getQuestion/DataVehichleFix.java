package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Roberto on 7/11/2019
 */
@Generated("com.robohorse.robopojogenerator")
public class DataVehichleFix {
    @SerializedName("INT_VHCL_MNT_ID")
    public int INT_VHCL_MNT_ID;
    @SerializedName("INT_VHCL_ISSUE_ID")
    public int INT_VHCL_ISSUE_ID;
    @SerializedName("TXT_REASON")
    public String TXT_REASON ;
    @SerializedName("TXT_DESCRIPTION")
    public String TXT_DESCRIPTION;
    @SerializedName("TXT_STATUS")
    public String TXT_STATUS;
    @SerializedName("BIT_CLOSED")
    public String BIT_CLOSED;
    @SerializedName("listDatVehichleFixImage")
    public List<DataVehichleFixImage> listDatVehichleFixImage;

    public int getINT_VHCL_MNT_ID() {
        return INT_VHCL_MNT_ID;
    }

    public void setINT_VHCL_MNT_ID(int INT_VHCL_MNT_ID) {
        this.INT_VHCL_MNT_ID = INT_VHCL_MNT_ID;
    }

    public int getINT_VHCL_ISSUE_ID() {
        return INT_VHCL_ISSUE_ID;
    }

    public void setINT_VHCL_ISSUE_ID(int INT_VHCL_ISSUE_ID) {
        this.INT_VHCL_ISSUE_ID = INT_VHCL_ISSUE_ID;
    }

    public String getTXT_REASON() {
        return TXT_REASON;
    }

    public void setTXT_REASON(String TXT_REASON) {
        this.TXT_REASON = TXT_REASON;
    }

    public String getTXT_DESCRIPTION() {
        return TXT_DESCRIPTION;
    }

    public void setTXT_DESCRIPTION(String TXT_DESCRIPTION) {
        this.TXT_DESCRIPTION = TXT_DESCRIPTION;
    }

    public String getTXT_STATUS() {
        return TXT_STATUS;
    }

    public void setTXT_STATUS(String TXT_STATUS) {
        this.TXT_STATUS = TXT_STATUS;
    }

    public String getBIT_CLOSED() {
        return BIT_CLOSED;
    }

    public void setBIT_CLOSED(String BIT_CLOSED) {
        this.BIT_CLOSED = BIT_CLOSED;
    }

    public List<DataVehichleFixImage> getListDatVehichleFixImage() {
        return listDatVehichleFixImage;
    }

    public void setListDatVehichleFixImage(List<DataVehichleFixImage> listDatVehichleFixImage) {
        this.listDatVehichleFixImage = listDatVehichleFixImage;
    }
}
