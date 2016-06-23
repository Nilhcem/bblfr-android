package com.nilhcem.bblfr.core.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    public static boolean wasInstalledFromGooglePlay(Context context) {
        String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return "com.android.vending".equals(installer);
    }

    public static boolean isGeolocAllowed(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
