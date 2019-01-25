package com.kalbe.mobiledevknlibs.library.zXingLib.zxing.integrator;


import android.app.Activity;
import android.content.Intent;

import com.kalbe.mobiledevknlibs.library.zXingLib.zxing.CaptureActivity;
import com.kalbe.mobiledevknlibs.library.zXingLib.zxing.Intents;
import com.kalbe.mobiledevknlibs.library.zXingLib.zxing.config.ZXingLibConfig;

/**
 * @author Jim.H
 */
public final class IntentIntegrator {

    public static final int REQUEST_CODE = 99;

    private IntentIntegrator() {
    }


    public static void initiateScan(Activity activity) {
        initiateScan(activity, null, null, null);
    }


    public static void initiateScan(Activity activity, ZXingLibConfig config) {
        initiateScan(activity, null, null, config);
    }


    public static void initiateScan(Activity activity, String scanFormatsString,
                                    String characterSet, ZXingLibConfig config) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        intent.putExtra(Intents.Scan.FORMATS, scanFormatsString);
        intent.putExtra(Intents.Scan.CHARACTER_SET, characterSet);
        intent.putExtra(ZXingLibConfig.INTENT_KEY, config);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra(Intents.Scan.RESULT);
                String formatName = intent.getStringExtra(Intents.Scan.RESULT_FORMAT);
                return new IntentResult(contents, formatName);
            } else {
                return new IntentResult(null, null);
            }
        }
        return null;
    }
}
