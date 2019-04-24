package com.kalbenutritionals.simantra.ViewModel;

public class Jawaban {
    public String jawaban;
    public int idJawaban;
    public  int idPertanyaan;
    public boolean bitChoosen;
    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban, int idJawaban, int idPertanyaan, boolean bitChoosen) {
        this.jawaban = jawaban;
        this.idJawaban = idJawaban;
        this.idPertanyaan = idPertanyaan;
        this.bitChoosen = bitChoosen;
    }
}
