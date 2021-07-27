package com.rookie.travelinandroid.super_structure;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.rookie.skin_lib.base.DayOrNightSkinActivity;
import com.rookie.travelinandroid.R;

import androidx.appcompat.app.AppCompatDelegate;

public class ChangeSkinActivity extends DayOrNightSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void changeSkin(View view) {

        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (uiMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                setDayOrNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                //改为夜间模式
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                setDayOrNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                //改为日间模式
                break;
        }
    }
}