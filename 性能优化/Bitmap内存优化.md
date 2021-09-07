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
        4。采样率：
            Bitmap.Config.inSampleSize:
                对Bitmap的宽和高进行缩放，为2的指数，如果不是，则向下取整为2的指数
        5。图片格式：
            CompressFormat.JPEG：quality从0-100
            CompressFormat.PNG:无损，quality被忽略
            CompressFormat.WEBP
### 生成一个Bitmap的方法：

### Bitmap的常见压缩方式：
    1。Bitmap的属性：
        inDensity： 代表的是系统最终选择的 drawable 文件夹类型，等于 480 说明取的是 drawable-xxhdpi文件夹下的图片
        inTargetDensity： 代表的是当前设备的 dpi
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