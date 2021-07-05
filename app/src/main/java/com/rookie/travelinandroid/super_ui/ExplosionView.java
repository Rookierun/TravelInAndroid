package com.rookie.travelinandroid.super_ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.rookie.travelinandroid.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 爆炸粒子特效
 * 1.拿到每个像素点的颜色后，构建对应颜色的小球
 * 2.初始化每个小球的x，y，坐标及x，y轴的速度&加速度
 * 3.触摸事件开启动画，并且在动画执行过程中，动态修改小球的以上参数，并且重绘
 * 关键点在于：1.速度&坐标2.属性动画的执行及重绘
 */

public class ExplosionView extends View {
    private List<ExplodedBall> balls = new ArrayList<>();
    private Paint mPaint;
    float d = 6;
    private ValueAnimator mAnimator;
    public ExplosionView(Context context) {
        this(context, null);
    }

    public ExplosionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExplosionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        ExplodedBall ball;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = bitmap.getPixel(i, j);
                ball = new ExplodedBall();
                ball.radius = d / 2;
                ball.ballColor = pixel;
                ball.x = i * d + d / 2;
                ball.y = j * d + d / 2;
                ball.vX= ((float)( Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random()));
                ball.vY=rangerInt(-15,35);
                ball.aX=0;
                ball.aY=0.98f;
                balls.add(ball);
            }
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnimator=ValueAnimator.ofFloat(0,1);
        mAnimator.setDuration(12000);
        mAnimator.setRepeatCount(-1);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBalls();
            }
        });
    }

    private float rangerInt(int i, int i1) {
        int max=Math.max(i,i1);
        int min=Math.min(i,i1)-1;
        return (float) (min+Math.ceil(Math.random()*(max-min)));
    }

    private void updateBalls() {
        for (ExplodedBall ball : balls) {
            ball.x=ball.x+ball.vX;
            ball.y=ball.y+ball.vY;
            ball.vX=ball.vX+ball.aX;
            ball.vY=ball.aY+ball.vY;
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (balls!=null&&!balls.isEmpty()){
            for (int i = 0; i < balls.size(); i++) {
                ExplodedBall explodedBall = balls.get(i);
                mPaint.setColor(explodedBall.ballColor);
                canvas.drawCircle(explodedBall.x,explodedBall.y,explodedBall.radius,mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            mAnimator.start();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
