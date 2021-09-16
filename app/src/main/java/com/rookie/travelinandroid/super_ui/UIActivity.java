package com.rookie.travelinandroid.super_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.rookie.travelinandroid.R;
import com.rookie.travelinandroid.super_ui.utils.UiUtils;

import androidx.appcompat.app.AppCompatActivity;

public class UIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        initRoot();
    }

    private void initRoot() {
        String text = "你好我是flag看看我能显示多少个字";
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        float realWidth = screenWidth - UiUtils.dp2Ppx(40);
        double singleItemWidth = realWidth / 3.0;
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.height = 300;
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_item_test, root, false);
            TextView flagTv = (TextView) view.findViewById(R.id.tv_flag);
            TextView testTv = (TextView) view.findViewById(R.id.tv_test);
            if (i == 1) {
                flagTv.post(new Runnable() {
                    @Override
                    public void run() {
                        double halfItemWidth = (singleItemWidth - testTv.getWidth()) / 2.0f;
                        float textRealWidth = flagTv.getPaint().measureText(text);
                        RelativeLayout.LayoutParams flagParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        flagParams = ((RelativeLayout.LayoutParams) flagTv.getLayoutParams());
                        flagParams.addRule(RelativeLayout.BELOW, testTv.getId());
                        flagParams.addRule(RelativeLayout.LEFT_OF, testTv.getId());
                        if (halfItemWidth < textRealWidth) {
                            flagParams.leftMargin = (int) (halfItemWidth - textRealWidth);
                        }
//                        flagParams.width = (int) textRealWidth;
                        Log.e("test", "flagTv.getPaint().measureText(text)=" + textRealWidth);
                        flagTv.setLayoutParams(flagParams);
                        Log.e("test", "flagTv.width=" + flagTv.getWidth());
//                        flagTv.invalidate();
                    }
                });
                flagTv.setText(text);
            } else {
                flagTv.setText(i + "");
            }


            view.setLayoutParams(params);
            root.addView(view);
        }
    }
}