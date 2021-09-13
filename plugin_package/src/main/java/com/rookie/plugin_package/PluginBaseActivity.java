package com.rookie.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rookie.plugin_standard.ActivityInterface;

import androidx.annotation.Nullable;

public class PluginBaseActivity extends Activity implements ActivityInterface {
    private Activity appActivity;

    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return appActivity.findViewById(id);
    }

    @Override
    public void setContentView(int layoutResID) {
        appActivity.setContentView(layoutResID);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRestart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }
}
