package shp.template.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

import shp.template.Common.VMDownloadFile;
import shp.template.Common.mUserLogin;
import shp.template.Data.clsHardCode;
import shp.template.Repo.mUserLoginRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

/**
 * Created by Dewi Oktaviani on 11/22/2018.
 */

public class LongThread implements Runnable {
    int threadNo;
    Handler handler;
    VMDownloadFile data;
    public static final String TAG = "LongThread";
    Context context;

    public LongThread(Context context, int threadNo, VMDownloadFile data, Handler handler) {
        this.threadNo = threadNo;
        this.handler = handler;
        this.data = data;
        this.context = context;
    }

    @Override
    public void run() {
        Log.i(TAG, "Starting Thread : " + threadNo);
//        getBitmap(strUrl);
        final byte[] file =  getByte(data.getLink());
        handler.post(new Runnable() {
            @Override
            public void run() {
                /*try {
                    if (data.getGroupDownload().equals(new clsHardCode().INFO_PROGRAM)){
                        mFileAttachment datum = (mFileAttachment) new mFileAttachmentRepo(context).findById(Integer.parseInt(data.getTxtId()));
                        datum.setBlobFile(file);
                        new mFileAttachmentRepo(context).createOrUpdate(datum);
                    }else if (data.getGroupDownload().equals(new clsHardCode().AKUISISI)){
                        tAkuisisiDetail datum = new tAkuisisiDetailRepo(context).findByDetailId(data.getTxtId());
                        datum.setTxtImg(file);
                        new tAkuisisiDetailRepo(context).createOrUpdate(datum);
                    }else if (data.getGroupDownload().equals(new clsHardCode().REALISASI_SATU)){
                        tRealisasiVisitPlan datum = new tRealisasiVisitPlanRepo(context).findBytxtId(data.getTxtId());
                        datum.setBlobImg1(file);
                        new tRealisasiVisitPlanRepo(context).createOrUpdate(datum);
                    }else if (data.getGroupDownload().equals(new clsHardCode().REALISASI_DUA)){
                        tRealisasiVisitPlan datum = new tRealisasiVisitPlanRepo(context).findBytxtId(data.getTxtId());
                        datum.setBlobImg2(file);
                        new tRealisasiVisitPlanRepo(context).createOrUpdate(datum);
                    }else if (data.getGroupDownload().equals(new clsHardCode().LOGIN)){
                        mUserLogin datum = new mUserLoginRepo(context).findByTxtId(data.getTxtId());
                        datum.setBlobImg(file);
                        new mUserLoginRepo(context).createOrUpdate(datum);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
//                Toast.makeText(context, String.valueOf(file), Toast.LENGTH_SHORT).show();
                sendMessage(threadNo, "Thread Completed");
            }
        });
        Log.i(TAG, "Thread Completed " + threadNo);
    }

    public void sendMessage(int what, String msg) {
        Message message = handler.obtainMessage(what, msg);
        message.sendToTarget();
    }

    private Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new URL(url).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
            // Do extra processing with the bitmap
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private byte[] getByte(String url) {
        InputStream is = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            is = ucon.getInputStream();
            byte[] data = null;

            int length =  ucon.getContentLength();
            data = new byte[length];
            int bytesRead;
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            return output.toByteArray();
//            String contentType = ucon.getHeaderField("Content-Type");
//            boolean image = contentType.startsWith("image/");
//            boolean text = contentType.startsWith("application/");
//
//            if (image||text){
//
//            }
//            else { 
//                return null;
//            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }finally {
            try {
                if (output!=null)
                output.close();
                if (is!=null)
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
