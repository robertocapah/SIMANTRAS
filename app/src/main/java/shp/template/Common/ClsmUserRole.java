package shp.template.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

@DatabaseTable
public class ClsmUserRole implements Serializable {
    @DatabaseField(id = true, columnName = "txtId")
    public String txtId;
    @DatabaseField(columnName = "intRoleId")
    public int intRoleId;
    @DatabaseField(columnName = "txtRoleName")
    public String txtRoleName;

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public int getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(int intRoleId) {
        this.intRoleId = intRoleId;
    }

    public String getTxtRoleName() {
        return txtRoleName;
    }

    public void setTxtRoleName(String txtRoleName) {
        this.txtRoleName = txtRoleName;
    }
}
