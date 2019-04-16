package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.List;

public class VmListItemAdapterPertanyaan {
    public int id;
    public int image;
    public Drawable imageDrw;
    public String name;
    public boolean expanded = false;
    public boolean parent = false;
    public int jenisPertanyaan;
    public int bitImage;
    public Bitmap bitmap;
    public Uri path;
    public boolean bitValid;
    public String message;
    public List<Jawaban> jawabans;
    // flag when item swiped
    public boolean swiped = false;

    public VmListItemAdapterPertanyaan() {
    }

    public VmListItemAdapterPertanyaan(int image, String name,int id, int jenisPertanyaan, int bitImage, List<Jawaban> jawabans, boolean bitValid,String msg, Bitmap bitmap, Uri uri) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.bitImage = bitImage;
        this.jenisPertanyaan = jenisPertanyaan;
        this.jawabans = jawabans;
        this.bitValid = bitValid;
        this.message = msg;
        this.path= uri;
        this.bitmap = bitmap;
    }
}
