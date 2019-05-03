package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class ClsTChecker {
    @DatabaseField(id = true)
    private int idTransaksi;
    @DatabaseField
    private String txtNoSPM;
    @DatabaseField
    private Date startTime;
    @DatabaseField
    private Date excalTime;
    @DatabaseField
    private Date loadTime;

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getTxtNoSPM() {
        return txtNoSPM;
    }

    public void setTxtNoSPM(String txtNoSPM) {
        this.txtNoSPM = txtNoSPM;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExcalTime() {
        return excalTime;
    }

    public void setExcalTime(Date excalTime) {
        this.excalTime = excalTime;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }
}
