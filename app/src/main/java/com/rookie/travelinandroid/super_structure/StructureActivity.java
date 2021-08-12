package com.rookie.travelinandroid.super_structure;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.rookie.travelinandroid.R;
import com.rookie.travelinandroid.super_structure.event_bus.EventBusMainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class StructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);
    }

    public void changeSkin(View view) {
        startActivity(new Intent(this,ChangeSkinActivity.class));
    }

    public void checkEventBus(View view) {
        startActivity(new Intent(this, EventBusMainActivity.class));
    }
}