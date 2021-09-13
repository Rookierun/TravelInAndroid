package com.rookie.travelinandroid.super_structure;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.rookie.travelinandroid.R;
import com.rookie.travelinandroid.super_structure.event_bus.EventBusMainActivity;
import com.rookie.travelinandroid.super_structure.glide.GlideActivity;
import com.rookie.travelinandroid.super_structure.okhttp.OKHttpActivity;
import com.rookie.travelinandroid.super_structure.pulgin.PluginMainActivity;
import com.rookie.travelinandroid.super_structure.retrofit.RetrofitActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class StructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);
    }

    public void changeSkin(View view) {
        startActivity(new Intent(this, ChangeSkinActivity.class));
    }

    public void checkEventBus(View view) {
        startActivity(new Intent(this, EventBusMainActivity.class));
    }

    public void checkOkHttp(View view) {
        startActivity(new Intent(this, OKHttpActivity.class));
    }

    public void checkRetrofit(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }
    public void checkGlide(View view) {
        startActivity(new Intent(this, GlideActivity.class));
    }
    public void checkPlugin(View view) {
        startActivity(new Intent(this, PluginMainActivity.class));
    }
}