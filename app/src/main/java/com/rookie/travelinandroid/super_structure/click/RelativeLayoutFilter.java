package com.rookie.travelinandroid.super_structure.click;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

class RelativeLayoutFilter extends RelativeLayout {
    public RelativeLayoutFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(new FilterClickListener(l));
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
