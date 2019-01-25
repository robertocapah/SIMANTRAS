package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

@DatabaseTable
public class tProgramVisit implements Serializable{
    @DatabaseField(id = true, columnName = "txtProgramVisitId")
    private String txtProgramVisitId;
    @DatabaseField(columnName = "intUserId")
    private int intUserId;
    @DatabaseField(columnName = "intRoleId")
    private int intRoleId;
    @DatabaseField(columnName = "txtNotes")
    private String txtNotes;
    @DatabaseField(columnName = "intType")
    private int intType;
    @DatabaseField(columnName = "intStatus")
    private int intStatus;
    @DatabaseField(columnName = "dtStart")
    private String dtStart;
    @DatabaseField(columnName = "dtEnd")
    private String dtEnd;
    @DatabaseField(columnName = "intFlagPush")
    private int intFlagPush;


    public String Property_txtProgramVisitId = "txtProgramVisitId";
    public String Property_intUserId = "intUserId";
    public String Property_intRoleId = "intRoleId";
    public String Property_txtNotes = "txtNotes";
    public String Property_intType = "intType";
    public String Property_intStatus = "intStatus";
    public String Property_dtStart = "dtStart";
    public String Property_dtEnd = "dtEnd";
    public String Property_dtLogin = "dtLogin";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_ListDataOftProgramVisit = "ListDataOftProgramVisit";

    public String getTxtProgramVisitId() {
        return txtProgramVisitId;
    }

    public void setTxtProgramVisitId(String txtProgramVisitId) {
        this.txtProgramVisitId = txtProgramVisitId;
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

    public String getTxtNotes() {
        return txtNotes;
    }

    public void setTxtNotes(String txtNotes) {
        this.txtNotes = txtNotes;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public String getDtStart() {
        return dtStart;
    }

    public void setDtStart(String dtStart) {
        this.dtStart = dtStart;
    }

    public String getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(String dtEnd) {
        this.dtEnd = dtEnd;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }
}
