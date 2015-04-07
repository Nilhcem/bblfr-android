package com.nilhcem.bblfr.core.utils;

import android.text.TextUtils;

import com.nilhcem.bblfr.BuildConfig;

public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getAbsoluteUrl(String url) {
        String absoluteUrl = url;

        if (!TextUtils.isEmpty(absoluteUrl)) {
            if (!absoluteUrl.startsWith("http")) {
                absoluteUrl = BuildConfig.WS_ENDPOINT + absoluteUrl;
            }
        }
        return absoluteUrl;
    }
}
