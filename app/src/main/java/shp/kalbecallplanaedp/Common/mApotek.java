package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/18/2018.
 */
@DatabaseTable
public class mApotek implements Serializable{
    @DatabaseField(id = true, columnName = "txtCode")
    private String txtCode;
    @DatabaseField(columnName ="txtName")
    private String txtName;
    @DatabaseField(columnName = "txtKecId")
    private String txtKecId;
    @DatabaseField(columnName = "txtKecName")
    private String txtKecName;

    public String Property_txtCode = "txtCode";

    public String getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(String txtCode) {
        this.txtCode = txtCode;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtKecId() {
        return txtKecId;
    }

    public void setTxtKecId(String txtKecId) {
        this.txtKecId = txtKecId;
    }

    public String getTxtKecName() {
        return txtKecName;
    }

    public void setTxtKecName(String txtKecName) {
        this.txtKecName = txtKecName;
    }
}
