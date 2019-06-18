package com.kalbenutritionals.simantra.ViewModel;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by dewi.oktaviani on 06/05/2019.
 */

public class VmImageContainer {
    private Bitmap bitmap;
    private Uri path;
    private String imgName;
    private int position;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
