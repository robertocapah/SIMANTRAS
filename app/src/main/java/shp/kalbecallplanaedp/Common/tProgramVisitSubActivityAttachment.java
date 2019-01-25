package shp.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

@DatabaseTable
public class tProgramVisitSubActivityAttachment implements Serializable {
    @DatabaseField(id = true, columnName = "txtProgramVisitSubActivityAttachmentId")
    private String txtProgramVisitSubActivityAttachmentId;
    @DatabaseField(columnName = "txtFileName")
    private String txtFileName;
    @DatabaseField(columnName = "blobFile", dataType = DataType.BYTE_ARRAY)
    private byte[] blobFile;
    @DatabaseField(columnName = "txtNoDocument")
    private String txtNoDocument;
    @DatabaseField(columnName = "txtProgramVisitSubActivityId")
    private String txtProgramVisitSubActivityId;
    @DatabaseField(columnName = "dtExpiredDate")
    private String dtExpiredDate;

    public String Property_txtProgramVisitSubActivityAttachmentId = "txtProgramVisitSubActivityAttachmentId";
    private String Property_txtFileName = "txtFileName";
    private String Property_blobFile =  "blobFile";
    private String Property_txtNoDocument = "txtNoDocument";
    private String Property_txtProgramVisitSubActivityId = "txtProgramVisitSubActivityId";
    private String Property_dtExpiredDate = "dtExpiredDate";
    public String Property_ListOfDatatProgramVisitSubActivityAttachment = "ListOfDatatProgramVisitSubActivityAttachment";

    public String getTxtProgramVisitSubActivityAttachmentId() {
        return txtProgramVisitSubActivityAttachmentId;
    }

    public void setTxtProgramVisitSubActivityAttachmentId(String txtProgramVisitSubActivityAttachmentId) {
        this.txtProgramVisitSubActivityAttachmentId = txtProgramVisitSubActivityAttachmentId;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public String getTxtNoDocument() {
        return txtNoDocument;
    }

    public void setTxtNoDocument(String txtNoDocument) {
        this.txtNoDocument = txtNoDocument;
    }

    public String getTxtProgramVisitSubActivityId() {
        return txtProgramVisitSubActivityId;
    }

    public void setTxtProgramVisitSubActivityId(String txtProgramVisitSubActivityId) {
        this.txtProgramVisitSubActivityId = txtProgramVisitSubActivityId;
    }

    public String getDtExpiredDate() {
        return dtExpiredDate;
    }

    public void setDtExpiredDate(String dtExpiredDate) {
        this.dtExpiredDate = dtExpiredDate;
    }

    public byte[] getBlobFile() {
        return blobFile;
    }

    public void setBlobFile(byte[] blobFile) {
        this.blobFile = blobFile;
    }
}
