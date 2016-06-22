package com.nilhcem.bblfr.core.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;

public class StringUtils {

    public static final String COMMA_SEPARATOR = ", ";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String HTML_LINE_SEPARATOR = "<br />";
    public static final String ZERO_WIDTH_SPACE = "\u200B";

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static String appendOptional(String first, String second) {
        return appendOptional(first, second, null);
    }

    public static String appendOptional(String first, String second, String separator) {
        StringBuilder sb = new StringBuilder();

        if (!TextUtils.isEmpty(first)) {
            sb.append(first);
        }

        if (!TextUtils.isEmpty(second)) {
            if (sb.length() > 0 && separator != null) {
                sb.append(separator);
            }
            sb.append(second);
        }
        return sb.toString();
    }

    public static String strJoin(List<String> entries, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean addSeparatorFlag = false;

        if (entries != null) {
            for (String entry : entries) {
                if (!TextUtils.isEmpty(entry)) {
                    if (addSeparatorFlag && separator != null) {
                        sb.append(separator);
                    } else {
                        addSeparatorFlag = true;
                    }
                    sb.append(entry);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Computes the start index of the spannable and adds a line separator (only if needed when force is set to false).
     */
    public static int addLineSeparator(SpannableStringBuilder sb, boolean force) {
        int start = sb.length();
        if (force || start > 0) {
            sb.append(LINE_SEPARATOR);
            start++;
        }
        return start;
    }

    public static int addLineSeparator(SpannableStringBuilder sb) {
        return addLineSeparator(sb, false);
    }

    public static Spanned createSpannedHtmlLink(String name, String url) {
        return CompatibilityUtils.fromHtml(String.format(Locale.US, "<a href=\"%s\">%s</a>", url, name));
    }
}
