package com.kalbenutritionals.simantra.BL;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kalbenutritionals.simantra.Database.Common.ClsDataError;
import com.kalbenutritionals.simantra.Database.Common.ClsDataJson;
import com.kalbenutritionals.simantra.Database.Common.ClsPushData;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClstLogError;
import com.kalbenutritionals.simantra.Database.EnumCounterData;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepotLogError;
import com.kalbenutritionals.simantra.Database.Repo.RepomCounterData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.kalbenutritionals.simantra.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class BLHelper {

    String access_token,clientId = "";
    List<ClsToken> dataToken;
    public void setupFormats(ArrayList<Integer> mSelectedIndices,ZXingScannerView mScannerView) {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }
    public ClsPushData pushData(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataJson dtPush = new ClsDataJson();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        List<Boolean> isDataNull = new ArrayList<>();
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setDtLogin(dataLogin.getDtLogIn());
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }



//            List<ClstLogError>

            FileUpload = new HashMap<>();
            dtPush.setFromUnplan(false);

//            if ()
        }else {
            dtPush = null;
        }
        dtclsPushData.setDataJson(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public boolean printPDF(Context context){
        // open a new document
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("Images.pdf"));
            document.open();

            Image image1 = Image.getInstance("watermark.png");
            document.add(image1);


            String imageUrl = "http://resepcaramemasak.info/wp-content/uploads/2018/02/resep-bajigur.jpg";

            Image image2 = Image.getInstance(new URL(imageUrl));
            document.add(image2);
            document.add(new Paragraph("A Hello World PDF document."));

            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public void createPDF(View view){
//output file path
        String outpath=Environment.getExternalStorageDirectory()+"/mypdf.pdf";
//reference to EditText
//create document object
        PdfDocument pdfDoc = new PdfDocument();
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
//        PdfWriter writer = PdfWriter.getInstance(doc, out);
//        PdfContentByte cb = writer.getDirectContent();
        try {

//create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
//open the document for writing
            doc.open();
//add paragraph to the document

            doc.add(new LineSeparator());
            doc.addHeader("Test Name", "Test Content");
            doc.add(new Paragraph("HelloWorld"));
            doc.addAuthor("Roberto");
            doc.add(new Chunk());
            doc.add(new DottedLineSeparator());
//close the document
            doc.close();

        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ClsPushData pushDataError(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataError dtPush = new ClsDataError();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RepotLogError _repotLogError = new RepotLogError(context);
//            List<ClstLogError> ListOfDataError = _repotLogError.getAllPushData();
/*
            FileUpload = new HashMap<>();
            if (ListOfDataError!=null){
                dtPush.setListOfDatatLogError(ListOfDataError);
                for (ClstLogError data : ListOfDataError){
                    if (data.getBlobImg()!=null){
                        FileName.add(data.getTxtGuiId());
                        FileUpload.put(data.getTxtGuiId(), data.getBlobImg());
                    }
                }
            }*/

        }else {
            dtPush = null;
        }
        dtclsPushData.setDataError(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }





}


