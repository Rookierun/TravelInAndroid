package com.rookie.travelinandroid.super_structure.pulgin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rookie.travelinandroid.R;

public class PluginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
    }

    public void startPluginByStub(View view) {
        Intent intent = new Intent(this, TargetActivity.class);
        startActivity(intent);
    }
}