package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class FilterEntryView extends BaseRecyclerViewHolder<Tag> {

    @InjectView(R.id.filter_entry_name) TextView mName;

    public FilterEntryView(ViewGroup parent) {
        super(parent, R.layout.filter_list_item);
    }

    @Override
    public void bindData(Tag data) {
        mName.setText("#" + data.name);
    }
}
