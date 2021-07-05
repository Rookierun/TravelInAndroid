package com.rookie.travelinandroid.super_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ExplosionView extends View {
    public ExplosionView(Context context) {
        this(context,null);
    }

    public ExplosionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExplosionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
