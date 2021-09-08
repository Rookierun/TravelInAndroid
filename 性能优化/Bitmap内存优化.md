# TravelInAndroid. 

# 三。性能优化：2021-09-01～2021-09-30.
## 09-04 Bitmap内存优化
### 1.Bitmap内存模型
    1.API10之前Bitmap自身在Dalvik Heap中，像素在Native中
    2.API10之后像素也被放在了Dalvik Heap中
    3.API26之后，像素在Native中
### 2.获取Bitmap占用内存
    1。计算方式：
        1。本地图片
            宽*高*1像素占用的内存*（当前图片所在的文件夹与当前机器的dpi的压缩比例）
        2。网络图片
             宽*高*1像素占用的内存
    1。影响一个图片占用内存大小的因素有：
        1。尺寸：也就是宽高
        2。色彩模式也就是图片质量：也就是1像素点占用的内存数
            Bitmap.Config.ALPHA_8：1个像素占用2个字节
            Bitmap.Config.RGB_565：1个像素占用2个字节
            Bitmap.Config.ARGB_4444：1个像素占用4个字节
            Bitmap.Config.ARGB_8888：1个像素占用8个字节,BitmapFactory默认的加载策略
            Bitmap.Config.ALPHA_8
        3。当前资源所在文件夹与当前机型的dpi（只有 decodeResourceStream 和 decodeResource）
            Android在drawable目录查找资源的策略是：xxhdpi（当前机型的dpi） -> xxxhdpi->xhdpi -> hdpi -> mdpi -> ldpi
            其中dpi的数值分别是:320dpi(xhdpi)->240dpi(hdpi)->160(mdpi)->120(ldpi)
            假如：资源图片放在了mdpi中，而当前的机器是hdpi，那么Bitmap为了适应机型，就必须将尺寸放大，也就是h/m=1.5倍
            所以，在只适配一个drawable目录时，我们应尽可能把图片放到较高dpi的目录下，比如当前的xxhdpi
            规则小结：
                1。如果图片所在dpi目录和当前机型的dpi相同，则不会缩放
                2。如果图片位于比较低dpi的目录，那么系统会认为当前图片需要放大才能满足目标机器的尺寸，故图片的宽高都会变大，对应的bitmap所占内存也会升高
                3。如果图片位于较高dpi的目录，那么系统会认为当前图片需要缩小才能满足目标机器的尺寸，故图片的宽高会减小，对应的bitmap所占内存也会降低
                4。如果图片放在drawable-nodpi目录下，也不会缩放
                5。缩放比例的计算：
                    1。假如当前的资源图片放在了xxhdpi，目标机型也行xxhdpi，那么图片不会缩放
                    2。假如当前的资源图片放在了xxxhdpi，目标机型xxhdpi，那么图片会缩小，长和宽都缩小1.3倍，整体就缩小了1.3x1.3=1.69倍
                    3。假如当前的资源图片放在了xhdpi，目标机型也行xxhdpi，那么图片会放大，长和宽都放大为原来的1.5倍，整体就放大了1.5X1.5=2.25倍
                    4。
                        DPI	    分辨率	    系统dpi	基准比例	    对应Drawable目录
                        ldpi	240x320	    120	        0.75	drawable-ldpi(低密度)
                        mdpi	320x480	    160	        1	    drawable-mdpi(中等密度)
                        hdpi	480x800	    240	        1.5	    drawable-hdpi(高密度)
                        xhdpi	720x1280	320	        2	    drawable-xhdpi(超高密度)
                        xxhdpi	1080x1920	480	        3	    drawable-xxhdpi(超超高密度)
                        xxxhdpi	2160 x3840	640	        4	    drawable-xxxhdpi(超超超高密度)
                6。当前设备信息：
                      density:2.75
                      densityDpi:440--拉取xxhdpi目录下的资源文件
                      scaledDensity:2.75
                      heightPixels:2261
                      widthPixels:1080
                      xdpi:391.885
                      ydpi:395.844
                7。图片原始信息：
                    702X1000，512KB
                7.文件放到xx-hdpi中时：
                    bitmap info:
                        bitmapWidth:644
                        bitmapHeight:917
                        byteCount:2362192
                        allocationByteCount:2362192
                        bitmapDensity:440
                        config:ARGB_8888
                8。文件放到xxx-hdpi中时：
                    bitmap info:
                        bitmapWidth:483
                        bitmapHeight:688
                        byteCount:1329216
                        allocationByteCount:1329216
                        bitmapDensity:440
                        config:ARGB_8888
                9。文件放到x-hdpi中时：
                    bitmap info:
                        bitmapWidth:965
                        bitmapHeight:1375
                        byteCount:5307500
                        allocationByteCount:5307500
                        bitmapDensity:440
                        config:ARGB_8888
                10。代码：
                    ```
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

                            Log.e("test", "device info:\n" + builder.toString());
                            ImageView imageView = (ImageView) findViewById(R.id.iv);
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nba);
                            imageView.setImageBitmap(bitmap);
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
                    ```

        4。采样率：
            Bitmap.Config.inSampleSize:本质是影响了图片的宽高
                对Bitmap的宽和高进行缩放，为2的指数，如果不是，则向下取整为2的指数
        5。图片格式：
            CompressFormat.JPEG：quality从0-100
            CompressFormat.PNG:无损，quality被忽略
            CompressFormat.WEBP
### 生成一个Bitmap的方法：
    1.BitmapFactory.decode系列：
        BitmapFactory.decodeFile
        BitmapFactory.decodeStream
        BitmapFactory.decodeResource
        BitmapFactory.decodeByteArray
    2.Bitmap.createBitmap
    3.Bitmap.compress

### Bitmap的常见压缩方式：
    1。Bitmap的属性：
        inDensity： 代表的是系统最终选择的 drawable 文件夹类型，等于 480 说明取的是 drawable-xxhdpi文件夹下的图片
        inTargetDensity： 代表的是当前设备的 dpi
    2。质量压缩：
        1。质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度，来达到压缩图片的目的
        2。压缩后图片的长，宽，像素都不会改变，那么 bitmap 所占内存大小是不会变的
        3。由于图片的质量变低了，所以压缩后图片的大小会变小
        4。质量压缩 png 格式这种图片没有作用，因为 png 是无损压缩
        ```
          /**
               * 将文件压缩到指定大小以内
               * 这个大小指的是，存储到文件时，文件的大小，而不是当前Bitmap所占用的内存大小
               * 这种方式并没有改变Bitmap本身，而是改变了由Bitmap生成的文件的大小以及质量
               */
            private void compressQuality(int targetSize, int declineQuality) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Log.e("test", "压缩前文件的大小：" + baos.toByteArray().length / 1024 + "kb");
                int quality = 100;
                while (baos.toByteArray().length / 1024 > targetSize) {
                    baos.reset();
                    quality -= declineQuality;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                }
                Log.e("test", "压缩后的文件大小是：" + baos.toByteArray().length / 1024 + "kb");

            }
        ```
    3。采样率：
        1。采样率压缩其原理时缩放bitmap的尺寸
        2。压缩后的Bitmap宽度，高度，以及占用的内存都会变小，文件（压缩后保存到本地的文件）大小也会变小
        3。采样率代表宽高变为原来的几分之一，
        4。采样率只能为2的整幂次，比如2，4，8。。。
        5。由于inSampleSize只能为2的整幂次，所以无法精确控制大小
            ```
                    /**
                     * 将图片bytes压缩到宽度小于[targetWidth],高度小于[tartgetHeight]
                     * 这种方案改变了采样率，生成的新的Bitmap在宽高尺寸和占用内存方面都相比于原Bitmap变小了
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
            ```
            >下面是压缩前Bitmap的信息:
             2021-09-08 10:59:39.100 14090-14090/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:921
                 bitmapHeight:1313
                 byteCount:4837092
                 allocationByteCount:4837092
                 bitmapDensity:420
                 config:ARGB_8888
             2021-09-08 10:59:39.781 14090-14090/com.rookie.travelinandroid E/test: 压缩前文件的大小：1459kb
             2021-09-08 10:59:39.781 14090-14090/com.rookie.travelinandroid E/test: 采样率：8
             2021-09-08 10:59:39.781 14090-14090/com.rookie.travelinandroid E/test: 压缩后文件的大小：41kb
             2021-09-08 10:59:39.781 14090-14090/com.rookie.travelinandroid E/test: 下面是压缩后Bitmap的信息:
             2021-09-08 10:59:39.781 14090-14090/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:115
                 bitmapHeight:164
                 byteCount:75440
                 allocationByteCount:75440
                 bitmapDensity:420
                 config:ARGB_8888

    4。缩放压缩：
        1.缩放发使用的时通过矩阵对图片进行缩放
        2。缩放后的图片宽度，高度，以及占用内存都会变小，文件大小也会变小（指压缩后保存到本地的文件，原始文件不会改变）

            ```
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
            ```

            >
            2021-09-08 11:13:55.229 14704-14704/com.rookie.travelinandroid E/test: 下面是压缩前Bitmap的信息:
             2021-09-08 11:13:55.229 14704-14704/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:921
                 bitmapHeight:1313
                 byteCount:4837092
                 allocationByteCount:4837092
                 bitmapDensity:420
                 config:ARGB_8888
             2021-09-08 11:13:55.808 14704-14704/com.rookie.travelinandroid E/test: 压缩前文件的大小：1459kb
             2021-09-08 11:13:55.809 14704-14704/com.rookie.travelinandroid E/test: 缩放率：0.07616146
             2021-09-08 11:13:55.809 14704-14704/com.rookie.travelinandroid E/test: 压缩后文件的大小：16kb
             2021-09-08 11:13:55.809 14704-14704/com.rookie.travelinandroid E/test: 下面是压缩后Bitmap的信息:
             2021-09-08 11:13:55.809 14704-14704/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:70
                 bitmapHeight:99
                 byteCount:27720
                 allocationByteCount:27720
                 bitmapDensity:420

    5。色彩模式：
        1.由于图片的存储格式改变，与 ARGB_8888 相比，每个像素的占用的字节由 8 变为 4 ， 所以图片占用的内存也为原来的一半
        2.图片的宽高不发生变化
        3.如果图片不包含透明信息的话，可以使用此方法进行压缩

        ```
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
        ```
            >
            下面是压缩前Bitmap的信息:
             2021-09-08 11:19:57.400 14898-14898/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:921
                 bitmapHeight:1313
                 byteCount:4837092
                 allocationByteCount:4837092
                 bitmapDensity:420
                 config:ARGB_8888
             2021-09-08 11:19:58.543 14898-14898/com.rookie.travelinandroid E/test: 压缩前文件的大小：1459kb
             2021-09-08 11:19:58.543 14898-14898/com.rookie.travelinandroid E/test: 压缩后文件的大小：909kb
             2021-09-08 11:19:58.543 14898-14898/com.rookie.travelinandroid E/test: 下面是压缩后Bitmap的信息:
             2021-09-08 11:19:58.543 14898-14898/com.rookie.travelinandroid E/test: bitmap info:
                 bitmapWidth:921
                 bitmapHeight:1313
                 byteCount:2418546
                 allocationByteCount:2418546
                 bitmapDensity:420
                 config:RGB_565

### 常用加载库的比较：
    GlideVSPicassoVSFresco
    1。Glide可以提供多种尺寸对图片的加载，而Picasso只能加载原图
### 优化策略
    1。通过配置GlideModule：
        @GlideModule
        class MyGlideModule : AppGlideModule() {
        ​
            override fun applyOptions(context: Context, builder: GlideBuilder) {
                builder.setDefaultRequestOptions(RequestOptions().format(getBitmapQuality()))
            }
        ​
            private fun getBitmapQuality(): DecodeFormat {
                return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N || hasLowRam()) {
                    // 低端机型采用RGB_565以节约内存
                    DecodeFormat.PREFER_RGB_565
                } else {
                    DecodeFormat.PREFER_ARGB_8888
                }
            }
        }
        针对机型，高端机型使用ARGB_8888(DecodeFormat.PREFER_ARGB_8888),低端机型用RGB_565(DecodeFormat.PREFER_RGB_565)
### 问题随记
    1.不同资源在不同目录下内存占用的变化，也就是压缩比怎么取值
    2.argb_8888 rgb565区别 （透明度）
    3.hook的方法能不能把Glide加载图片时也检测呢？