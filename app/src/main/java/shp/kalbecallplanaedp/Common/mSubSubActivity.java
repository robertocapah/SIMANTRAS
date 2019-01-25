package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

@DatabaseTable
public class mSubSubActivity implements Serializable {
    @DatabaseField(id = true,columnName = "intSubSubActivityid")
    private int intSubSubActivityid;
    @DatabaseField(columnName = "intSubActivityid")
    private int intSubActivityid;
    @DatabaseField(columnName = "txtName")
    private String txtName;
    @DatabaseField(columnName = "txtDesc")
    private String txtDesc;
    @DatabaseField(columnName = "intType")
    private int intType;

    public String Property_intSubActivityid = "intSubActivityid";
    public String Property_intSubSubActivityid = "intSubSubActivityid";
    public String Property_intType = "intType";

    public int getIntSubSubActivityid() {
        return intSubSubActivityid;
    }

    public void setIntSubSubActivityid(int intSubSubActivityid) {
        this.intSubSubActivityid = intSubSubActivityid;
    }

    public int getIntSubActivityid() {
        return intSubActivityid;
    }

    public void setIntSubActivityid(int intSubActivityid) {
        this.intSubActivityid = intSubActivityid;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }
}
