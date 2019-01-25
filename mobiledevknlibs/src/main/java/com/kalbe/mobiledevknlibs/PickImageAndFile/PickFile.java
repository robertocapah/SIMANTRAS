package com.kalbe.mobiledevknlibs.PickImageAndFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dewi Oktaviani on 1/8/2018.
 */

public class PickFile {

    public void intentPickFile(Context context, int requestCode, String[] mimetypes){
        boolean result = PermissionChecker.Utility.checkPermission(context);
        if (result){
            Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
//                    pickIntent.setType("application/x-compressed-zip");
            pickIntent.setType("application/*");
//            String[] mimetypes = {"application/pdf" , "application/msword" , "application/vnd.ms-excel"};
            pickIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            ((Activity)context).startActivityForResult(pickIntent, requestCode);
        }
    }
    public byte[] getByteArrayFileToSave(Uri path, Context mContext) throws FileNotFoundException
    {
        byte[] byteFile = null;
        InputStream is = null;
        try {
            byte[] data = null;
            is = mContext.getContentResolver().openInputStream(path); // use recorded file instead of getting file from assets folder.
            int length = is.available();
            data = new byte[length];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            byteFile = output.toByteArray();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
                try {
                    if (is!=null)
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return byteFile;
    }

    public void moveFileToSpecificUri(Context context, Intent fileReturnedIntent, Uri path){
        InputStream in = null;
        OutputStream out = null;
        Uri uri = fileReturnedIntent.getData();
        try {
            in = context.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(path.getPath().toString());

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //untuk decode yang ada path folder dan nama filenya (create file pertama kali saat intent untuk pick file)??
    public File decodeByteArraytoFile(byte[] fileArray, String pathFolder, String nameFile) {
        File folder = new File(pathFolder);
        folder.mkdirs();
        File file = null;
        try {
            file = new File(pathFolder, nameFile);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(fileArray);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //untuk create file temporary dengan ekstensi PDF
    public File decodeByteArraytoTempFilePDF(byte[] fileArray, String pathFolder) {
        File folder = new File(pathFolder);
        folder.mkdirs();
        File file = null;
        try {
            file = File.createTempFile("file-", ".pdf", new File(pathFolder));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(fileArray);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //create file temporary dengan berbagai ekstensi, parameternya hanya byte array data, path folder dan ekstension(nama file tidak di perlukan)
    public File decodeByteArraytoTempFile(byte[] fileArray, String pathFolder, String fileExtension) {
        File folder = new File(pathFolder);
        folder.mkdirs();
        File file = null;
        try {
            file = File.createTempFile("file-", fileExtension, new File(pathFolder));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(fileArray);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public String getFileName(Context context, int resultCode, Intent fileReturnedIntent) throws FileNotFoundException {
        Uri uri = fileReturnedIntent.getData();
        String fileName = "";
        byte[] dataFile = null;
        String path = fileReturnedIntent.getData().getPath().toString();
        if (resultCode == Activity.RESULT_OK){
            //cek dulu jika byte array != null maka tampilkan nama file
            byte[] byteFile = null;
            try {
                InputStream is = context.getContentResolver().openInputStream(fileReturnedIntent.getData()); // use recorded file instead of getting file from assets folder.
                int length = is.available();
                byteFile = new byte[length];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = is.read(byteFile)) != -1) {
                    output.write(byteFile, 0, bytesRead);
                }
                dataFile = output.toByteArray();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (dataFile != null){
                if (Build.MANUFACTURER.equals("Xiaomi")){
                    fileName = path.substring(path.lastIndexOf('/')+1, path.length());
                }else {
                    Cursor returnCursor = context.getContentResolver().query(uri,null, null, null, null);

                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    returnCursor.moveToFirst();
                    fileName = returnCursor.getString(nameIndex);
                }

            }else {
                fileName = "";
            }}
        return fileName;
    }
}
