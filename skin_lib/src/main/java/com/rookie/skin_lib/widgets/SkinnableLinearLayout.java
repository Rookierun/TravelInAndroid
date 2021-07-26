package com.rookie.skin_lib.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.rookie.skin_lib.R;
import com.rookie.skin_lib.core.ViewsMatch;
import com.rookie.skin_lib.model.AttrsBean;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class SkinnableLinearLayout extends LinearLayout implements ViewsMatch {
    private final AttrsBean attrsBean;

    public SkinnableLinearLayout(Context context, AttributeSet mAttrs) {
        this(context, mAttrs,android.R.attr.textViewStyle);
    }

    public SkinnableLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);  attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinnableTextView,
                defStyleAttr, 0);
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableTextView);
        typedArray.recycle();
    }

    @Override
    public void changeSkin() {
        int key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            setBackground(drawable);
        }
    }
}
