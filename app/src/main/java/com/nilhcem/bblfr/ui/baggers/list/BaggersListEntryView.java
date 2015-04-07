package com.nilhcem.bblfr.ui.baggers.list;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class BaggersListEntryView extends BaseRecyclerViewHolder<Bagger> {

    @InjectView(R.id.bagger_entry_name) TextView mName;

    public BaggersListEntryView(ViewGroup parent) {
        super(parent, R.layout.baggers_list_item);
    }

    @Override
    public void bindData(Bagger data) {
        mName.setText(data.name);
    }
}
