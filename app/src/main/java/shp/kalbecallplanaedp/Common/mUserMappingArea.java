package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

@DatabaseTable
public class mUserMappingArea implements Serializable {
    @DatabaseField(id = true, columnName = "intUserMappingAreaId")
    private int intUserMappingAreaId;
    @DatabaseField(columnName = "intUserId")
    private int intUserId;
    @DatabaseField(columnName = "txtKecamatanID")
    private String txtKecamatanID;

    public int getIntUserMappingAreaId() {
        return intUserMappingAreaId;
    }

    public void setIntUserMappingAreaId(int intUserMappingAreaId) {
        this.intUserMappingAreaId = intUserMappingAreaId;
    }

    public int getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(int intUserId) {
        this.intUserId = intUserId;
    }

    public String getTxtKecamatanID() {
        return txtKecamatanID;
    }

    public void setTxtKecamatanID(String txtKecamatanID) {
        this.txtKecamatanID = txtKecamatanID;
    }
}
