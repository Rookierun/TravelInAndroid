package com.rookie.travelinandroid.super_ui.custom_view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.rookie.travelinandroid.R;
import com.rookie.travelinandroid.super_ui.utils.UiUtils;

import androidx.annotation.Nullable;


/**
 * 支持缩放的ImageView
 * 1。通过缩放系数控制需要绘制ImageView的大小,实际是对canvas进行scale操作
 * 2。使用GestureDetector实现图片的拖动与fling（需Scroller配合）
 * 3。使用动画过渡图片的缩放效果
 */
public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float SIZE_SMALL_MODE = UiUtils.dp2Ppx(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;
    private int originalOffsetX, originalOffsetY;//xy的初始偏移，使得图片能够在屏幕正中间绘制
    private boolean bigMode;
    private float bigScale, smallScale;
    private float scaleFraction;//用来执行缩放时候的动画
    private ObjectAnimator scaleAnimator;

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        return scaleAnimator;
    }

    public ScalableImageView(Context context) {
        this(context, null);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Bitmap bitmap;
    GestureDetector gestureDetector;

    private void init() {
        bitmap = UiUtils.getBitmap((int) SIZE_SMALL_MODE, R.mipmap.ic_launcher, getResources());
        gestureDetector = new GestureDetector(getContext(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里不使用bigMode来计算scale的缩放系数，因为那样会把scale一下写死，而是去动画效果，需要将scaleFraction与scale系数联系起来
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, mPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (int) ((w - bitmap.getWidth()) / 2);
        originalOffsetY = (int) ((h - bitmap.getHeight()) / 2);
        //判断是横屏图片还是竖屏图片
        if (bitmap.getWidth() * 1.0f / bitmap.getHeight() > w * 1.0f / h) {
            smallScale = w * 1.0f / bitmap.getWidth();
            bigScale = h * 1.0f / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            bigScale = w * 1.0f / bitmap.getWidth() * OVER_SCALE_FACTOR;
            smallScale = h * 1.0f / bitmap.getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        bigMode = !bigMode;
        if (bigMode) {
            //放大
            getScaleAnimator().start();
        } else {
            //缩小
            getScaleAnimator().reverse();
        }
        invalidate();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}