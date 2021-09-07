# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31. 

### 1.网络
#### 1.1 Retrofit
    08-17 Retrofit源码解析
    1。使用
        1。Retrofit requires at minimum Java 8+ or Android API 21+.
        2。依赖：
            implementation 'com.squareup.retrofit2:retrofit:(insert latest version)'
            implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
        3。You might also need rules for OkHttp and Okio which are dependencies of this library.
        4。同步请求：
            android.os.NetworkOnMainThreadException：注意，需要自己开个线程
        5。注解相关
            1。请求方式：HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD
            2。URL MANIPULATION
                1。url能动态更新，{}块里面的内容就可以被@PATH注解替换
                    @GET("group/{id}/users")
                    Call<List<User>> groupList(@Path("id") int groupId);
                2。查询参数也可以：
                    @GET("group/{id}/users")
                    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
                3。Map作为参数也可以
                    @GET("group/{id}/users")
                    Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);
                4。请求体：RequestBody
                    @POST("users/new")
                    Call<User> createUser(@Body User user);
                5。表单：Form
                    @FormUrlEncoded
                    @POST("user/edit")
                    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
                6。多表单
                    @Multipart
                    @PUT("user/photo")
                    Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);
                7。Header
                    @Headers("Cache-Control: max-age=640000")
                    @GET("widget/list")
                    Call<List<Widget>> widgetList();
                    或者：
                    @Headers({
                        "Accept: application/vnd.github.v3.full+json",
                        "User-Agent: Retrofit-Sample-App"
                    })
                    @GET("users/{username}")
                    Call<User> getUser(@Path("username") String username);
                    或者：
                    @GET("user")
                    Call<User> getUser(@Header("Authorization") String authorization)
                    或者：
                    @GET("user")
                    Call<User> getUser(@HeaderMap Map<String, String> headers)
    2。问题
        1。Retrofit是什么
            A：A type-safe HTTP client for Android and Java.
            Retrofit并不做真正的HTTP请求，它的请求也依赖于OKhttp，Retrofit只是提供了对OKHttp的封装
        2。Retrofit用了哪些设计模式
            1。创建Retrofit实例
                1。Builder模式：
                      Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                                    .addConverterFactory(GsonConverterFactory.create()).build();
                2。工厂方法模式：
            2。创建网络请求的接口实例
                1。外观模式
                2。代理模式
                3。单例模式
                4。策略模式
                5。装饰模式（建造者模式）
            3。生成并执行请求过程时：
                1。适配器模式（代理模式，装饰模式）
        3。Retrofit用了哪些知识点
            1。注解
            2。动态代理
    3。源码
