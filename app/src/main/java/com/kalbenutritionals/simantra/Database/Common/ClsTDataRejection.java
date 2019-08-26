package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Roberto on 7/11/2019
 */
@DatabaseTable
public class ClsTDataRejection implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int intFillDtlId;
    @DatabaseField
    private int intValueId;
    @DatabaseField
    private String txtPertanyaan;
    @DatabaseField
    private String txtJawaban;
    @DatabaseField
    private String txtReason;
    @DatabaseField
    private String txtIssueReason;

    public String getTxtIssueReason() {
        return txtIssueReason;
    }

    public void setTxtIssueReason(String txtIssueReason) {
        this.txtIssueReason = txtIssueReason;
    }

    public String getTxtReason() {
        return txtReason;
    }

    public void setTxtReason(String txtReason) {
        this.txtReason = txtReason;
    }

    public int getIntFillDtlId() {
        return intFillDtlId;
    }

    public void setIntFillDtlId(int intFillDtlId) {
        this.intFillDtlId = intFillDtlId;
    }

    public int getId() {
        return id;
    }

    public int getIntValueId() {
        return intValueId;
    }

    public void setIntValueId(int intValueId) {
        this.intValueId = intValueId;
    }

    public String getTxtPertanyaan() {
        return txtPertanyaan;
    }

    public void setTxtPertanyaan(String txtPertanyaan) {
        this.txtPertanyaan = txtPertanyaan;
    }

    public String getTxtJawaban() {
        return txtJawaban;
    }

    public void setTxtJawaban(String txtJawaban) {
        this.txtJawaban = txtJawaban;
    }
}
