package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.List;

public class VmListItemAdapterPertanyaan {
    public int id;
    public int image;
    public String txtPertanyaan;
    public boolean expanded = false;
    public boolean parent = false;
    public int jenisPertanyaan;
    public boolean bitImage;
    public Bitmap bitmap;
    public Uri path;
    public int countImage;
    public List<VmImageContainer> listImage;
    public boolean bolHaveAnswer;
    public boolean bitValid;
    public String message;
    public List<Jawaban> jawabans;
    public int intValidateId;
    public int intPositionId;
    // flag when item swiped
    public boolean swiped = false;

    public VmListItemAdapterPertanyaan() {
    }

    public VmListItemAdapterPertanyaan(int image, String txtPertanyaan, int id, int jenisPertanyaan, boolean bitImage, List<Jawaban> jawabans, boolean bitValid, String msg, Bitmap bitmap, Uri uri, boolean bolHaveAnswer, int intValidateId, int intPositionId) {
        this.id = id;
        this.image = image;
        this.txtPertanyaan = txtPertanyaan;
        this.bitImage = bitImage;
        this.jenisPertanyaan = jenisPertanyaan;
        this.jawabans = jawabans;
        this.bitValid = bitValid;
        this.message = msg;
        this.path= uri;
        this.bitmap = bitmap;
        this.bolHaveAnswer = bolHaveAnswer;
        this.intValidateId = intValidateId;
        this.intPositionId = intPositionId;
    }
}
