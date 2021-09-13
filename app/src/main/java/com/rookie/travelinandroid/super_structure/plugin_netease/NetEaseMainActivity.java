package com.rookie.travelinandroid.super_structure.plugin_netease;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.rookie.travelinandroid.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NetEaseMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
    }

    public void loadPlugin(View view) {
        PluginManager.getInstance(this).loadPlugin();
    }

    public void startPlugin(View view) {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(PluginManager.getInstance(this).getPluginDir(), PackageManager.GET_ACTIVITIES);
        ActivityInfo firstActivityInfo = packageArchiveInfo.activities[0];
        Intent intent = new Intent(this, NetEaseProxyActivity.class);
        intent.putExtra("className", firstActivityInfo.name);
        startActivity(intent);
    }
}
