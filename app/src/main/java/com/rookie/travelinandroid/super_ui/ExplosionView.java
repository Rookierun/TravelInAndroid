package com.rookie.travelinandroid.super_ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.rookie.travelinandroid.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ExplosionView extends View {
    private List<ExplodedBall> balls = new ArrayList<>();
    private Paint mPaint;
    float d = 6;

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
            }
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
