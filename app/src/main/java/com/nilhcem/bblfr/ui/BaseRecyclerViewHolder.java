package com.nilhcem.bblfr.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.BBLApplication;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private T mData;

    protected BaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int resource, boolean withDi) {
        this(parent, resource, withDi, true);
    }

    protected BaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int resource, boolean withDi, boolean withVi) {
        super(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
        if (withVi) {
            ButterKnife.inject(this, itemView);
        }
        if (withDi) {
            BBLApplication.get(itemView.getContext()).inject(this);
        }
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    public void bindData(T data) {
        // Should be implemented on sub-classes.
        mData = data;
    }

    public T getData() {
        return mData;
    }

    public void setOnClickListener(View.OnClickListener l) {
        itemView.setOnClickListener(l);
        itemView.setTag(this);
    }
}
