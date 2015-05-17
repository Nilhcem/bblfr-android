package com.nilhcem.bblfr.ui.about;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nilhcem.bblfr.R;

public enum AboutPagerStepEntry {

    FIND_A_SPEAKER(R.string.about_step1_title, R.string.about_step1_content, R.drawable.about_step_1),
    BUY_HIM_SOME_FOOD(R.string.about_step2_title, R.string.about_step2_content, R.drawable.about_step_2),
    HAVE_A_GOOD_TIME(R.string.about_step3_title, R.string.about_step3_content, R.drawable.about_step_3),
    QUOTE(0, 0, 0);

    public final int title;
    public final int content;
    public final int image;

    AboutPagerStepEntry(@StringRes int titleRes, @StringRes int contentRes, @DrawableRes int imageRes) {
        title = titleRes;
        content = contentRes;
        image = imageRes;
    }

    public static int[] getTitles() {
        AboutPagerStepEntry[] entries = AboutPagerStepEntry.values();
        int[] titles = new int[entries.length];
        for (int i = 0; i < entries.length; i++) {
            titles[i] = entries[i].title;
        }
        return titles;
    }

    public static int[] getContents() {
        AboutPagerStepEntry[] entries = AboutPagerStepEntry.values();
        int[] contents = new int[entries.length];
        for (int i = 0; i < entries.length; i++) {
            contents[i] = entries[i].content;
        }
        return contents;
    }
}
