package com.kalbenutritionals.simantra.ViewModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by dewi.oktaviani on 25/04/2019.
 */
public class VmTJawabanUser {
    private String txtTransJawabanId;
    private String txtNoSPM;
    private int intPertanyaanId;
    private int intmJawabanId;
    private int intTypePertanyaanId;
    private String txtJawaban;
    private String txtPathImage;
    private List<String> listPathImage;
    private boolean bolHavePhoto;
    private boolean bolHaveAnswer;

    public String getTxtTransJawabanId() {
        return txtTransJawabanId;
    }

    public void setTxtTransJawabanId(String txtTransJawabanId) {
        this.txtTransJawabanId = txtTransJawabanId;
    }

    public String getTxtNoSPM() {
        return txtNoSPM;
    }

    public void setTxtNoSPM(String txtNoSPM) {
        this.txtNoSPM = txtNoSPM;
    }

    public int getIntPertanyaanId() {
        return intPertanyaanId;
    }

    public void setIntPertanyaanId(int intPertanyaanId) {
        this.intPertanyaanId = intPertanyaanId;
    }

    public int getIntmJawabanId() {
        return intmJawabanId;
    }

    public void setIntmJawabanId(int intmJawabanId) {
        this.intmJawabanId = intmJawabanId;
    }

    public int getIntTypePertanyaanId() {
        return intTypePertanyaanId;
    }

    public void setIntTypePertanyaanId(int intTypePertanyaanId) {
        this.intTypePertanyaanId = intTypePertanyaanId;
    }

    public String getTxtJawaban() {
        return txtJawaban;
    }

    public void setTxtJawaban(String txtJawaban) {
        this.txtJawaban = txtJawaban;
    }

    public String getTxtPathImage() {
        return txtPathImage;
    }

    public void setTxtPathImage(String txtPathImage) {
        this.txtPathImage = txtPathImage;
    }

    public boolean isBolHavePhoto() {
        return bolHavePhoto;
    }

    public void setBolHavePhoto(boolean bolHavePhoto) {
        this.bolHavePhoto = bolHavePhoto;
    }

    public boolean isBolHaveAnswer() {
        return bolHaveAnswer;
    }

    public void setBolHaveAnswer(boolean bolHaveAnswer) {
        this.bolHaveAnswer = bolHaveAnswer;
    }

    public List<String> getListPathImage() {
        return listPathImage;
    }

    public void setListPathImage(List<String> listPathImage) {
        this.listPathImage = listPathImage;
    }
}
