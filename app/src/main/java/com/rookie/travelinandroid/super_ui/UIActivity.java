package com.rookie.travelinandroid.super_ui;

import android.os.Bundle;

import com.rookie.travelinandroid.R;

import androidx.appcompat.app.AppCompatActivity;

public class UIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ScratchCardView(this));
    }
}