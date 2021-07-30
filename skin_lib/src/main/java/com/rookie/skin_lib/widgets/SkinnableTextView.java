package com.rookie.skin_lib.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.rookie.skin_lib.R;
import com.rookie.skin_lib.SkinManager;
import com.rookie.skin_lib.core.ViewsMatch;
import com.rookie.skin_lib.model.AttrsBean;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class SkinnableTextView extends AppCompatTextView implements ViewsMatch {
    AttrsBean attrsBean;

    public SkinnableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SkinnableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrsBean = new AttrsBean();
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
            if (SkinManager.getInstance().isDefaultSkin()) {
                //使用内置的资源
                Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
                setBackground(drawable);

            } else {
                //使用皮肤资源
                Object o = SkinManager.getInstance().getBgOrSrc(backgroundResourceId);
                if (o instanceof Integer) {
                    setBackgroundColor(((Integer) o));
                } else if (o instanceof Drawable) {

                    setBackground(((Drawable) o));
                }
            }

        }
        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), textColorResourceId);
            setTextColor(colorStateList);
        }
    }
}
