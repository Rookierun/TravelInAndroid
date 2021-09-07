# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.
### 3.图片
#### 3.1 Glide
        1。使用
            1。依赖：
                  implementation 'com.github.bumptech.glide:glide:4.12.0'
                  annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
                对于kotlin项目
                dependencies {
                  kapt 'com.github.bumptech.glide:compiler:4.11.0'
                }
                apply plugin: 'kotlin-kapt'
            2。加载图片
                Glide.with(fragment) .load(url).into(imageView);
        2。问题
        3。源码
            1.Glide架构
                https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2478933d44534c2fabb6466049b9f3b7~tplv-k3u1fbpfcp-watermark.awebp
                Glide交互层
                    RequestManager requestManager = Glide.with(this);
                    RequestBuilder<Drawable> requestBuilder = requestManager.load(url);
                    requestBuilder.into(imageView);
                展示层
                业务逻辑配置层
                业务逻辑执行层
                数据获取层
                网络请求层




