package com.rookie.travelinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.rookie.travelinandroid.super_ui.ExplosionView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ExplosionView(this));
    }

    public void startUI(View view) {

    }
}