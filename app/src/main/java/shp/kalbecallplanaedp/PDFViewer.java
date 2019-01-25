package shp.kalbecallplanaedp;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.mFileAttachment;
import shp.kalbecallplanaedp.Common.tInfoProgramDetail;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Repo.mFileAttachmentRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class PDFViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
    private static final String TAG = PDFViewer.class.getSimpleName();
    String fileName;
    int pageNumber = 0;
    boolean swipeHorizontal;
    //    tInfoProgramDetailRepo infoProgramDetailRepo;
//    tInfoProgramDetail dtDetail;
    private String PDF_View = "pdf viewer";
    mFileAttachment attach;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        getSupportActionBar().setHomeButtonEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        pdfView = (PDFView)findViewById(R.id.pdfViewInfo);
        Bundle bundle = getIntent().getExtras();
//        pDialog = new ProgressDialog(PDFViewer.this);
//        pDialog.setMessage("Please wait....");
//        pDialog.setCancelable(false);
//        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//
//            }
//        });
//        infoProgramDetailRepo = new tInfoProgramDetailRepo(getApplicationContext());
        if (bundle!=null){
            try {
//                pDialog.show();
//                String tes = bundle.getString(PDF_View);
                attach = (mFileAttachment) new mFileAttachmentRepo(getApplicationContext()).findById(bundle.getInt(PDF_View));
//                dtDetail = (tInfoProgramDetail) infoProgramDetailRepo.findByDetailId(bundle.getString(PDF_View));
//                decryptFile(dtDetail.getBlobFile(), dtDetail.getTxtFileName());
                byte[] array = new clsMainBL().arrayDecryptFile(attach.getBlobFile());
                display(attach.getTxtFileName(), array);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        Uri file = (Uri) getIntent().getExtras().get("pdf");
//        swipeHorizontal = getIntent().getExtras().getBoolean("swipeHorizontal");
//        display(file);
    }


    private boolean display(String txtFileName, byte[] blobFile){
        fileName = txtFileName;

        pdfView.fromBytes(blobFile).defaultPage(pageNumber).onPageChange(this).swipeHorizontal(swipeHorizontal)
                .scrollHandle(new DefaultScrollHandle(this)).load();
        return true;
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarkTree(pdfView.getTableOfContents(), "-");
        pDialog.dismiss();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s  (%s / %s)", fileName, page + 1 , pageCount));
    }

    public void printBookmarkTree(List<PdfDocument.Bookmark> tree, String sep){
        for (PdfDocument.Bookmark b : tree){
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()){
                printBookmarkTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(parentIntent);
        finish();
    }
}
