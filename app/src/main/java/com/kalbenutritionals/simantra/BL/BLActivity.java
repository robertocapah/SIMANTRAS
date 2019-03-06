package com.kalbenutritionals.simantra.BL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.TypedValue;


import com.kalbe.mobiledevknlibs.library.PullToRefreshSwipeMenuListView;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenu;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenuItem;
import com.kalbe.mobiledevknlibs.library.swipemenu.interfaces.SwipeMenuCreator;

import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterApp;
import com.kalbenutritionals.simantra.ViewModel.VmSwipeList;
import com.kalbenutritionals.simantra.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Rian Andrivani on 11/23/2017.
 */

public class BLActivity extends Activity {
    public Bitmap resizeImageForBlob(Bitmap photo){
        int width = photo.getWidth();
        int height = photo.getHeight();

        int maxHeight = 800;
        int maxWidth = 800;

        Bitmap bitmap;

        if(height > width){
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        }
        else if(height < width){
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        }
        else{
            width = maxWidth;
            height = maxHeight;
        }

        bitmap = Bitmap.createScaledBitmap(photo, width, height, true);

        return bitmap;
    }

    public String GenerateGuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    public String greetings(){
        Calendar today = Calendar.getInstance();
        int hour = today.get(Calendar.HOUR_OF_DAY);
        String greeting = "Welcome, ";
        if(hour > 2 && hour < 12){
            greeting = "Good Morning, ";
        }
        else if(hour >= 12 && hour < 16){
            greeting = "Good Afternoon, ";
        }
        else if(hour >= 16 && hour < 19){
            greeting = "Good Evening, ";
        }
        else if(hour >= 19 && hour < 2){
            greeting = "Good Night, ";
        }
        else{
            greeting = "Welcome, ";
        }

        return greeting;
    }

    public String convertNumberDec(double dec) {
        double harga = dec;
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        String hsl = df.format(harga);

        return hsl;
    }

    private static int dp2px(Context _ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, _ctx.getResources().getDisplayMetrics());
    }

    public SwipeMenuCreator setCreator(final Context _ctx, final Map<String, HashMap> map) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                HashMap<String, String> map2 = new HashMap<String, String>();

                for (int i = 0; i < map.size(); i++) {
                    map2 = map.get(String.valueOf(i));

                    SwipeMenuItem menuItem = new SwipeMenuItem(_ctx);
                    menuItem.setWidth(dp2px(_ctx, 90));

                    if (map2.get("name") == "View") {
                        int icon = R.drawable.ic_view;
                        menuItem.setIcon(icon);
                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#16a085")));
                    } else if (map2.get("name") == "Edit") {
                        int icon = R.drawable.ic_edit_white;
                        menuItem.setIcon(icon);
                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#2980b9")));
                    } else if (map2.get("name") == "Delete") {
                        int icon = R.drawable.ic_delete_white;
                        menuItem.setIcon(icon);
                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#c0392b")));
                    } else if (map2.get("name") == "Add") {
                        int icon = R.drawable.ic_add_white;
                        menuItem.setIcon(icon);
                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#27ae60")));
                    }
//                    else if (map2.get("name") == "AddProduct") {
//                        int icon = R.drawable.ic_shopping_cart;
//                        menuItem.setIcon(icon);
//                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#27ae60")));
//                    } else if (map2.get("name") == "ViewData") {
//                        int icon = R.drawable.ic_view_data;
//                        menuItem.setIcon(icon);
//                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#16a085")));
//                    } else if (map2.get("name") == "ViewImage") {
//                        int icon = R.drawable.ic_view_image;
//                        menuItem.setIcon(icon);
//                        menuItem.setBackground(new ColorDrawable(Color.parseColor("#ffa200")));
//                    }
                    menu.addMenuItem(menuItem);
                }
            }
        };

        return creator;

    }

    public static AdapterApp setList(Context _ctx, final List<VmSwipeList> swipeList) {
        final AdapterApp mAdapter;
        PullToRefreshSwipeMenuListView mListView;
        Handler mHandler;

        List<String> mAppList = new ArrayList<String>();

        for (int i = 0; i < swipeList.size(); i++) {
            VmSwipeList getswipeList = swipeList.get(i);
            mAppList.add(getswipeList.get_txtTitle() + "\n" + getswipeList.get_txtDescription());
        }

        mAdapter = new AdapterApp(_ctx, mAppList);

        return mAdapter;

    }

    public String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return null;
        }
    }
}
