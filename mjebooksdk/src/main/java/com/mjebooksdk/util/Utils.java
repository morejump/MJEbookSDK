package com.mjebooksdk.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {
    private static int countAction;
    public static int TIME_TO_SHOW_INTER = 3;
    public static boolean isFistTimeOpenApp;
    public static String DEVELOPER_ID = "TopLibrary";    // TODO  change this developer id in release version

    public static void readOtherStory(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + DEVELOPER_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:" + DEVELOPER_ID)));
        }
    }

    public static boolean shouldShowInterAds(Context context) {
        countAction++;
        if (!isFistTimeOpenApp && countAction == TIME_TO_SHOW_INTER) {
            countAction = 0;
            return true;
        }
        return false;
    }
}
