package com.nilhcem.bblfr.ui.baggers.list;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaggersListAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    @IntDef({TYPE_ITEM, TYPE_HEADER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {}

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;

    private String mHeaderPictureUrl;
    private List<BaggersListEntry> mItems = Collections.emptyList();

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new BaggersListEntryView(parent);
        } else if (viewType == TYPE_HEADER) {
            return new BaggersListHeaderView(parent);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (isPositionHeader(position)) {
            BaggersListHeaderView headerView = (BaggersListHeaderView) holder;
            headerView.bindData(NetworkUtils.getAbsoluteUrl(mHeaderPictureUrl));
        } else {
            BaggersListEntryView entryView = (BaggersListEntryView) holder;
            entryView.bindData(mItems.get(position - 1));
            // "- 1" as we are taking header into account so all of our items are correctly positioned
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
        // "+ 1" for the header
    }

    @Override
    @ViewType
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public void updateItems(String headerPictureUrl, List<BaggersListEntry> items) {
        mHeaderPictureUrl = headerPictureUrl;

        if (items == null) {
            mItems = Collections.emptyList();
        } else {
            mItems = new ArrayList<>(items);
        }
        notifyDataSetChanged();
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
