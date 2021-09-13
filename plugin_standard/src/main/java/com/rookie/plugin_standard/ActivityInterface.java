package com.rookie.plugin_standard;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public interface ActivityInterface {
    void insertAppContext(Activity appActivity);

    void onCreate(@Nullable Bundle savedInstanceState);


    void onStart();


    void onRestart();


    void onResume();


    void onPause();


    void onStop();


    void onDestroy();
}
