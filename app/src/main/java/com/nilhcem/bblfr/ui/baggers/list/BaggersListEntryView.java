package com.nilhcem.bblfr.ui.baggers.list;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class BaggersListEntryView extends BaseRecyclerViewHolder<BaggersListEntry> {

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
    public void bindData(@NonNull BaggersListEntry data) {
        setTextIfAny(mName, data.name);
        setTextIfAny(mLinks, data.links);
        setTextIfAny(mSessions, data.sessions);
        setTextIfAny(mBio, data.bio);
        setTextIfAny(mLocations, data.locations);
    }

    private void setTextIfAny(TextView textview, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            textview.setVisibility(View.GONE);
        } else {
            textview.setText(text);
            textview.setVisibility(View.VISIBLE);
        }
    }
}
