package com.nilhcem.bblfr.ui.baggers.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class BaggersListHeaderView extends BaseRecyclerViewHolder<String> {

    @Inject Picasso mPicasso;

    @BindView(R.id.baggers_list_city_header) ImageView mCityImage;

    public BaggersListHeaderView(ViewGroup parent) {
        super(parent, R.layout.baggers_list_header);
        BBLApplication.get(parent.getContext()).component().inject(this);
    }

    @Override
    public void bindData(String data) {
        super.bindData(data);
        if (data == null) {
            return;
        }

        mCityImage.setVisibility(View.INVISIBLE);
        if (data.isEmpty()) {
            setPlaceHolder();
        } else {
            mPicasso.load(data).into(mCityImage, new Callback() {
                @Override
                public void onSuccess() {
                    mCityImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    setPlaceHolder();
                }
            });
        }
    }

    private void setPlaceHolder() {
        mPicasso.load(R.drawable.location_placeholder).into(mCityImage, new Callback.EmptyCallback() {
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
