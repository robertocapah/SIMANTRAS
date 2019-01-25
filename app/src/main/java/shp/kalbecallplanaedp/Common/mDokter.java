package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/18/2018.
 */

@DatabaseTable
public class mDokter implements Serializable {
    @DatabaseField(id = true, columnName = "txtId")
    private String txtId;
    @DatabaseField(columnName = "txtFirstName")
    private String txtFirstName;
    @DatabaseField(columnName = "txtLastName")
    private String txtLastName;
    @DatabaseField(columnName = "txtGender")
    private String txtGender;
    @DatabaseField(columnName = "txtType")
    private String txtType;
    @DatabaseField(columnName = "txtSpecialist")
    private String txtSpecialist;

    public String Property_txtId = "txtId";

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getTxtFirstName() {
        return txtFirstName;
    }

    public void setTxtFirstName(String txtFirstName) {
        this.txtFirstName = txtFirstName;
    }

    public String getTxtLastName() {
        return txtLastName;
    }

    public void setTxtLastName(String txtLastName) {
        this.txtLastName = txtLastName;
    }

    public String getTxtGender() {
        return txtGender;
    }

    public void setTxtGender(String txtGender) {
        this.txtGender = txtGender;
    }

    public String getTxtType() {
        return txtType;
    }

    public void setTxtType(String txtType) {
        this.txtType = txtType;
    }

    public String getTxtSpecialist() {
        return txtSpecialist;
    }

    public void setTxtSpecialist(String txtSpecialist) {
        this.txtSpecialist = txtSpecialist;
    }
}
