package com.rookie.skin_lib.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.rookie.skin_lib.widgets.SkinnableButton;
import com.rookie.skin_lib.widgets.SkinnableLinearLayout;
import com.rookie.skin_lib.widgets.SkinnableRelativeLayout;
import com.rookie.skin_lib.widgets.SkinnableTextView;

import androidx.appcompat.app.AppCompatViewInflater;

public class CustomAppCompatViewInflater extends AppCompatViewInflater {
    private AttributeSet mAttrs;
    private String tagName;

    private Context context;

    public CustomAppCompatViewInflater(Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.tagName = name;
    }

    public void setAttrs(AttributeSet attrs) {
        this.mAttrs = attrs;
    }

    public View autoMatch() {
        View view = null;
        switch (tagName) {
            case "LinearLayout":
                view = new SkinnableLinearLayout(context, mAttrs);
                this.verifyNotNull(view, tagName);
                break;
            case "RelativeLayout":
                view = new SkinnableRelativeLayout(context, mAttrs);
                this.verifyNotNull(view, tagName);
                break;
            case "TextView":
                view=new SkinnableTextView(context,mAttrs);
                this.verifyNotNull(view, tagName);
                break;
            case "Button":
                view = new SkinnableButton(context, mAttrs);
                this.verifyNotNull(view, tagName);
                break;
        }
        return view;
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName()
                    + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
