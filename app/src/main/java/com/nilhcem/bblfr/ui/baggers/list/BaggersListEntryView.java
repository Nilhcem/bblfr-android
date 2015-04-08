package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.ui.CenteredImageSpan;
import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.core.utils.StringUtils;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Website;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import java.util.List;
import java.util.Locale;

import butterknife.InjectView;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.nilhcem.bblfr.core.utils.CompatibilityUtils.getDrawable;
import static com.nilhcem.bblfr.core.utils.StringUtils.ZERO_WIDTH_SPACE;
import static com.nilhcem.bblfr.core.utils.StringUtils.addLineSeparator;

public class BaggersListEntryView extends BaseRecyclerViewHolder<Bagger> {

    @InjectView(R.id.bagger_entry_name) TextView mName;
    @InjectView(R.id.bagger_entry_links) TextView mLinks;
    @InjectView(R.id.bagger_entry_bio) TextView mBio;
    @InjectView(R.id.bagger_entry_sessions) TextView mSessions;
    @InjectView(R.id.bagger_entry_locations) TextView mLocations;
    @InjectView(R.id.bagger_entry_invite_button) Button mInvite;

    public BaggersListEntryView(ViewGroup parent) {
        super(parent, R.layout.baggers_list_item);

        mBio.setMovementMethod(LinkMovementMethod.getInstance());
        mLinks.setMovementMethod(LinkMovementMethod.getInstance());
        mSessions.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void bindData(@NonNull Bagger data) {
        Context context = itemView.getContext();

        setTextIfAny(mName, data.name);
        setTextIfAny(mLinks, linksToSpannable(context, data.websites, data.twitter));
        setTextIfAny(mSessions, sessionsToSpannable(context, data.sessions));
        setTextIfAny(mBio, htmlTextToSpannable(data.bio));
        setTextIfAny(mLocations, locationsToSpannable(context, data.location));
    }

    private void setTextIfAny(TextView textview, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            textview.setVisibility(View.GONE);
        } else {
            textview.setText(text);
            textview.setVisibility(View.VISIBLE);
        }
    }

    public static Spanned htmlTextToSpannable(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        return Html.fromHtml(source);
    }

    public static Spanned locationsToSpannable(Context context, String location) {
        if (TextUtils.isEmpty(location)) {
            return null;
        }
        return htmlTextToSpannable(context.getString(R.string.baggers_list_location, location));
    }

    public static CharSequence sessionsToSpannable(@NonNull Context context, List<Session> sessions) {
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

    public static CharSequence linksToSpannable(Context context, List<Website> websites, String twitter) {
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

    private static void addLinkToSpannable(SpannableStringBuilder sb, Drawable icon, CharSequence text) {
        addLineSeparator(sb);
        int start = addLineSeparator(sb);
        sb.append(ZERO_WIDTH_SPACE);
        sb.append(text);
        sb.setSpan(new CenteredImageSpan(icon), start, start + 1, SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
