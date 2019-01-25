package com.kalbe.mobiledevknlibs.IntroApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dewi Oktaviani on 2/19/2018.
 */

public class Intro {
    public class PrefManager{
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        Context _context;

        // shared pref mode
        int PRIVATE_MODE = 0;

        // Shared preferences file name
        private static final String PREF_NAME = "kalbenutritionals-welcome";

        private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

        public PrefManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void setFirstTimeLaunch(boolean isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
            editor.commit();
        }

        public boolean isFirstTimeLaunch() {
            return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        }
    }
        /**
         * View pager adapter
         */
        public class ViewPagerAdapter extends PagerAdapter {
            private LayoutInflater layoutInflater;
            private int[] layouts;
            private Context mContext;
            public ViewPagerAdapter(Context mContext, int[] layouts) {
                this.layouts = layouts;
                this.mContext = mContext;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = layoutInflater.inflate(layouts[position], container, false);
                container.addView(view);

                return view;
            }

            @Override
            public int getCount() {
                return layouts.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = (View) object;
                container.removeView(view);
            }
        }
}
