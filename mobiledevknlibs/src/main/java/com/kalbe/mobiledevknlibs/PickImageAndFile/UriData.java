package com.kalbe.mobiledevknlibs.PickImageAndFile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dewi Oktaviani on 1/8/2018.
 */

public class UriData {
    private final String IMAGE_DIRECTORY_NAME = "Image";
    //get lication file to save using file path
    public Uri getOutputMediaUrifromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    //get location file to save tmp using string path
    public Uri getOutputMediaImageUri(Context context, String folderName, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+ ".provider", getOutputMediaImage(folderName, fileName));
        } else {
            return Uri.fromFile(getOutputMediaImage(folderName, fileName));
        }
    }

    public Uri getOutputMediaUri(Context context, String folderName, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+ ".provider", getOutputMedia(folderName, fileName));
        } else {
            return Uri.fromFile(getOutputMedia(folderName, fileName));
        }
    }

    public Uri getOutputMediaUriData(Context context, String txtPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+ ".provider", new File(txtPath));
        } else {
            return Uri.fromFile(new File(txtPath));
        }
    }

    public Uri getOutputMediaUriFolder(Context context, String folderName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+ ".provider", getOutputFolder(folderName));
        } else {
            return Uri.fromFile(getOutputFolder(folderName));
        }
    }

    public Uri getOutputMediaImageUriCons(Context context, String folderName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+ ".provider", getOutputMediaImageCons(folderName));
        } else {
            return Uri.fromFile(getOutputMediaImageCons(folderName));
        }
    }
    //create path file
    public Uri getOutputMediaFileUri(String folderName, String fileName) {
        // External sdcard location

        File mediaStorageDir = new File(folderName + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        File mediaFile = null;
        if (fileName.length()>0){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName );
        }

        return Uri.fromFile(mediaFile);
    }

    //create path file
    public File getOutputMediaImage(String folderName, String fileName) {
        // External sdcard location

        File mediaStorageDir = new File(folderName + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName + ".png");
        return mediaFile;
    }

    //create path file
    public File getOutputMediaImageCons(String folderName) {
        // External sdcard location

        File mediaStorageDir = new File(folderName + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_act" + ".png");
        return mediaFile;
    }

    //create path file
    public File getOutputMedia(String folderName, String fileName) {
        // External sdcard location

        File mediaStorageDir = new File(folderName + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public File getOutputFolder(String folderName) {
        // External sdcard location

        File mediaStorageDir = new File(folderName + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        return mediaStorageDir;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title",null);
        return Uri.parse(path);
    }
}
