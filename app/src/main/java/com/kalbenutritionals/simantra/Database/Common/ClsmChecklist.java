package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsmChecklist {
    @DatabaseField(generatedId = true)
    private int intId;
    @DatabaseField
    private String pertanyaan;
    @DatabaseField
    private int intBitActive;

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public int getIntBitActive() {
        return intBitActive;
    }

    public void setIntBitActive(int intBitActive) {
        this.intBitActive = intBitActive;
    }
}
