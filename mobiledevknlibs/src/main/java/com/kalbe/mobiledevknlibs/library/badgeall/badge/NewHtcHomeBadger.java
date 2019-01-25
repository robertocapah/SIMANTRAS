package com.kalbe.mobiledevknlibs.library.badgeall.badge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.BadgerShortCut;
import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.ShortcutBadgeException;

import java.util.Arrays;
import java.util.List;



public class NewHtcHomeBadger implements BadgerShortCut {

    public static final String INTENT_UPDATE_SHORTCUT = "com.htc.launcher.action.UPDATE_SHORTCUT";
    public static final String INTENT_SET_NOTIFICATION = "com.htc.launcher.action.SET_NOTIFICATION";
    public static final String PACKAGENAME = "packagename";
    public static final String COUNT = "count";
    public static final String EXTRA_COMPONENT = "com.htc.launcher.extra.COMPONENT";
    public static final String EXTRA_COUNT = "com.htc.launcher.extra.COUNT";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {

        Intent intent1 = new Intent(INTENT_SET_NOTIFICATION);
        intent1.putExtra(EXTRA_COMPONENT, componentName.flattenToShortString());
        intent1.putExtra(EXTRA_COUNT, badgeCount);
        context.sendBroadcast(intent1);

        Intent intent = new Intent(INTENT_UPDATE_SHORTCUT);
        intent.putExtra(PACKAGENAME, componentName.getPackageName());
        intent.putExtra(COUNT, badgeCount);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.htc.launcher");
    }
}
