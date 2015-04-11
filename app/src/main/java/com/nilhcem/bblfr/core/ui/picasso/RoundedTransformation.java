package com.nilhcem.bblfr.core.ui.picasso;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import java.util.Locale;

/**
 * Picasso Transformation that enables hardware accelerated rounded corners.
 * <p>
 * Original idea here : http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
 * </p>
 */
public class RoundedTransformation implements com.squareup.picasso.Transformation {
    private final int radius;
    private final int margin;

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return String.format(Locale.US, "rounded(radius=%d, margin=%d)", radius, margin);
    }
}
