package com.nilhcem.bblfr.core.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.ui.splashscreen.SplashscreenActivity;

import java.util.Locale;

public class IntentUtils {

    private static final String MAILTO_SCHEME = "mailto";

    private IntentUtils() {
        throw new UnsupportedOperationException();
    }

    public static void startEmailIntent(Context context, String chooserTitle, String recipient, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(MAILTO_SCHEME, recipient, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        context.startActivity(Intent.createChooser(intent, chooserTitle));
    }

    public static void startSiteIntent(Context context, String website) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        context.startActivity(intent);
    }

    public static void startGooglePlayIntent(Context context) {
        try {
            startSiteIntent(context, String.format(Locale.US, "market://details?id=%s", BuildConfig.APPLICATION_ID));
        } catch (ActivityNotFoundException e) {
            startSiteIntent(context, String.format(Locale.US, "http://play.google.com/store/apps/details?id=%s", BuildConfig.APPLICATION_ID));
        }
    }

    public static void restartApp(Context context) {
        Intent intent = new Intent(context, SplashscreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
