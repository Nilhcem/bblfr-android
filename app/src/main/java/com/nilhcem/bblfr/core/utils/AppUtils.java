package com.nilhcem.bblfr.core.utils;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.nilhcem.bblfr.BuildConfig;

import java.util.Locale;

public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getVersion() {
        return String.format(Locale.US, "%s (#%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    }

    public static boolean hasGooglePlayServices(Context context) {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    public static boolean wasInstalledFromGooglePlay(Context context) {
        String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return "com.android.vending".equals(installer);
    }
}
