package shp.template.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

@DatabaseTable
public class tAkuisisiDetail implements Serializable {
    @DatabaseField(id = true, columnName = "txtDetailId")
    public String txtDetailId;
    @DatabaseField(columnName = "txtHeaderId")
    public String txtHeaderId;
    @DatabaseField(columnName = "txtImg", dataType = DataType.BYTE_ARRAY)
    public byte[] txtImg;
    @DatabaseField(columnName = "txtImgName")
    public String txtImgName;

    public String Property_txtDetailId = "txtDetailId";
    public String Property_intHeaderId = "txtHeaderId";
    public String Property_txtImg = "txtImg";
    public String Property_txtImgName = "txtImgName";
    public String Property_ListDataOftAkuisisiDetail = "ListDataOftAkuisisiDetail";



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

    public byte[] getTxtImg() {
        return txtImg;
    }

    public void setTxtImg(byte[] txtImg) {
        this.txtImg = txtImg;
    }

    public String getTxtImgName() {
        return txtImgName;
    }

    public void setTxtImgName(String txtImgName) {
        this.txtImgName = txtImgName;
    }
}
