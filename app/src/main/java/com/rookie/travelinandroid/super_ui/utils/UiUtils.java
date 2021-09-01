package com.rookie.travelinandroid.super_ui.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.rookie.travelinandroid.R;

public class UiUtils {
    public static float dp2Ppx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmap(int targetWidth, int resId, Resources resources) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth;
        return  BitmapFactory.decodeResource(resources, resId, options);
    }
}
