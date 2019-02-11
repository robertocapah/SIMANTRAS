package shp.template.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 11/15/2018.
 */

public class mFileAttachment implements Serializable {
    @DatabaseField(id = true)
    private int intFileAttachmentId;
    @DatabaseField
    private int intSubDetailActivityId;
    @DatabaseField
    private String txtFileName;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] blobFile;
    @DatabaseField
    private String Description;

    public String Property_intSubDetailActivityId = "intSubDetailActivityId";
    public String Property_txtFileName = "txtFileName";
    public String Property_blobFile = "blobFile";
    public String Property_intFileAttachmentId = "intFileAttachmentId";
    public String Property_Description = "Description";

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

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public byte[] getBlobFile() {
        return blobFile;
    }

    public void setBlobFile(byte[] blobFile) {
        this.blobFile = blobFile;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
