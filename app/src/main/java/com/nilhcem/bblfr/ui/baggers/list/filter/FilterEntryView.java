package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import java.util.Locale;

import butterknife.InjectView;

public class FilterEntryView extends BaseRecyclerViewHolder<Tag> {

    @InjectView(R.id.filter_entry_layout) ViewGroup mContainer;
    @InjectView(R.id.filter_entry_name) TextView mName;

    private Tag mTag;

    public FilterEntryView(ViewGroup parent) {
        super(parent, R.layout.filter_list_item, false);
    }

    @Override
    public void bindData(Tag data) {
        mName.setText(String.format(Locale.US, "#%s", data.name));
        mTag = data;
    }

    public Tag getTag() {
        return mTag;
    }

    public void setIsSelected(boolean isSelected) {
        mContainer.setActivated(isSelected);
        mName.setTextAppearance(itemView.getContext(), isSelected ? R.style.FilterEntry_Selected : R.style.FilterEntry);
    }
}
