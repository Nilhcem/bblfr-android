package com.nilhcem.bblfr.core.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

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
}
