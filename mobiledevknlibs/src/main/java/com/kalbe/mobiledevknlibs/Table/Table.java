package com.kalbe.mobiledevknlibs.Table;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kalbe.mobiledevknlibs.R;

import java.util.ArrayList;

/**
 * Created by Robert on 04/01/2018.
 */

public class Table {
    public static void setTableLayout(Context context, TableLayout tableLayout, String[] colTextHeader, ArrayList<String[]> colData) {
        tableLayout.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(context);

        TableLayout tl = new TableLayout(context);

        for (String text : colTextHeader) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));
            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);
        for(String[] data : colData){
            tr = new TableRow(context);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin=0;
            int topMargin=0;
            int rightMargin=0;
            int bottomMargin=0;
            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            tr.setLayoutParams(tableRowParams);
            for (String dt : data){
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 3));
                tv.setTextSize(12);
                tv.setWidth(100);
                tv.setPadding(10, 10, 10, 10);
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv.setTextColor(Color.BLACK);
                tv.setText(dt);
                tr.addView(tv,params);
            }
            tl.addView(tr, tableRowParams);
        }

        tableLayout.addView(tl);
    }
    public void setTable(Context context, TableLayout tableLayout){{

    }
    }
}
