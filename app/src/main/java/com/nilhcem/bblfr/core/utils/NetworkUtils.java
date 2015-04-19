package com.nilhcem.bblfr.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.nilhcem.bblfr.BuildConfig;

import java.util.Locale;

public class NetworkUtils {

    private static final String TWITTER_LINK_PREFIX = "http://www.twitter.com/#!/%s";

    private NetworkUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getAbsoluteUrl(String url) {
        String absoluteUrl = url;

        if (!TextUtils.isEmpty(absoluteUrl) && !absoluteUrl.startsWith("http")) {
            absoluteUrl = BuildConfig.WS_ENDPOINT + absoluteUrl;
        }
        return absoluteUrl;
    }

    public static String getTwitterUrl(String username) {
        return String.format(Locale.US, TWITTER_LINK_PREFIX, username);
    }
}
