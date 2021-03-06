package com.rookie.travelinandroid.super_structure.okhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rookie.travelinandroid.R;

import java.io.IOException;
import java.io.InputStream;

public class OKHttpActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient.Builder().build();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        imageView = (ImageView) findViewById(R.id.iv);

        //

    }

    public void testGet(View view) {
        Runnable runnable = () -> {
            Request syncRequest = new Request.Builder().url("https://www.baidu.com/").build();
            Call call = client.newCall(syncRequest);
            try {
                Response response = call.execute();
                String string = response.body().string();
                Log.e("test", "response:" + string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        threadStart(runnable);
    }

    private void threadStart(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 5.2 注册
     * https://www.wanandroid.com/user/register
     * <p>
     * 方法：POST
     * 参数
     * username,password,repassword
     *
     * @param view
     */
    public void testPost(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MediaType mediaTypeMarkDown = MediaType.parse("text/x-markdown; charset=utf-8");
                FormBody formBody = new FormBody.Builder().add("username", "test1")
                        .add("password", "pwd")
                        .add("repassword", "pwd").build();
                Request request = new Request.Builder().url("https://www.wanandroid.com/user/register").post(formBody).build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    String string = response.body().string();
                    Log.e("test", "response of post:" + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadStart(runnable);

    }

    Handler mainHandler = new Handler();

    public void testDownload(View view) {
        String imgUrl = "https://pics6.baidu.com/feed/bf096b63f6246b60d41f7a6e90e8e244510fa2b5.jpeg?token=44c43662f4dccd9a81cdeb740f4f898a";
        Request downLoadRequest = new Request.Builder().url(imgUrl).build();
        Call call = client.newCall(downLoadRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null && body.contentLength() > 0) {
                    InputStream inputStream = body.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    OKHttpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        });


    }

    public void testUpload(View view) {


    }
}