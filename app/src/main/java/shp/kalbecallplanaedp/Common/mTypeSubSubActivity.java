package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

@DatabaseTable
public class mTypeSubSubActivity implements Serializable {
    @DatabaseField(id = true, columnName = "intTypeSubSubActivityId")
    public int intTypeSubSubActivityId;
    @DatabaseField(columnName = "txtName")
    public String txtName;
    @DatabaseField(columnName = "txtDesc")
    public String txtDesc;

    public int getIntTypeSubSubActivityId() {
        return intTypeSubSubActivityId;
    }

    public void setIntTypeSubSubActivityId(int intTypeSubSubActivityId) {
        this.intTypeSubSubActivityId = intTypeSubSubActivityId;
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
}
