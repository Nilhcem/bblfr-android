package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.Bind;

public class TagsListEntryView extends BaseRecyclerViewHolder<TagsListEntry> {

    @Bind(R.id.filter_entry_layout) ViewGroup mContainer;
    @Bind(R.id.filter_entry_name) TextView mName;

    public TagsListEntryView(ViewGroup parent) {
        super(parent, R.layout.tags_list_item);
    }

    @Override
    public void bindData(TagsListEntry data) {
        super.bindData(data);
        mName.setText(data.name);

        boolean activated = data.isActivated();
        mContainer.setActivated(activated);
        mName.setTextAppearance(getContext(), activated ? R.style.FilterEntry_Selected : R.style.FilterEntry);
    }
}
