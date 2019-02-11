package shp.template.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

@DatabaseTable
public class tMaintenanceHeader implements Serializable {
    @DatabaseField(id = true)
    private String txtHeaderId;
    @DatabaseField
    private String txtRealisasiVisitId;
    @DatabaseField
    private int intActivityId;
    @DatabaseField
    private int intUserId;
    @DatabaseField
    private int intRoleId;
    @DatabaseField
    private String intDokterId;
    @DatabaseField
    private String intApotekID;
    @DatabaseField
    private String intAreaId;
    @DatabaseField
    private int intFlagPush;


    public String Property_txtHeaderId = "txtHeaderId";
    public String Property_txtRealisasiVisitId = "txtRealisasiVisitId";
    public String Property_intActivityId = "intActivityId";
    public String Property_intUserId = "intUserId";
    public String Property_intRoleId = "intRoleId";
    public String Property_intDokterId = "intDokterId";
    public String Property_intApotekID = "intApotekID";
    public String Property_intAreaId = "intAreaId";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_ListOfDatatMaintenanceHeader = "ListOfDatatMaintenanceHeader";


    public String getTxtHeaderId() {
        return txtHeaderId;
    }

    public void setTxtHeaderId(String txtHeaderId) {
        this.txtHeaderId = txtHeaderId;
    }

    public String getTxtRealisasiVisitId() {
        return txtRealisasiVisitId;
    }

    public void setTxtRealisasiVisitId(String txtRealisasiVisitId) {
        this.txtRealisasiVisitId = txtRealisasiVisitId;
    }

    public int getIntActivityId() {
        return intActivityId;
    }

    public void setIntActivityId(int intActivityId) {
        this.intActivityId = intActivityId;
    }

    public int getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(int intUserId) {
        this.intUserId = intUserId;
    }

    public int getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(int intRoleId) {
        this.intRoleId = intRoleId;
    }

    public String getIntDokterId() {
        return intDokterId;
    }

    public void setIntDokterId(String intDokterId) {
        this.intDokterId = intDokterId;
    }

    public String getIntApotekID() {
        return intApotekID;
    }

    public void setIntApotekID(String intApotekID) {
        this.intApotekID = intApotekID;
    }

    public String getIntAreaId() {
        return intAreaId;
    }

    public void setIntAreaId(String intAreaId) {
        this.intAreaId = intAreaId;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }
}
