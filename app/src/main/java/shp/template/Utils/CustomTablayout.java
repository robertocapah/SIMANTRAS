package shp.template.Utils;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by Dewi Oktaviani on 10/12/2018.
 */

public class CustomTablayout extends TabLayout{
//    private static final int WIDTH_INDEX = 0;
    private static final int WIDTH_INDEX = 0;
    private static final int HEIGHT_INDEX = 1;
    private int DIVIDER_FACTOR = 3;
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";

    public CustomTablayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public CustomTablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public CustomTablayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }

    public void setTabNumbers(int num)
    {
        this.DIVIDER_FACTOR = num;
        initTabMinWidth();
    }

    private void initTabMinWidth()
    {
        int[] wh = getScreenSize(getContext());
        int tabMinWidth = wh[WIDTH_INDEX] / DIVIDER_FACTOR;

        Log.v("CUSTOM TAB LAYOUT", "SCREEN WIDTH = " + wh[WIDTH_INDEX] + " && tabTotalWidth = " + (tabMinWidth*DIVIDER_FACTOR) + " && TotalTabs = " + DIVIDER_FACTOR);

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static int[] getScreenSize(Context context) {
        int[] widthHeight = new int[2];
        widthHeight[WIDTH_INDEX] = 0;
        widthHeight[HEIGHT_INDEX] = 0;

        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();

            Point size = new Point();
            display.getSize(size);
            widthHeight[WIDTH_INDEX] = size.x;
            widthHeight[HEIGHT_INDEX] = size.y;

            if (!isScreenSizeRetrieved(widthHeight))
            {
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                widthHeight[0] = metrics.widthPixels;
                widthHeight[1] = metrics.heightPixels;
            }

            // Last defense. Use deprecated API that was introduced in lower than API 13
            if (!isScreenSizeRetrieved(widthHeight)) {
                widthHeight[0] = display.getWidth(); // deprecated
                widthHeight[1] = display.getHeight(); // deprecated
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return widthHeight;
    }

    private static boolean isScreenSizeRetrieved(int[] widthHeight) {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0;
    }
}
