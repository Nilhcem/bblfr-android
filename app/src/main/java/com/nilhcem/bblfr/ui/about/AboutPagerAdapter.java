package com.nilhcem.bblfr.ui.about;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AboutPagerAdapter extends PagerAdapter {

    private List<AboutPagerStepEntry> mEntries;

    public AboutPagerAdapter() {
        mEntries = new ArrayList<>();
        for (AboutPagerStepEntry entry : AboutPagerStepEntry.values()) {
            mEntries.add(entry);
        }
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        int imageRes = mEntries.get(position).image;
        if (imageRes != 0) {
            imageView.setImageResource(imageRes);
        }

        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
