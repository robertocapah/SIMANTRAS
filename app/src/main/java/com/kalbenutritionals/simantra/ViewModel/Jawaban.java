package com.kalbenutritionals.simantra.ViewModel;

public class Jawaban {
    public String jawaban;
    public String idJawaban;
    public  int idPertanyaan;
    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban, String idJawaban, int idPertanyaan) {
        this.jawaban = jawaban;
        this.idJawaban = idJawaban;
        this.idPertanyaan = idPertanyaan;

    }
}
