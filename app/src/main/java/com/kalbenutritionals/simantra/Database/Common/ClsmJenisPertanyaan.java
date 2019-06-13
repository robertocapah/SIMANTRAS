package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsmJenisPertanyaan {
    @DatabaseField(id = true)
    private String intJenisPertanyaanId;
    @DatabaseField
    private String txtJenisPertanyaan;
    @DatabaseField
    private String txtDescription;
    @DatabaseField
    private boolean bolHaveAnswer;
    @DatabaseField
    private boolean bolActive;

    public String getIntjenisPertanyaanId() {
        return intJenisPertanyaanId;
    }

    public void setIntJenisPertanyaanId(String intJenisPertanyaanId) {
        this.intJenisPertanyaanId = intJenisPertanyaanId;
    }

    public String getTxtJenisPertanyaan() {
        return txtJenisPertanyaan;
    }

    public void setTxtJenisPertanyaan(String txtJenisPertanyaan) {
        this.txtJenisPertanyaan = txtJenisPertanyaan;
    }

    public String getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription = txtDescription;
    }

    public boolean isBolHaveAnswer() {
        return bolHaveAnswer;
    }

    public void setBolHaveAnswer(boolean bolHaveAnswer) {
        this.bolHaveAnswer = bolHaveAnswer;
    }

    public boolean isBolActive() {
        return bolActive;
    }

    public void setBolActive(boolean bolActive) {
        this.bolActive = bolActive;
    }
}
