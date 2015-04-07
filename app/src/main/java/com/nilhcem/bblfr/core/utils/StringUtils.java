package com.nilhcem.bblfr.core.utils;

import android.text.TextUtils;

import java.util.List;

public class StringUtils {

    public static final String COMMA_SEPARATOR = ", ";
    public static final String HTML_LINE_SEPARATOR = "<br />";

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static String appendOptional(String first, String second, String separator) {
        StringBuilder sb = new StringBuilder();

        if (!TextUtils.isEmpty(first)) {
            sb.append(first);
        }

        if (!TextUtils.isEmpty(second)) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(second);
        }
        return sb.toString();
    }

    public static String strJoin(List<String> entries, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean addSeparatorFlag = false;

        for (String entry : entries) {
            if (!TextUtils.isEmpty(entry)) {
                if (addSeparatorFlag) {
                    sb.append(separator);
                } else {
                    addSeparatorFlag = true;
                }
                sb.append(entry);
            }
        }
        return sb.toString();
    }
}
