package com.nilhcem.bblfr.core.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}
