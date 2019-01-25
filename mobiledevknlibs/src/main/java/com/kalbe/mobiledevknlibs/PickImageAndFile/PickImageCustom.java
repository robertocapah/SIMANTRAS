package com.kalbe.mobiledevknlibs.PickImageAndFile;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;

import java.net.URI;

/**
 * Created by Rian Andrivani on 1/12/2018.
 */

public class PickImageCustom {
    public static void selectImageProfile(final Context context, final String folderPath, final int REQUEST_CODE_CAMERA, final int REQUEST_CODE_GALLERY) {
        final CharSequence[] items = { "Ambil Foto", "Pilih dari Galeri",
                "Batal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= PermissionChecker.Utility.checkPermission(context);
                if (items[item].equals("Ambil Foto")) {
                    if(result)
                        CaptureImageCustomUri(context, folderPath, REQUEST_CODE_CAMERA);
                } else if (items[item].equals("Pilih dari Galeri")) {
                    if(result)
                        galleryIntent(context, REQUEST_CODE_GALLERY);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private static void CaptureImageCustomUri(Context context, String folderName, final int REQUEST_CODE) {
        boolean result = PermissionChecker.Utility.checkPermission(context);
        if (result){
            Uri uriImage = new UriData().getOutputMediaImageUriCons(context, folderName);
            Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentCamera1.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
            ((Activity)context).startActivityForResult(intentCamera1, REQUEST_CODE);
        }
    }
    private static void galleryIntent(Context context, int REQUEST_CODE) {
        boolean result = PermissionChecker.Utility.checkPermission(context);
        if (result){
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity)context).startActivityForResult(pickPhoto , REQUEST_CODE);//one can be replaced with any action code
        }
    }

    public static void performCropProfile(Uri uriImage, Context context, int REQUEST_CODE){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
            //start the activity - we handle returning in onActivityResult
            ((Activity)context).startActivityForResult(cropIntent, REQUEST_CODE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void performCropCustom(Uri uriImage, Context context, int REQUEST_CODE){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            ((Activity)context).startActivityForResult(cropIntent, REQUEST_CODE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
