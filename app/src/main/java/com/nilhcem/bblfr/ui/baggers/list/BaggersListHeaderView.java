package com.nilhcem.bblfr.ui.baggers.list;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

public class BaggersListHeaderView extends BaseRecyclerViewHolder<String> {

    @Inject Picasso mPicasso;
    @InjectView(R.id.baggers_list_city_header) ImageView mCityImage;

    public BaggersListHeaderView(ViewGroup parent) {
        super(parent, R.layout.baggers_list_header);
        BBLApplication.get(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(String data) {
        if (!TextUtils.isEmpty(data)) {
            mPicasso.load(data).into(mCityImage, new Callback() {
                @Override
                public void onSuccess() {
                    mCityImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    mCityImage.setVisibility(View.GONE);
                }
            });
        }
    }
}
