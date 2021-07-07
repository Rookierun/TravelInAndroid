package com.rookie.travelinandroid.super_ui.animator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

public class ProvinceView extends View {
    private String city = "北京市--北京 ";

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        invalidate();
    }

    public ProvinceView(Context context) {
        this(context, null);
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint;

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        ObjectAnimator animator = ObjectAnimator.ofObject(this, "city", new ProvinceTypeEvaluator(), "广东省--广州 ");
        animator.setDuration(1500);
        animator.setStartDelay(1000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(city, getWidth() / 2, getHeight() / 2, paint);
    }
}
