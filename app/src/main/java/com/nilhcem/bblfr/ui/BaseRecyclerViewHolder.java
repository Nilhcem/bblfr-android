package com.nilhcem.bblfr.ui;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.BBLApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

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

    public void bindData(T data) {
        // Should be implemented on sub-classes.
    }

    public void setOnClickListener(View.OnClickListener l) {
        itemView.setOnClickListener(l);
        itemView.setTag(this);
    }
}
