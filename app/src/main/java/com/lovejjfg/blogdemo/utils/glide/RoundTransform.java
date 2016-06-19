/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lovejjfg.blogdemo.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Joe on 2016-05-24
 * Email: lovejjfg@163.com
 * http://stackoverflow.com/a/25806229/409481
 */
public class RoundTransform extends BitmapTransformation {
    private int cornerRadius;
    private RectF rectF = new RectF();

    public RoundTransform(Context context, int round) {
        super(context);
        this.cornerRadius = round;
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int width = source.getWidth();
        int height = source.getHeight();
        rectF.set(0, 0, width, height);

        Bitmap squared = Bitmap.createBitmap(source, 0, 0, width, height);

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint
                .ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader
                .TileMode.CLAMP));
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);


        return result;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
