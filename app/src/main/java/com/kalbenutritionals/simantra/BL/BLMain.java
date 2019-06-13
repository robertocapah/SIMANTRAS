package com.kalbenutritionals.simantra.BL;

import android.content.Context;
import android.util.Log;


import com.kalbenutritionals.simantra.Database.Common.ClsStatusMenuStart;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Repo.EnumStatusMenuStart;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class BLMain {

    public ClsStatusMenuStart checkUserActive(Context context) throws ParseException, SQLException {

        RepomUserLogin loginRepo = new RepomUserLogin(context);
        ClsmUserLogin dtLogin = new RepomUserLogin(context).getUserLogin(context);
        ClsStatusMenuStart _clsStatusMenuStart =new ClsStatusMenuStart();
//        if (dtLogin!=null){
//
//        }

        if(loginRepo.CheckLoginNow(context)){
            _clsStatusMenuStart.set_intStatus(EnumStatusMenuStart.UserActiveLogin);
        }else {

            boolean valid = false;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            if (valid==true){
                _clsStatusMenuStart.set_intStatus(EnumStatusMenuStart.PushDataMobile);
            }else {
                _clsStatusMenuStart.set_intStatus(EnumStatusMenuStart.FormLogin);
            }
        }
        return _clsStatusMenuStart;
    }



    public ClsmUserLogin getUserLogin(Context context){
        List <ClsmUserLogin> dtList = new ArrayList<>();
        RepomUserLogin dtRepo= new RepomUserLogin(context);
        try {
            dtList = (List<ClsmUserLogin>) dtRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dtList.size()>0){
            return dtList.get(0);
        }else {
            return null;
        }
    }

    public byte[] arrayDecryptFile(byte[] blobFile){
        String key = "kalbenutritionals";
        byte[] arrayFileDecrypt = null;
        try {
            byte[] array = blobFile;
            byte[] keyInByte = getKeyBytes(key);
            arrayFileDecrypt = decrypt(array, keyInByte, keyInByte);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return arrayFileDecrypt;
    }

   /* public byte[] getFile() throws FileNotFoundException {
        byte[] data = null;
        byte[] inarry = null;
        String path = new ClsHardCode().txtFolderAkuisisi + "dewi";
        try {
            InputStream is = new FileInputStream(path); // use recorded file instead of getting file from assets folder.
            int length = is.available();
            data = new byte[length];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            inarry = output.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inarry;

    }*/

    public byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes= new byte[16];
        String characterEncoding = "UTF-8";
        byte[] parameterKeyBytes= key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

    public  byte[] decrypt(byte[] cipherText, byte[] key, byte [] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String cipherTransformation = "AES/CBC/PKCS5Padding";
        String aesEncryptionAlgorithm = "AES";
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public void createFolderApp(){
        ClsHardCode clsdthc = new ClsHardCode();
        File appDir=new File(clsdthc.txtPathApp);

        if(!appDir.exists() && !appDir.isDirectory())
        {
            // create empty directory
            if (appDir.mkdirs())
            {
                Log.i("CreateDir","App dir created");
            }
            else
            {
                Log.w("CreateDir","Unable to create app dir!");
            }
        }
        else
        {
            Log.i("CreateDir","App dir already exists");
        }
    }

    /*public static void deleteMediaStorage(){
        File mediaStorageDirAbsen = new File(new ClsHardCode().txtFolderCheckIn + File.separator);
        if (mediaStorageDirAbsen.exists()){
            if (mediaStorageDirAbsen.isDirectory()){
                for (File currentFile : mediaStorageDirAbsen.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDirAbsen.delete();
        }

        File mediaStorageDirAkuisisi = new File(new ClsHardCode().txtFolderAkuisisi + File.separator);
        if (mediaStorageDirAkuisisi.exists()){
            if (mediaStorageDirAkuisisi.isDirectory()){
                for (File currentFile : mediaStorageDirAkuisisi.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDirAkuisisi.delete();
        }

        File mediaStorageDir = new File(new ClsHardCode().txtFolderData + File.separator);
        if (mediaStorageDir.exists()){
            if (mediaStorageDir.isDirectory()){
                for (File currentFile : mediaStorageDir.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDir.delete();
        }
    }*/

    public static void deleteMediaStorageDir (){
        File mediaStorageDir = new File(new ClsHardCode().txtFolderTemp + File.separator);
        if (mediaStorageDir.exists()){
            if (mediaStorageDir.isDirectory()){
                for (File currentFile : mediaStorageDir.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDir.delete();
        }
    }
}
