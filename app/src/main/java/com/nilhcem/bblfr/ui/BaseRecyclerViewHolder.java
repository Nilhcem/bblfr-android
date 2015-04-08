package com.nilhcem.bblfr.ui;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nilhcem.bblfr.BBLApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    @Inject protected Picasso mPicasso;

    protected BaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int resource) {
        super(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
        BBLApplication.get(itemView.getContext()).inject(this);
        ButterKnife.inject(this, itemView);
    }

    public abstract void bindData(T data);
}
