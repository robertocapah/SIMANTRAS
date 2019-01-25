package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

@DatabaseTable
public class tNotification implements Serializable {
    @DatabaseField(id = true)
    private String intHeaderAkuisisiId;
    @DatabaseField
    private int intActivityId;
    @DatabaseField
    private String intDokterId;
    @DatabaseField
    private String intApotekId;
    @DatabaseField
    private String dtExpired;
    @DatabaseField
    private int intSubDetailActivityId;
    @DatabaseField
    private String txtNoDoc;

    public String Property_intDokterId = "intDokterId";
    public String Property_intApotekId = "intApotekId";
    public String Property_intHeaderAkuisisiId = "intHeaderAkuisisiId";
    public String Proporty_intActivityId= "intActivityId";

    public String getIntHeaderAkuisisiId() {
        return intHeaderAkuisisiId;
    }

    public void setIntHeaderAkuisisiId(String intHeaderAkuisisiId) {
        this.intHeaderAkuisisiId = intHeaderAkuisisiId;
    }

    public int getIntActivityId() {
        return intActivityId;
    }

    public void setIntActivityId(int intActivityId) {
        this.intActivityId = intActivityId;
    }

    public String getIntDokterId() {
        return intDokterId;
    }

    public void setIntDokterId(String intDokterId) {
        this.intDokterId = intDokterId;
    }

    public String getIntApotekId() {
        return intApotekId;
    }

    public void setIntApotekId(String intApotekId) {
        this.intApotekId = intApotekId;
    }

    public String getDtExpired() {
        return dtExpired;
    }

    public void setDtExpired(String dtExpired) {
        this.dtExpired = dtExpired;
    }

    public int getIntSubDetailActivityId() {
        return intSubDetailActivityId;
    }

    public void setIntSubDetailActivityId(int intSubDetailActivityId) {
        this.intSubDetailActivityId = intSubDetailActivityId;
    }

    public String getTxtNoDoc() {
        return txtNoDoc;
    }

    public void setTxtNoDoc(String txtNoDoc) {
        this.txtNoDoc = txtNoDoc;
    }
}
