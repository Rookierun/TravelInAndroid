package com.rookie.travelinandroid.super_structure.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.rookie.travelinandroid.R;

import androidx.appcompat.app.AppCompatActivity;

public class GlideActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        imageView = (ImageView) findViewById(R.id.iv);
        //
    }

    public void testGet(View view) {
        String url = "https://pics0.baidu.com/feed/8d5494eef01f3a292a4bcdd9f11146395d607ce6.jpeg?token=1f285ea67958dacd453571e3d7abb3c6";
        RequestOptions options = new RequestOptions();
        RequestManager requestManager = Glide.with(this);
        RequestBuilder<Drawable> requestBuilder = requestManager.load(url);
        requestBuilder.into(imageView);
    }

}