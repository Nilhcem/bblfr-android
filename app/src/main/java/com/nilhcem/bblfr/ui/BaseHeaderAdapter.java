package com.nilhcem.bblfr.ui;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseHeaderAdapter<H, I> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    @IntDef({TYPE_ITEM, TYPE_HEADER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_HEADER = 2;

    protected H mHeader;
    protected List<I> mItems = Collections.emptyList();

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return onCreateEntryView(parent);
        } else if (viewType == TYPE_HEADER) {
            return onCreateHeaderView(parent);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (isPositionHeader(position)) {
            holder.bindData(mHeader);
        } else {
            holder.bindData(mItems.get(position - 1));
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

    public void updateItems(H header, List<I> items) {
        mHeader = header;

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

    protected abstract BaseRecyclerViewHolder onCreateEntryView(ViewGroup parent);

    protected abstract BaseRecyclerViewHolder onCreateHeaderView(ViewGroup parent);
}
