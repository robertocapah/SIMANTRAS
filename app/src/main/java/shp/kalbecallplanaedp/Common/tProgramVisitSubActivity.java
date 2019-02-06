package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

@DatabaseTable
public class tProgramVisitSubActivity {
    @DatabaseField(id = true, columnName = "txtProgramVisitSubActivityId")
    private String txtProgramVisitSubActivityId;
    @DatabaseField(columnName = "txtApotekName")
    private String txtApotekName;
    @DatabaseField(columnName = "intType")
    private int intType;
    @DatabaseField(columnName = "txtAreaName")
    private String txtAreaName;
    @DatabaseField(columnName = "txtDokterId")
    private String txtDokterId;
    @DatabaseField(columnName = "txtNotes")
    private String txtNotes;
    @DatabaseField(columnName = "txtDokterName")
    private String txtDokterName;
    @DatabaseField(columnName = "txtProgramVisitId")
    private String txtProgramVisitId;
    @DatabaseField(columnName = "txtApotekId")
    private String txtApotekId;
    @DatabaseField(columnName = "intActivityId")
    private int intActivityId;
    @DatabaseField(columnName = "intSubActivityId")
    private int intSubActivityId;
    @DatabaseField(columnName = "txtAreaId")
    private String txtAreaId;
    @DatabaseField(columnName = "dtStart")
    private String dtStart;
    @DatabaseField(columnName = "dtEnd")
    private String dtEnd;
    @DatabaseField
    private int intFlagPush;
    @DatabaseField
    private boolean bitNew;

    public String Property_txtProgramVisitSubActivityId = "txtProgramVisitSubActivityId";
    public String Property_txtApotekName = "txtApotekName";
    public String Property_intType = "intType";
    public String Property_txtAreaName = "txtAreaName";
    public String Property_txtDokterId = "txtDokterId";
    public String Property_txtNotes = "txtNotes";
    public String Property_txtDokterName = "txtDokterName";
    public String Property_txtProgramVisitId = "txtProgramVisitId";
    public String Property_txtApotekId = "txtApotekId";
    public String Property_intActivityId = "intActivityId";
    public String Property_intSubActivityId = "intSubActivityId";
    public String Property_txtAreaId = "txtAreaId";
    public String Property_dtStart = "dtStart";
    public String Property_dtEnd = "dtEnd";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_bitNew = "bitNew";

    public String Property_ListOfDatatProgramVisitSubActivity = "ListOfDatatProgramVisitSubActivity";

    public String getTxtProgramVisitSubActivityId() {
        return txtProgramVisitSubActivityId;
    }

    public void setTxtProgramVisitSubActivityId(String txtProgramVisitSubActivityId) {
        this.txtProgramVisitSubActivityId = txtProgramVisitSubActivityId;
    }

    public String getTxtApotekName() {
        return txtApotekName;
    }

    public void setTxtApotekName(String txtApotekName) {
        this.txtApotekName = txtApotekName;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public String getTxtAreaName() {
        return txtAreaName;
    }

    public void setTxtAreaName(String txtAreaName) {
        this.txtAreaName = txtAreaName;
    }

    public String getTxtDokterId() {
        return txtDokterId;
    }

    public void setTxtDokterId(String txtDokterId) {
        this.txtDokterId = txtDokterId;
    }

    public String getTxtNotes() {
        return txtNotes;
    }

    public void setTxtNotes(String txtNotes) {
        this.txtNotes = txtNotes;
    }

    public String getTxtDokterName() {
        return txtDokterName;
    }

    public void setTxtDokterName(String txtDokterName) {
        this.txtDokterName = txtDokterName;
    }

    public String getTxtProgramVisitId() {
        return txtProgramVisitId;
    }

    public void setTxtProgramVisitId(String txtProgramVisitId) {
        this.txtProgramVisitId = txtProgramVisitId;
    }

    public String getTxtApotekId() {
        return txtApotekId;
    }

    public void setTxtApotekId(String txtApotekId) {
        this.txtApotekId = txtApotekId;
    }

    public int getIntActivityId() {
        return intActivityId;
    }

    public void setIntActivityId(int intActivityId) {
        this.intActivityId = intActivityId;
    }

    public int getIntSubActivityId() {
        return intSubActivityId;
    }

    public void setIntSubActivityId(int intSubActivityId) {
        this.intSubActivityId = intSubActivityId;
    }

    public String getTxtAreaId() {
        return txtAreaId;
    }

    public void setTxtAreaId(String txtAreaId) {
        this.txtAreaId = txtAreaId;
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

    public boolean isBitNew() {
        return bitNew;
    }

    public void setBitNew(boolean bitNew) {
        this.bitNew = bitNew;
    }
}
