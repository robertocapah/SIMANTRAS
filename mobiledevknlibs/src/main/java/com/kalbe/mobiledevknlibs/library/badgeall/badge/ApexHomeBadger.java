package com.kalbe.mobiledevknlibs.library.badgeall.badge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.BadgerShortCut;
import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.ShortcutBadgeException;

import java.util.Arrays;
import java.util.List;



/**
 * @author Gernot Pansy
 */
public class ApexHomeBadger implements BadgerShortCut {

    private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String PACKAGENAME = "package";
    private static final String COUNT = "count";
    private static final String CLASS = "class";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {

        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, componentName.getPackageName());
        intent.putExtra(COUNT, badgeCount);
        intent.putExtra(CLASS, componentName.getClassName());
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.anddoes.launcher");
    }
}
