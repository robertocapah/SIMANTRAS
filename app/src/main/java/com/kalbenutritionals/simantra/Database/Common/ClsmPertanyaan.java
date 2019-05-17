package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsmPertanyaan {
    @DatabaseField(id = true)
    private int intPertanyaanId;
    @DatabaseField
    private String txtPertanyaan;
    @DatabaseField
    private int intLocationDocsId;
    @DatabaseField
    private int intJenisPertanyaanId;
    @DatabaseField
    private int intValidateID;
    @DatabaseField
    private boolean bolHaveAnswer;
    @DatabaseField
    private boolean bolHavePhoto;
    @DatabaseField
    private int intPhotoNeeded;
    @DatabaseField
    private int intSeq;

    public static String TXTINTLOCATIONID = "intLocationDocsId";
    public static String TXTJENISPERTANYAAN = "intJenisPertanyaanId";
    public static String INTVALIDATEID = "intValidateID";


    public int getIntValidateID() {
        return intValidateID;
    }

    public void setIntValidateID(int intValidateID) {
        this.intValidateID = intValidateID;
    }

    public boolean isBolHavePhoto() {
        return bolHavePhoto;
    }

    public void setBolHavePhoto(boolean bolHavePhoto) {
        this.bolHavePhoto = bolHavePhoto;
    }
    public int getIntSeq() {
        return intSeq;
    }

    public void setIntSeq(int intSeq) {
        this.intSeq = intSeq;
    }

    public int getIntPhotoNeeded() {
        return intPhotoNeeded;
    }

    public void setIntPhotoNeeded(int intPhotoNeeded) {
        this.intPhotoNeeded = intPhotoNeeded;
    }

    public int getIntPertanyaanId() {
        return intPertanyaanId;
    }

    public void setIntPertanyaanId(int intPertanyaanId) {
        this.intPertanyaanId = intPertanyaanId;
    }

    public String getTxtPertanyaan() {
        return txtPertanyaan;
    }

    public void setTxtPertanyaan(String txtPertanyaan) {
        this.txtPertanyaan = txtPertanyaan;
    }

    public int getIntLocationDocsId() {
        return intLocationDocsId;
    }

    public void setIntLocationDocsId(int intLocationDocsId) {
        this.intLocationDocsId = intLocationDocsId;
    }

    public int getIntJenisPertanyaanId() {
        return intJenisPertanyaanId;
    }

    public void setIntJenisPertanyaanId(int intJenisPertanyaanId) {
        this.intJenisPertanyaanId = intJenisPertanyaanId;
    }

    public boolean isBolHaveAnswer() {
        return bolHaveAnswer;
    }

    public void setBolHaveAnswer(boolean bolHaveAnswer) {
        this.bolHaveAnswer = bolHaveAnswer;
    }
}
