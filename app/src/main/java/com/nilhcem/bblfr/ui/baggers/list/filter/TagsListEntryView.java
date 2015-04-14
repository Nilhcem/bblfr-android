package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class TagsListEntryView extends BaseRecyclerViewHolder<TagsListEntry> {

    @InjectView(R.id.filter_entry_layout) ViewGroup mContainer;
    @InjectView(R.id.filter_entry_name) TextView mName;

    private TagsListEntry mTag;

    public TagsListEntryView(ViewGroup parent) {
        super(parent, R.layout.tags_list_item, false);
    }

    @Override
    public void bindData(TagsListEntry data) {
        mName.setText(data.name);
        mTag = data;

        boolean activated = mTag.isActivated();
        mContainer.setActivated(activated);
        mName.setTextAppearance(getContext(), activated ? R.style.FilterEntry_Selected : R.style.FilterEntry);
    }

    public TagsListEntry getTag() {
        return mTag;
    }
}
