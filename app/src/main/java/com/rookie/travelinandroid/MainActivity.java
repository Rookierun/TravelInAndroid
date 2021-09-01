package com.rookie.travelinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.util.Printer;
import android.view.View;

import com.rookie.travelinandroid.super_structure.StructureActivity;
import com.rookie.travelinandroid.super_ui.UIActivity;

import java.io.File;
import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                if (x.contains(">>>>> Dispatching to ")) {
//                    Log.e("test", "messageLooping:开始分发消息");
//                } else if (x.contains("<<<<< Finished to ")) {
//                    Log.e("test", "messageLooping:结束消息处理");
//                }
//            }
//        });
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .detectAll()
                .detectCustomSlowCalls()
                .detectDiskReads()
                .detectNetwork()
                .detectResourceMismatches()
                .detectUnbufferedIo()
                .build();
//        StrictMode.setThreadPolicy(policy);
        File file = new File(getCacheDir().getAbsolutePath(), "test.txt ");
        try {
            file.createNewFile();
            Thread.sleep(3000);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String text = "123";
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startUI(View view) {
        startActivity(new Intent(this, UIActivity.class));
    }

    public void startStructure(View view) {
        startActivity(new Intent(this, StructureActivity.class));
    }
}