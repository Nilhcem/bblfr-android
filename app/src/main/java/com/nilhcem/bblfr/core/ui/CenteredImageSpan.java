package com.nilhcem.bblfr.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ReplacementSpan;

public class CenteredImageSpan extends ReplacementSpan {

    private final Drawable mDrawable;

    public CenteredImageSpan(final Drawable drawable) {
        mDrawable = drawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    @Override
    public int getSize(final Paint paint, final CharSequence text, final int start, final int end, final Paint.FontMetricsInt fm) {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public void draw(final Canvas canvas, final CharSequence text, final int start, final int end, final float x, final int top, final int y, final int bottom, final Paint paint) {
        final Paint.FontMetrics metrics = paint.getFontMetrics();
        final float padding = (metrics.descent - metrics.ascent - mDrawable.getIntrinsicHeight()) / 2f;
        final float transY = bottom - mDrawable.getIntrinsicHeight() - padding;
        canvas.save();
        canvas.translate(x, transY);
        mDrawable.draw(canvas);
        canvas.restore();
    }
}
