package com.rookie.travelinandroid.super_structure.pulgin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class PluginApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
//            HookHelper.hookAMS();
        } catch (Exception e) {
            Log.e("test","exception in hookAMS:"+e.getMessage());
        }
    }
}
