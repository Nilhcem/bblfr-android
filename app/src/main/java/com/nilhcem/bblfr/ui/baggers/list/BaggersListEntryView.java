package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.ui.picasso.RoundedTransformation;
import com.nilhcem.bblfr.core.utils.IntentUtils;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class BaggersListEntryView extends BaseRecyclerViewHolder<BaggersListEntry> {

    @Inject Picasso mPicasso;

    @InjectView(R.id.bagger_entry_name) TextView mName;
    @InjectView(R.id.bagger_entry_links) TextView mLinks;
    @InjectView(R.id.bagger_entry_bio) TextView mBio;
    @InjectView(R.id.bagger_entry_sessions) TextView mSessions;
    @InjectView(R.id.bagger_entry_locations) TextView mLocations;
    @InjectView(R.id.bagger_entry_picture) ImageView mPicture;
    @InjectView(R.id.bagger_entry_invite_button) Button mContactButton;

    public BaggersListEntryView(ViewGroup parent) {
        super(parent, R.layout.baggers_list_item, true);

        mBio.setMovementMethod(LinkMovementMethod.getInstance());
        mLinks.setMovementMethod(LinkMovementMethod.getInstance());
        mSessions.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void bindData(@NonNull BaggersListEntry data) {
        super.bindData(data);
        setTextIfAny(mName, data.name);
        setTextIfAny(mLinks, data.links);
        setTextIfAny(mSessions, data.sessions);
        setTextIfAny(mBio, data.bio);
        setTextIfAny(mLocations, data.locations);
        mContactButton.setTag(data.email);

        mPicasso.load(data.pictureUrl).transform(new RoundedTransformation(4, 0)).noFade().into(mPicture, new Callback() {
            @Override
            public void onSuccess() {
                mPicture.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                mPicture.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.bagger_entry_invite_button)
    public void onInviteButtonClicked(Button button) {
        Context context = getContext();
        String chooser = context.getString(R.string.baggers_list_contact_chooser_title);
        String subject = context.getString(R.string.baggers_list_contact_email_subject);
        IntentUtils.startEmailIntent(context, chooser, (String) button.getTag(), subject);
    }

    void setTextIfAny(TextView textview, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            textview.setVisibility(View.GONE);
        } else {
            textview.setText(text);
            textview.setVisibility(View.VISIBLE);
        }
    }
}
