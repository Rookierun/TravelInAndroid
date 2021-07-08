package com.rookie.travelinandroid.super_ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.rookie.travelinandroid.super_ui.utils.UiUtils;

import androidx.annotation.Nullable;

/**
 * 仪表盘
 * 1。弧度
 * 2。刻度
 * 3。指针
 */

public class DashBoard extends View {
    private static final double LINE_LENGTH = UiUtils.dp2Ppx(120);
    Paint mPaint;
    Paint centerPoint;
    private static final int START_ANGLE = 120;
    private static final float RADIUS = UiUtils.dp2Ppx(150);
    private static final int SWEEP_ANGLE = 300;
    private static final float ARC_WIDTH = UiUtils.dp2Ppx(4);
    private static final float MARK_WIDTH = UiUtils.dp2Ppx(2);
    private static final float MARK_HEIGHT = UiUtils.dp2Ppx(10);
    private static final int MARK_COUNT = 20;

    public DashBoard(Context context) {
        this(context, null);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ARC_WIDTH);
        centerPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPoint.setStyle(Paint.Style.FILL_AND_STROKE);
        centerPoint.setStrokeWidth(UiUtils.dp2Ppx(2));


    }

    RectF rect;
    PathDashPathEffect effect;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect = new RectF(getWidth() / 2.0f - RADIUS, getHeight() / 2.0f - RADIUS, getWidth() / 2.0f + RADIUS, getHeight() / 2.0f + RADIUS);
        Path dashPath = new Path();
        dashPath.addRect(0, 0, MARK_WIDTH, MARK_HEIGHT, Path.Direction.CW);
        Path arcPath = new Path();
        arcPath.addArc(rect, START_ANGLE, SWEEP_ANGLE);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(arcPath, false);
        float length = pathMeasure.getLength();
        Log.e("test", "advance:" + length / MARK_COUNT);
        effect = new PathDashPathEffect(dashPath, (length - MARK_WIDTH) / MARK_COUNT, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧
        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, mPaint);
        //画刻度
        mPaint.setPathEffect(effect);
        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, mPaint);
        mPaint.setPathEffect(null);
        //画圆心
        canvas.drawPoint(getWidth() / 2.0f, getHeight() / 2.0f, centerPoint);
        //画指针
        canvas.drawLine(getWidth() / 2.0f, getHeight() / 2.0f,
                ((float) (Math.cos(Math.toRadians(getAngleFromMark(5))) * LINE_LENGTH + getWidth() / 2f)),
                ((float) (Math.sin(Math.toRadians(getAngleFromMark(5))) * LINE_LENGTH + getHeight() / 2f)),
                mPaint);
    }

    private int getAngleFromMark(int mark) {
        return ((int) (90 + START_ANGLE / 2 + (360 - START_ANGLE) / 20 * mark));
    }


}
