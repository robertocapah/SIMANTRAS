package com.kalbe.mobiledevknlibs.ToastAndSnackBar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.R;
import com.kalbe.mobiledevknlibs.library.toasty.Toasty;


/**
 * Created by aan.junianto on 28/12/2017.
 */

public class ToastCustom{
    public void showToastDefault(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
    }
    public void showToastSPGMobile(Context ctx, String str, boolean status) {
        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View promptView = mInflater.inflate(R.layout.custom_toast, null);

        TextView tvTextToast = (TextView) promptView.findViewById(R.id.custom_toast_message);
        ImageView icon = (ImageView) promptView.findViewById(R.id.custom_toast_image);
        tvTextToast.setText(str);

        GradientDrawable bgShape = (GradientDrawable)promptView.getBackground();

        if (status) {
            bgShape.setColor(Color.parseColor("#6dc066"));
            icon.setImageResource(R.drawable.ic_checklist);

        } else {
            bgShape.setColor(Color.parseColor("#e74c3c"));
            icon.setImageResource(R.drawable.ic_error);
        }

        Toast toast = new Toast(ctx);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(promptView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    //1 for success, 2 for error, 3 for info, 4 for warning, 5 for normal
    public void showToasty(Context ctx, String str, int type) {
        switch (type){
            case 1:
                Toasty.success(ctx, str, Toast.LENGTH_SHORT, true).show();
                break;
            case 2:
                Toasty.error(ctx, str, Toast.LENGTH_SHORT, true).show();
                break;
            case 3:
                Toasty.info(ctx, str, Toast.LENGTH_SHORT, true).show();
                break;
            case 4:
                Toasty.warning(ctx, str, Toast.LENGTH_SHORT, true).show();
                break;
            case 5:
                Toasty.warning(ctx, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
                break;
        }
    }
    public void showToastyCustom(Context ctx, CharSequence str, Drawable yourIconDrawable, int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        Toasty.custom(ctx, str, yourIconDrawable, tintColor, duration, withIcon, shouldTint).show();
    }
}
