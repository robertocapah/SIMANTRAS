package com.kalbe.mobiledevknlibs.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Robert on 04/01/2018.
 */

public class AlertDialogs {
    public static void alertDialogExit(final Context activity){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ((Activity)activity).finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
