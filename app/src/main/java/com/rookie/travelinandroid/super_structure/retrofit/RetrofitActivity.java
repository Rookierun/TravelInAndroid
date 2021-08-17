package com.rookie.travelinandroid.super_structure.retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rookie.travelinandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                mockRequest();
            }
        }).start();

    }

    private void mockRequest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitHubService service = retrofit.create(GitHubService.class);
        retrofit2.Call<List<Repo>> rookieRun = service.listRepos("RookieRun");
        try {
            rookieRun.enqueue(new retrofit2.Callback<List<Repo>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Repo>> call, retrofit2.Response<List<Repo>> response) {
                    boolean successful = response.isSuccessful();
                    if (successful) {
                        int size = response.body().size();
                        Log.e("test", Thread.currentThread().getName() + ",size:" + size);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Repo>> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    RetrofitActivity.this.runOnUiThread(new Runnable() {
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