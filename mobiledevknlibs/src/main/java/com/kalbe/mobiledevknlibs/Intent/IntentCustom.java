package com.kalbe.mobiledevknlibs.Intent;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;

import com.kalbe.mobiledevknlibs.PDFView.PDFViewer;
import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

/**
 * Created by Dewi Oktaviani on 1/2/2018.
 */

public class IntentCustom {

    /*
    * ini untuk intent dari fragment ke fragment dengan passing data ke activity terlebih dahulu
    * dengan param @value berupa nama fragment yang akan di tuju
    */
    public static void intentViewFragment(Context context, Class<?> cls, String keyPutExtra, String value) {
        Intent myIntent = new Intent(context, cls);
        myIntent.putExtra(keyPutExtra, value);
        Activity activity = (Activity) context;
        activity.finish();
        context.startActivity(myIntent);
    }


    /*
    * method ini untuk intent ke activity tertentu
    * param @keyPutExtra ini bisa di isi null jika tidak ingin ada data yang di passing
    * param @value ini di isi null jika param @keyPutExtra di isi null
    * sebaliknya jika @keyPutExtra tidak null mana bisa di isi dengan array string dengan length berapapun
    */
    public static void intentToActivity(Context context, Class<?> cls, String keyPutExtra, String[] value) {
        Intent myIntent = new Intent(context, cls);
        myIntent.putExtra(keyPutExtra, value);
        Activity activity = (Activity) context;
        activity.finish();
        context.startActivity(myIntent);
    }


    /*
    * intent ini untuk melihat pdf melalui pdf viewer yang sudah di buat di libs mobile dev kn
    * jadi untuk melihat pdf tidak perlu menggunakan aplikasi pihak ketiga lagi
    * param @uri di isi dengan path file pdf yang ingin di lihat
    * param @swipeHorizontal akan menampilkan scroll horizontal jika di isi true dan scroll vertical jika false
    */
    public static void intentPDViewer(Context context, Uri uri, Boolean swipeHorizontal) {
        boolean result = PermissionChecker.Utility.checkPermission(context);
        if (result) {
            Intent intent = new Intent(context, PDFViewer.class);
            intent.putExtra("pdf", uri);
            intent.putExtra("swipeHorizontal", swipeHorizontal);
            ((Activity) context).finish();
            context.startActivity(intent);
        }
    }

    /*
    * ini intent untuk view file menggunakan aplikasi pihak ketiga
    * param @uri nya harus sudah jelas
    * param @typeData nya bisa di get dengan memanggil class TypeDataIntent
    * param @backToFront nya itu in case user ingin ke activity awal jika aplikasi pihak ketiga tidak tersedia
    * */
    public static void intentActionView(Context context, Uri uri, String typeData, Boolean backToFront) {
        boolean result = PermissionChecker.Utility.checkPermission(context);
        if (result) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                intent.setDataAndType(uri, typeData);
                ((Activity) context).finish();
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                if (backToFront) {
                    IntentCustom.intentBackToFront(context);
                }
                new ToastCustom().showToasty(context, "You haven't app for open this file", 3);
            }
        }
    }

    //ini intent untuk balik ke activty parentnya, satu tingkat di atasnya
    public static void intentBackToFront(Context context) {
        Intent parentIntent = NavUtils.getParentActivityIntent((Activity) context);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(parentIntent);
        ((Activity) context).finish();
    }
}
