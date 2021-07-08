package com.rookie.travelinandroid.super_ui.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.rookie.travelinandroid.R;

import androidx.annotation.Nullable;

/**
 * 刮刮卡自定义view
 * 主要使用xfermode 绘制双图层
 * 下方是结果，上方是触摸刮层
 * srcBitmap就是cover的那个Bitmap
 * dstBitmap就是和cover层相同大小的Bitmap
 * 原理就是：src与dst相重叠，然后使用SRC_OUT的模式，只绘制src与dst不相交的地方即不重叠区域，而重叠区域（手指的触摸区域）则将dst的透明度转为透明
 * 从而露出下方的刮奖结果
 */
public class ScratchCardView extends View {
    private Bitmap mTxtBmp;//下方刮奖结果的Bitmap
    private Bitmap mSrcBmp;//上方的刮刮卡区域
    private Bitmap mDstMap;
    private Paint pathPaint;//手指刮图时的路径画笔

    private Path fingerPath;//手指触摸的路径
    private float mEventX;
    private float mEventY;

    public ScratchCardView(Context context) {
        this(context, null);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.RED);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(80);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //初始化需要用到的图片

        mTxtBmp = BitmapFactory.decodeResource(getResources(), R.drawable.result);
        mSrcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);
        mDstMap = Bitmap.createBitmap(mSrcBmp.getWidth(), mSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);

        fingerPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.把resultBitmap画在最下一层
        canvas.drawBitmap(mTxtBmp, 0, 0, pathPaint);

        //2.使用离屏绘制
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), pathPaint, Canvas.ALL_SAVE_FLAG);

        //2.1先将路径画在coverBitmap上面
        //用指定的bitmap的区域生成画布，以只在这个画布上面绘制
        Canvas dstCanvas = new Canvas(mDstMap);
        dstCanvas.drawPath(fingerPath, pathPaint);

        //2.2绘制目标图像
        canvas.drawBitmap(mDstMap, 0, 0, pathPaint);
        //2.3设置模式为 SRC_OUT，刮刮区域为交际区域，需要清除像素
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        //2.4绘制原图像
        canvas.drawBitmap(mSrcBmp, 0, 0, pathPaint);
        pathPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //onTouchEvent中将手指的路径保存至path中并绘制到bitmap上面
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEventX = event.getX();
                mEventY = event.getY();
                fingerPath.moveTo(mEventX, mEventY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (event.getX() - mEventX) / 2 + mEventX;
                float endY = (event.getY() - mEventY) / 2 + mEventY;
                fingerPath.quadTo(mEventX, mEventY, endX, endY);
                mEventX = event.getX();
                mEventY = event.getY();
                break;
        }
        invalidate();
        return true;
    }
}
