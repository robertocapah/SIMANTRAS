package com.kalbe.mobiledevknlibs.PDFView;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.kalbe.mobiledevknlibs.R;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 1/15/2018.
 */

public class PDFViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
    private static final String TAG = PDFViewer.class.getSimpleName();
    String fileName;
    int pageNumber = 0;
    boolean swipeHorizontal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().setHomeButtonEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        Uri file = (Uri) getIntent().getExtras().get("pdf");
        swipeHorizontal = getIntent().getExtras().getBoolean("swipeHorizontal");
        display(file);
    }

    private boolean display(Uri uriFileName){
        fileName = getFileName(uriFileName);

        pdfView.fromUri(uriFileName).defaultPage(pageNumber).onPageChange(this).swipeHorizontal(swipeHorizontal)
                .scrollHandle(new DefaultScrollHandle(this)).load();
        return true;
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarkTree(pdfView.getTableOfContents(), "-");
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

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
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
}
