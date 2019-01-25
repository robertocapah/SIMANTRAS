package com.kalbe.mobiledevknlibs.CopyFile;

import android.content.Context;
import android.os.Environment;

import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


/**
 * Created by Dewi Oktaviani on 1/15/2018.
 */

public class CopyFile {
    public static void copyFileDB(Context context, String currPath)
    {
        try
        {
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite())
            {
                String backupDBName = "databaseroot_copy";
                File currentDB = new File(currPath);
                File backupDB = new File(sd, backupDBName);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    new ToastCustom().showToasty(context, "Successfull...", 1);
                } else {
                    new ToastCustom().showToasty(context, "File not found...", 2);
                }
            } else {
                new ToastCustom().showToasty(context, "Permission not granted...", 4);
            }
        }
        catch (Exception e) {
//            Log.w("Settings Backup", e);
            new ToastCustom().showToasty(context, e.getMessage().toString(), 2);
        }
    }

    /*
    * copy db
    * currDbName itu di isi nama db yang sekarang
    * copyDbName di isi nama backup db nya
    */
    public static void copydb(Context context, String currDbName, String copyDbName) throws IOException {
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ currDbName;

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+ copyDbName;
            File yourFile = new File(txtPathUserData);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
* copy
* pathFrom : lokasi file sebelumnya
* pathTo : lokasi file untuk paste
*/
    public static void copyFile(String pathFrom, String pathTo) throws IOException {

        try {
            File dbFile = new File(pathFrom);
            FileInputStream fis = new FileInputStream(dbFile);
            File yourFile = new File(pathTo);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
