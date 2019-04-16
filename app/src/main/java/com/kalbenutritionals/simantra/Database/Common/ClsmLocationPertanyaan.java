package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsmLocationPertanyaan {
    @DatabaseField(id = true)
    private int mLocationDocsId;
    @DatabaseField
    private String txtName;
    @DatabaseField
    private String txtDesc;
    @DatabaseField
    private boolean bolActive;

    public int getmLocationDocsId() {
        return mLocationDocsId;
    }

    public void setmLocationDocsId(int mLocationDocsId) {
        this.mLocationDocsId = mLocationDocsId;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public boolean isBolActive() {
        return bolActive;
    }

    public void setBolActive(boolean bolActive) {
        this.bolActive = bolActive;
    }
}
