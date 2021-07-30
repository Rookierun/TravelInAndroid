package com.rookie.skin_lib.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rookie.skin_lib.SkinManager;
import com.rookie.skin_lib.core.CustomAppCompatViewInflater;
import com.rookie.skin_lib.core.ViewsMatch;
import com.rookie.skin_lib.utils.ActionBarUtil;
import com.rookie.skin_lib.utils.NavigationBarUtil;
import com.rookie.skin_lib.utils.StatusBarUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

/**
 * 加载外部apk皮肤资源
 */
public class ChangeSkinByPathActivity extends AppCompatActivity {
    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (openSkinChange()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            LayoutInflaterCompat.setFactory2(inflater, this);
//            inflater.setFactory2(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (openSkinChange()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context);
            }
            viewInflater.setName(name);
            viewInflater.setAttrs(attrs);
            return viewInflater.autoMatch();
        }
        return super.onCreateView(name, context, attrs);
    }

    protected boolean openSkinChange() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skinDynamic(String skinPath, int themeColorId) {
        SkinManager.getInstance().loadSkinResource(skinPath);
        if (themeColorId > 0) {
            StatusBarUtil.forStatusBar(this);
            ActionBarUtil.forStatusBar(this);
            NavigationBarUtil.forStatusBar(this);
        }
        View decorView = getWindow().getDecorView();
        applyDayNightForView(decorView);
    }

    private void applyDayNightForView(View decorView) {

        if (decorView instanceof ViewsMatch) {
            ((ViewsMatch) decorView)
                    .changeSkin();
        }
        if (decorView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) decorView;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                applyDayNightForView(child);
            }
        }
    }
}
