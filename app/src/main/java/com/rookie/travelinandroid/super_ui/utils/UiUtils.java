package com.rookie.travelinandroid.super_ui.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class UiUtils {
    public static float dp2Ppx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
