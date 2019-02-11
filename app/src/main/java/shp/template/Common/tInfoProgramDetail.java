package shp.template.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

@DatabaseTable
public class tInfoProgramDetail implements Serializable {
    @DatabaseField(id = true)
    private String txtDetailId;
    @DatabaseField
    private String txtHeaderId;
    @DatabaseField
    private int intSubDetailActivityId;
    @DatabaseField
    private int intFileAttachmentId;
    @DatabaseField
    private boolean boolFlagChecklist;
    @DatabaseField
    private String dtChecklist;
    @DatabaseField
    private int intFlagPush;


    public String Property_txtDetailId = "txtDetailId";
    public String Property_txtHeaderId = "txtHeaderId";
    public String Property_intFlagChecklist = "intFlagChecklist";
    public String Property_boolFlagChecklist = "boolFlagChecklist";
    public String Property_dtChecklist = "dtChecklist";
    public String Property_intFileAttachmentId = "intFileAttachmentId";
    public String Property_intSubDetailActivityId = "intSubDetailActivityId";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_ListOfDatatInfoProgramDetail = "ListOfDatatInfoProgramDetail";

    public String getTxtDetailId() {
        return txtDetailId;
    }

    public void setTxtDetailId(String txtDetailId) {
        this.txtDetailId = txtDetailId;
    }

    public String getTxtHeaderId() {
        return txtHeaderId;
    }

    public void setTxtHeaderId(String txtHeaderId) {
        this.txtHeaderId = txtHeaderId;
    }

    public boolean isBoolFlagChecklist() {
        return boolFlagChecklist;
    }

    public void setBoolFlagChecklist(boolean boolFlagChecklist) {
        this.boolFlagChecklist = boolFlagChecklist;
    }

    public String getDtChecklist() {
        return dtChecklist;
    }

    public void setDtChecklist(String dtChecklist) {
        this.dtChecklist = dtChecklist;
    }

    public int getIntFileAttachmentId() {
        return intFileAttachmentId;
    }

    public void setIntFileAttachmentId(int intFileAttachmentId) {
        this.intFileAttachmentId = intFileAttachmentId;
    }

    public int getIntSubDetailActivityId() {
        return intSubDetailActivityId;
    }

    public void setIntSubDetailActivityId(int intSubDetailActivityId) {
        this.intSubDetailActivityId = intSubDetailActivityId;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }
}
