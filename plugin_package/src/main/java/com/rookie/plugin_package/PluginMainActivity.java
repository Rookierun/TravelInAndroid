package com.rookie.plugin_package;

import android.os.Bundle;
import android.util.Log;

public class PluginMainActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int activity_plugin_main = R.layout.activity_plugin_main;
        Log.e("test", "activity_plugin_main：" + activity_plugin_main);
        setContentView(activity_plugin_main);
        Log.e("test", "app传过来的信息：" + savedInstanceState != null ? savedInstanceState.getString("info") : "空的");
    }
}
