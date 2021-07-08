package com.rookie.travelinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.rookie.travelinandroid.super_ui.UIActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startUI(new View(this));
    }

    public void startUI(View view) {
        startActivity(new Intent(this, UIActivity.class));
    }
}