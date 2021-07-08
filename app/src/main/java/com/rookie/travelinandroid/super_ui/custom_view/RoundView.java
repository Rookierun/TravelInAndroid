package com.rookie.travelinandroid.super_ui.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.rookie.travelinandroid.R;
import com.rookie.travelinandroid.super_ui.utils.UiUtils;

import androidx.annotation.Nullable;

public class RoundView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float WIDTH = UiUtils.dp2Ppx(300);
    private static final float WIDTH_OVAL = UiUtils.dp2Ppx(200);
    private static final float EDGE_WIDTH = UiUtils.dp2Ppx(10);
    private float centerX, centerY;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        getLauncherBitmap((int) WIDTH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    Bitmap bitmap;
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, WIDTH_OVAL / 2, mPaint);
        int layer = canvas.saveLayer(centerX - WIDTH_OVAL / 2,
                centerY - WIDTH_OVAL / 2,
                centerX + WIDTH_OVAL / 2,
                centerY + WIDTH_OVAL / 2, mPaint);
        canvas.drawCircle(centerX, centerY, WIDTH_OVAL / 2 - EDGE_WIDTH/2, mPaint);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, centerX - WIDTH / 2f, centerY - WIDTH / 2f, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layer);

//        canvas.drawBitmap(bitmap, centerX - WIDTH / 2f, centerY - WIDTH / 2f, mPaint);

    }

    private void getLauncherBitmap(int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
    }
}
