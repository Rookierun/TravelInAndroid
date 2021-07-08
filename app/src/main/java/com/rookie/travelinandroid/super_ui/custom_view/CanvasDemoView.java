package com.rookie.travelinandroid.super_ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * canvas 提供了 transform,scale,rotate,setMatrix等对画布进行一系列操作
 * 其中，matrix是个3x3的矩阵
 *     public static final int MSCALE_X = 0;   //!< use with getValues/setValues
 *     public static final int MSKEW_X  = 1;   //!< use with getValues/setValues
 *     public static final int MTRANS_X = 2;   //!< use with getValues/setValues
 *     public static final int MSKEW_Y  = 3;   //!< use with getValues/setValues
 *     public static final int MSCALE_Y = 4;   //!< use with getValues/setValues
 *     public static final int MTRANS_Y = 5;   //!< use with getValues/setValues
 *     public static final int MPERSP_0 = 6;   //!< use with getValues/setValues
 *     public static final int MPERSP_1 = 7;   //!< use with getValues/setValues
 *     public static final int MPERSP_2 = 8;   //!< use with getValues/setValues
 * 主要是提供对画布的操作：
 */
public class CanvasDemoView extends View {
    public CanvasDemoView(Context context) {
        this(context, null);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
