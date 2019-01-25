package com.kalbe.mobiledevknlibs.AlertDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.kalbe.mobiledevknlibs.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Robert on 05/01/2018.
 */

public class clsDatePicker {
    private static Calendar calendar;
    private static EditText dateView;
    private static int year, month, day;
    private static DatePickerDialog dialog;
    private static int frm;
    public static String YEAR = "year";
    public static String MONTH = "month";
    public static String DAY_OF_MONTH = "day";
    public static String DATE_MAX = "date_max";
    public static String DATE_MIN = "date_min";

    public static void showHint(EditText editText, Bundle bundle, int format) {
        calendar = Calendar.getInstance();
        year = bundle.getInt(YEAR);
        month = bundle.getInt(MONTH);
        day = bundle.getInt(DAY_OF_MONTH);
        calendar.set(year, month, day);
        editText.setHint(formatSimpleDate(calendar.getTime(), format));
    }

    public static void showDatePicker(final Context context, EditText editText, String title, Bundle bundle, int format, int styles) {
        frm = format;
        dateView = editText;
        if (editText.getText().toString().equals("")) {
            year = bundle.getInt(YEAR);
            month = bundle.getInt(MONTH);
            day = bundle.getInt(DAY_OF_MONTH);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //tambah style baru
//            dialog = new DatePickerDialog(context, styles, myDateListener, year, month, day);
//        } else {
//            dialog = new DatePickerDialog(context, myDateListener, year, month, day);
//        }
        dialog = new DatePickerDialog(context, styles, myDateListener, year, month, day);
        long max = bundle.getLong(DATE_MAX);
        long min = bundle.getLong(DATE_MIN);
        if (max != 0) {
            dialog.getDatePicker().setMaxDate(max);
        }
        if (min != 0) {
            dialog.getDatePicker().setMinDate(min);
        }
        dialog.setTitle(title);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private static DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    displayDate(dateView, frm);
                    dateView.setEnabled(true);
                }
            };

    private static void displayDate(EditText editText, int format) {
        GregorianCalendar c = new GregorianCalendar(year, month, day);
        String date = formatSimpleDate(c.getTime(), format);
        editText.setText(date);
    }

    public static String formatSimpleDate(Date dates, int format) {
        String date = "";
        SimpleDateFormat simpleDateFormat;
        switch (format) {
            case 0:
                simpleDateFormat = new SimpleDateFormat("dd - MMM - yyyy");
                break;
            case 1:
                simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                break;
            case 2:
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                break;
            case 3:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        date = simpleDateFormat.format(dates);
        return date;
    }

    private static void showDate(EditText editText, int year, int month, int day) {
        editText.setHint(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public static class format {
        public static final int standard0 = 0; //01 - JAN - 2008
        public static final int standard1 = 1; //01 JAN 2008
        public static final int standard2 = 2; //31/12/2008
        public static final int standard3 = 3; //31-12-2008
    }

    public static class style {
        public static final int Theme_Holo_Dialog = android.R.style.Theme_Holo_Dialog;
        public static final int Theme_Holo_Light_Dialog = android.R.style.Theme_Holo_Light_Dialog;
    }
}
