package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsmJawaban {
    @DatabaseField(id = true)
    private int idJawaban;
    @DatabaseField
    private int idPertanyaan;
    @DatabaseField
    private String txtJawaban;
    @DatabaseField
    private boolean bitChoosen;
    @DatabaseField
    private boolean bitActive;

    public boolean isBitChoosen() {
        return bitChoosen;
    }

    public void setBitChoosen(boolean bitChoosen) {
        this.bitChoosen = bitChoosen;
    }

    public boolean isBitActive() {
        return bitActive;
    }

    public void setBitActive(boolean bitActive) {
        this.bitActive = bitActive;
    }

    public static String ID_PERTANYAAN = "idPertanyaan";

    public int getIdJawaban() {
        return idJawaban;
    }

    public void setIdJawaban(int idJawaban) {
        this.idJawaban = idJawaban;
    }

    public int getIdPertanyaan() {
        return idPertanyaan;
    }

    public void setIdPertanyaan(int idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public String getTxtJawaban() {
        return txtJawaban;
    }

    public void setTxtJawaban(String txtJawaban) {
        this.txtJawaban = txtJawaban;
    }

}
