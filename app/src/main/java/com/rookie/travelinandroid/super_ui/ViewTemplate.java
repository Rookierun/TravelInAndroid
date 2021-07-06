package com.rookie.travelinandroid.super_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewTemplate extends View {
    public ViewTemplate(Context context) {
        this(context, null);
    }

    public ViewTemplate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewTemplate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
