package com.mjebooksdk.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {
    // TODO  change this developer id in release version
    public static String DEVELOPER_ID = "TopLibrary";

    public static void readOtherStory(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + DEVELOPER_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:" + DEVELOPER_ID)));
        }
    }
}
