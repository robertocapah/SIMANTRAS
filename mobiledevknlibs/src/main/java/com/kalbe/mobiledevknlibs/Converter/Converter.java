package com.kalbe.mobiledevknlibs.Converter;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Robert on 03/01/2018.
 */

public class Converter {
    public static double StringToDouble(String txtNumber){
        if (txtNumber != null && !txtNumber.equals("")){
            return Double.parseDouble(txtNumber);
        }else{
            return 0;
        }
    }


    public static Fragment FragmentStringToClass(Context context, String name) throws InstantiationException,IllegalAccessException,ClassNotFoundException{
        Fragment fragment = null;
        try {
            Class<?> fragmentClass = Class.forName(context.getPackageName() + "." +name);
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return fragment;
    }
//    public static int
}
