package com.rookie.travelinandroid.super_ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.rookie.travelinandroid.R;

import androidx.annotation.Nullable;

/**
 * 三步走：
 * 1。旋转动画
 * 2。扩散聚合动画
 * 3。水波纹动画
 */
public class LoadingView extends View {
    private Paint mSmallCirclePaint;//旋转的小圆画笔
    private int mBackgroundColor = Color.WHITE;
    private int[] mSmallCircleColors;//小球颜色数组
    private ValueAnimator mAnimator;//旋转。扩散。水波纹动画
    private float mSmallCircleRadius = 18;
    private float mSmallCircleRotatedRadius = 90;
    private float mCurrentRotatedRadius = mSmallCircleRotatedRadius;
    private long mSmallCircleRotatedDuration = 1200;//小圆的旋转动画时长
    private float mCenterX, mCenterY;//圆心坐标
    private float mMaxExpandCircleRadius;//扩散圆最大的半径值为该view对角线的一半
    private LoadingState mLoadingState;
    private float mCurrentRotatedAngleOfSmallCircle;//小圆当前旋转的角度
    private float mCurrentExpandCircleRadius = 0F;//扩散圆的半径
    private Paint mExpandCirclePaint;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSmallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mExpandCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mExpandCirclePaint.setStyle(Paint.Style.STROKE);
        mExpandCirclePaint.setColor(mBackgroundColor);
        mSmallCircleColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w * 1.0f / 2;
        mCenterY = h * 1.0f / 2;
        mMaxExpandCircleRadius = (float) (Math.hypot(w, h) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLoadingState == null) {
            mLoadingState = new RotatedState();
        }
        mLoadingState.drawState(canvas);
    }

    private abstract class LoadingState {
        abstract void drawState(Canvas canvas);
    }

    //第一步，旋转动画
    private class RotatedState extends LoadingState {
        public RotatedState() {
            mAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            mAnimator.setDuration(mSmallCircleRotatedDuration);
            mAnimator.setRepeatCount(2);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //修改小球的旋转角度，然后重绘
                    Object animatedValue = animation.getAnimatedValue();
                    mCurrentRotatedAngleOfSmallCircle = (float) animatedValue;
                    invalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //切换到下一步的聚合扩散动画
                    mLoadingState = new MergeInAndOutState();
                }
            });
            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    private void drawCircles(Canvas canvas) {
        int colorsLength = mSmallCircleColors.length;
        float averageAngle = (float) (Math.PI * 2.0f / colorsLength);
        for (int i = 0; i < colorsLength; i++) {
            float angle = averageAngle * i + mCurrentRotatedAngleOfSmallCircle;
            float x = (float) (mCenterX + Math.cos(angle) * mCurrentRotatedRadius);
            float y = (float) (mCenterY + Math.sin(angle) * mCurrentRotatedRadius);
            mSmallCirclePaint.setColor(mSmallCircleColors[i]);
            canvas.drawCircle(x, y, mSmallCircleRadius, mSmallCirclePaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (mCurrentExpandCircleRadius != 0) {
            //扩散模式
            float strokeWidth = mMaxExpandCircleRadius - mCurrentExpandCircleRadius;
            float radius = strokeWidth / 2 + mCurrentExpandCircleRadius;
            mExpandCirclePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, radius, mExpandCirclePaint);
        } else {
            canvas.drawColor(mBackgroundColor);
        }
    }

    /**
     * 第二步，扩散/聚合动画
     */
    private class MergeInAndOutState extends LoadingState {
        public MergeInAndOutState() {
            mAnimator = ValueAnimator.ofFloat(mSmallCircleRadius, mSmallCircleRotatedRadius);
            mAnimator.setDuration(mSmallCircleRotatedDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //改变小圆的旋转直径，从而改变小圆的位置，然后重绘
                    mCurrentRotatedRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mLoadingState = new ExpandState();
                }
            });
            mAnimator.reverse();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    /**
     * 第三步，水波纹动画
     */
    private class ExpandState extends LoadingState {
        public ExpandState() {
            mAnimator = ValueAnimator.ofFloat(mSmallCircleRadius, mMaxExpandCircleRadius);
            mAnimator.setDuration(mSmallCircleRotatedDuration);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //改变大圆的直径，然后重绘
                    mCurrentExpandCircleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }
}
