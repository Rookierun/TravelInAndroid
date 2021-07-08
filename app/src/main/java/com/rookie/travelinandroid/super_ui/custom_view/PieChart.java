package com.rookie.travelinandroid.super_ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.rookie.travelinandroid.super_ui.utils.UiUtils;

import androidx.annotation.Nullable;

/**
 * 饼状图
 * 多个扇形区域的拼接
 */
public class PieChart extends View {
    private static final float RADIUS = UiUtils.dp2Ppx(150);
    private static final float LENGTH = UiUtils.dp2Ppx(10);
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int[] angles = {60, 100, 120, 80};
    int[] colors = {Color.RED, Color.GRAY, Color.GREEN, Color.BLUE};
    RectF bounds;
    public static final int PULLED_OUT_INDEX = 2;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds = new RectF(getWidth() / 2.0f - RADIUS, getHeight() / 2.0f - RADIUS, getWidth() / 2.0f + RADIUS, getHeight() / 2.0f + RADIUS);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {

            int angle = angles[i];
            mPaint.setColor(colors[i]);
            canvas.save();
            if (i == PULLED_OUT_INDEX) {
                float dx = (float) (Math.cos(Math.toRadians(currentAngle + angle / 2.0f)) * LENGTH);
                float dy = (float) (Math.sin(Math.toRadians(currentAngle + angle / 2.0f)) * LENGTH);
                canvas.translate(dx, dy);
            }

            canvas.drawArc(bounds, currentAngle, angle, true, mPaint);
            canvas.restore();
            currentAngle += angle;
        }
    }
}
