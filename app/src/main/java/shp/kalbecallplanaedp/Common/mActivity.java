package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

@DatabaseTable
public class mActivity implements Serializable {
    @DatabaseField(id = true, columnName = "intActivityId")
    public int intActivityId;
    @DatabaseField(columnName = "txtName")
    public String txtName;
    @DatabaseField(columnName = "txtDesc")
    public String txtDesc;

    public int getIntActivityId() {
        return intActivityId;
    }

    public void setIntActivityId(int intActivityId) {
        this.intActivityId = intActivityId;
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
