package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.ui.CenteredImageSpan;
import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.core.utils.StringUtils;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Website;

import java.util.List;
import java.util.Locale;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.nilhcem.bblfr.core.utils.CompatibilityUtils.getDrawable;
import static com.nilhcem.bblfr.core.utils.StringUtils.ZERO_WIDTH_SPACE;
import static com.nilhcem.bblfr.core.utils.StringUtils.addLineSeparator;

public class BaggersListEntry {

    // Original data
    public final Bagger bagger;

    // Optimized data
    public final CharSequence name;
    public final CharSequence links;
    public final CharSequence sessions;
    public final CharSequence bio;
    public final CharSequence locations;
    public final String pictureUrl;

    public BaggersListEntry(Context context, Bagger bagger) {
        this.bagger = bagger;

        name = bagger.name;
        pictureUrl = NetworkUtils.getAbsoluteUrl(bagger.picture);
        links = linksToSpannable(context, bagger.websites, bagger.twitter);
        sessions = sessionsToSpannable(context, bagger.sessions);
        bio = htmlTextToSpannable(bagger.bio);
        locations = locationsToSpannable(context, bagger.location);
    }

    private Spanned htmlTextToSpannable(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        return Html.fromHtml(source);
    }

    private Spanned locationsToSpannable(Context context, String location) {
        if (TextUtils.isEmpty(location)) {
            return null;
        }
        return htmlTextToSpannable(context.getString(R.string.baggers_list_location, location));
    }

    private CharSequence sessionsToSpannable(@NonNull Context context, List<Session> sessions) {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        if (sessions != null) {
            Drawable bulletpoint = getDrawable(context, R.drawable.baggers_bulletpoint_ic);

            for (Session session : sessions) {
                // Title
                String title = session.title;
                if (!TextUtils.isEmpty(title)) {
                    addLineSeparator(sb);
                    int start = addLineSeparator(sb, true);

                    // Add title (bulletpoint placeholder + title + style)
                    sb.append(ZERO_WIDTH_SPACE);
                    sb.append(Html.fromHtml(title));
                    sb.setSpan(new TextAppearanceSpan(context, R.style.BaggersSessions_Title), start, sb.length(), SPAN_EXCLUSIVE_EXCLUSIVE);

                    // Replace the placeholder with the actual image
                    sb.setSpan(new CenteredImageSpan(bulletpoint), start, start + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                // Summary
                String summary = session.summary;
                if (!TextUtils.isEmpty(summary)) {
                    int start = addLineSeparator(sb);
                    sb.append(Html.fromHtml(summary));
                    sb.setSpan(new TextAppearanceSpan(context, R.style.BaggersSessions_Summary), start, sb.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return sb;
    }

    private CharSequence linksToSpannable(Context context, List<Website> websites, String twitter) {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        if (websites != null) {
            Drawable icon = getDrawable(context, R.drawable.baggers_link_ic);
            for (Website website : websites) {
                addLinkToSpannable(sb, icon, StringUtils.createSpannedHtmlLink(website.title, website.href));
            }
        }

        if (!TextUtils.isEmpty(twitter)) {
            Drawable icon = getDrawable(context, R.drawable.baggers_twitter_ic);
            Spanned text = StringUtils.createSpannedHtmlLink(String.format(Locale.US, "@%s", twitter), NetworkUtils.getTwitterUrl(twitter));
            addLinkToSpannable(sb, icon, text);
        }
        return sb;
    }

    private void addLinkToSpannable(SpannableStringBuilder sb, Drawable icon, CharSequence text) {
        addLineSeparator(sb);
        int start = addLineSeparator(sb);
        sb.append(ZERO_WIDTH_SPACE);
        sb.append(text);
        sb.setSpan(new CenteredImageSpan(icon), start, start + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
