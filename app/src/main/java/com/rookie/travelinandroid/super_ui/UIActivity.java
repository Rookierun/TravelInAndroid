package com.rookie.travelinandroid.super_ui;

import android.os.Bundle;

import com.rookie.travelinandroid.super_ui.animator.custom_typeevaluator.ProvinceView;
import com.rookie.travelinandroid.super_ui.custom_view.DashBoard;
import com.rookie.travelinandroid.super_ui.custom_view.PieChart;
import com.rookie.travelinandroid.super_ui.custom_view.RoundView;

import androidx.appcompat.app.AppCompatActivity;

public class UIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RoundView(this));
    }
}