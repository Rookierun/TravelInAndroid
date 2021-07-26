package com.rookie.travelinandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.rookie.travelinandroid.super_structure.StructureActivity;
import com.rookie.travelinandroid.super_ui.UIActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startUI(View view) {
        startActivity(new Intent(this, UIActivity.class));
    }

    public void startStructure(View view) {
        startActivity(new Intent(this, StructureActivity.class));
    }
}