package com.rookie.travelinandroid.super_structure.event_bus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rookie.travelinandroid.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusMainActivity extends AppCompatActivity {
    @BindView(R.id.test_post)
    TextView testPostTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_main);
        EventBus.getDefault().register(this);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.test_post)
    public void testPost() {
        EventBus.getDefault().post("123");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10, sticky = true)
    public void update(String message) {
        Log.e("test", "update:" + message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}