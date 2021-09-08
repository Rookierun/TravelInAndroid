package com.rookie.travelinandroid.super_performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.rookie.travelinandroid.R;

import java.io.ByteArrayOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class PerformanceActivity extends AppCompatActivity {


    private ImageView imageView;
    private ImageView bottomIv;
    private Bitmap originalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        imageView = (ImageView) findViewById(R.id.iv);
        bottomIv = (ImageView) findViewById(R.id.iv_bottom);
        testBitmap();
//        compressQuality(50, 10);
//        testCompressInSampleSize();
//        testCompressScale();
        testCompressRGB565();
    }

    /**
     * 只改变色彩模式，也就是单位像素所占的内存数，并不改变Bitmap的尺寸
     */
    private void testCompressRGB565() {
        try {
            byte[] bytes = bitmapToByteArray(originalBitmap);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap scaledBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            byte[] scaledByteArray = bitmapToByteArray(scaledBitmap);
            Log.e("test", "压缩前文件的大小：" + bytes.length / 1024 + "kb");
            Log.e("test", "压缩后文件的大小：" + scaledByteArray.length / 1024 + "kb");
            Log.e("test", "下面是压缩后Bitmap的信息:");
            showBitmapInfo(scaledBitmap);
        } catch (Exception e) {

        }
    }

    private void testCompressScale() {
        Bitmap bitmap = originalBitmap;
        int targetWidth = 100;
        int targetHeight = 100;
        compressScale(bitmap, targetWidth, targetHeight);
    }

    /**
     * 将图片压缩到指定的宽高范围之内
     *
     * @param bitmap
     * @param targetWidth
     * @param targetHeight
     */
    private void compressScale(Bitmap bitmap, int targetWidth, int targetHeight) {
        try {
            float scale = Math.min(targetWidth * 1.0f / bitmap.getWidth(), targetHeight * 1.0f / bitmap.getHeight());
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, ((int) (bitmap.getWidth() * scale)), ((int) (bitmap.getHeight() * scale)), true);
            byte[] scaleBitmapByteArray = bitmapToByteArray(scaledBitmap);
            byte[] originalBytes = bitmapToByteArray(bitmap);
            Log.e("test", "压缩前文件的大小：" + originalBytes.length / 1024 + "kb");
            Log.e("test", "缩放率：" + scale);
            Log.e("test", "压缩后文件的大小：" + scaleBitmapByteArray.length / 1024 + "kb");
            Log.e("test", "下面是压缩后Bitmap的信息:");
            showBitmapInfo(scaledBitmap);
        } catch (Exception e) {

        }
    }

    private void testCompressInSampleSize() {
        byte[] bytes = bitmapToByteArray(originalBitmap);
        int targetWidth = 300;
        int targetHeight = 300;
        compressInSampleSize(bytes, targetWidth, targetHeight);
    }

    /**
     * 将图片bytes压缩到宽度小于[targetWidth],高度小于[tartgetHeight]
     * 这种方案改变了采样率，生成的新的Bitmap在宽高尺寸和占用内存方面都相比于原Bitmap变小了
     *
     * @param bytes
     * @param targetWidth
     * @param targetHeight
     */
    private void compressInSampleSize(byte[] bytes, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//指拿到bitmap的尺寸，而不拿像素信息，减少内存占用
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int inSampleSize = 1;
        while (options.outWidth / inSampleSize > targetWidth ||
                options.outHeight / inSampleSize > targetHeight) {
            inSampleSize *= 2;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap newBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        byte[] compressBitmapByteArray = bitmapToByteArray(newBitmap);
        Log.e("test", "压缩前文件的大小：" + bytes.length / 1024 + "kb");
        Log.e("test", "采样率：" + inSampleSize);
        Log.e("test", "压缩后文件的大小：" + compressBitmapByteArray.length / 1024 + "kb");
        Log.e("test", "下面是压缩后Bitmap的信息:");
        bottomIv.setImageBitmap(newBitmap);
        showBitmapInfo(newBitmap);

    }

    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将文件压缩到指定大小以内
     * 这个大小指的是，存储到文件时，文件的大小，而不是当前Bitmap所占用的内存大小
     * 这种方式并没有改变Bitmap本身，而是改变了由Bitmap生成的文件的大小以及质量
     */
    private void compressQuality(int targetSize, int declineQuality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.e("test", "压缩前文件的大小：" + baos.toByteArray().length / 1024 + "kb");
        int quality = 100;
        while (baos.toByteArray().length / 1024 > targetSize) {
            baos.reset();
            quality -= declineQuality;
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        Log.e("test", "压缩后的文件大小是：" + baos.toByteArray().length / 1024 + "kb");
        bottomIv.setImageBitmap(originalBitmap);
        showBitmapInfo(originalBitmap);
    }

    private void testBitmap() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        float scaledDensity = displayMetrics.scaledDensity;
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        StringBuilder builder = new StringBuilder();
        builder.append("density:").append(density).append("\n")
                .append("densityDpi:").append(densityDpi).append("\n")
                .append("scaledDensity:").append(scaledDensity).append("\n")
                .append("heightPixels:").append(heightPixels).append("\n")
                .append("widthPixels:").append(widthPixels).append("\n")
                .append("xdpi:").append(xdpi).append("\n")
                .append("ydpi:").append(ydpi);

//        Log.e("test", "device info:\n" + builder.toString());

        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nba);
        imageView.setImageBitmap(originalBitmap);
        Log.e("test", "下面是压缩前Bitmap的信息:");
        showBitmapInfo(originalBitmap);

    }

    private void showBitmapInfo(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int byteCount = bitmap.getByteCount();
        int allocationByteCount = bitmap.getAllocationByteCount();
        int bitmapDensity = bitmap.getDensity();
        StringBuilder bitmapBuilder = new StringBuilder();
        bitmapBuilder.append("bitmapWidth:").append(bitmapWidth).append("\n")
                .append("bitmapHeight:").append(bitmapHeight).append("\n")
                .append("byteCount:").append(byteCount).append("\n")
                .append("allocationByteCount:").append(allocationByteCount).append("\n")
                .append("bitmapDensity:").append(bitmapDensity).append("\n")
                .append("config:").append(bitmap.getConfig()).append("\n");

        Log.e("test", "bitmap info:\n" + bitmapBuilder.toString());
    }
}