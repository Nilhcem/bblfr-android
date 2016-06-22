package com.nilhcem.bblfr.core.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.N;

public class CompatibilityUtils {

    private CompatibilityUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isCompatible(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT >= apiLevel;
    }

    @TargetApi(LOLLIPOP)
    public static Drawable getDrawable(Context context, int id) {
        if (isCompatible(LOLLIPOP)) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    @TargetApi(N)
    public static Spanned fromHtml(String source) {
        if (isCompatible(N)) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
}
