package com.kalbe.mobiledevknlibs.Spinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kalbe.mobiledevknlibs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 1/2/2018.
 */

public class SpinnerCustom {

    public static void selectedItemByText(Context context, Spinner spinner, List<String> listData, String str) {
        int position = new MySpinnerAdapter((context), R.layout.custom_spinner, listData).getPosition(str);
        spinner.setSelection(position);
    }

    public static void setAdapterSpinner(Spinner spinner, Context context, int textViewResourceId, List<String> objects){
        spinner.setAdapter(new SpinnerCustom.MySpinnerAdapter(context, textViewResourceId, objects));
    }
    public static class MySpinnerAdapter extends ArrayAdapter<String> {

        List<String> arrObject;
        Context context;

        public MySpinnerAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            arrObject = new ArrayList<>();
            arrObject = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvTitle);
            label.setText(arrObject.get(position));
            TextView sub = (TextView) row.findViewById(R.id.tvDesc);
            sub.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.GONE);
            row.setBackgroundColor(new Color().TRANSPARENT);
            return row;
        }
    }

}
