# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31. 

#### 1.2 OKHttp
    07-30 OkHttp源码解读
    1。使用
        1。1依赖
        implementation("com.squareup.okhttp3:okhttp:4.9.1")
        implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
        1。2请求
            同步
            android.os.NetworkOnMainThreadException
            java.net.UnknownHostException: Unable to resolve host "wwww.baidu.com": No address associated with hostname
                域名解析的问题
            java.net.UnknownHostException: Unable to resolve host "www.baidu.com": No address associated with hostname
            java.net.UnknownHostException: Unable to resolve host "www.baidu.com": No address associated with hostname
                模拟器没网。。。

            添加header：
                private final OkHttpClient client = new OkHttpClient();
                public void run() throws Exception {
                    Request request = new Request.Builder()
                        .url("https://api.github.com/repos/square/okhttp/issues")
                        .header("User-Agent", "OkHttp Headers.java")
                        .addHeader("Accept", "application/json; q=0.5")
                        .addHeader("Accept", "application/vnd.github.v3+json")
                        .build();

                    try (Response response = client.newCall(request).execute()) {
                      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                      System.out.println("Server: " + response.header("Server"));
                      System.out.println("Date: " + response.header("Date"));
                      System.out.println("Vary: " + response.headers("Vary"));
                    }
                }
            1。Get
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
                    }
            2。Post
                1。表单
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
                2。图片/文件
                 public static final MediaType MEDIA_TYPE_MARKDOWN
                      = MediaType.parse("text/x-markdown; charset=utf-8");

                  private final OkHttpClient client = new OkHttpClient();

                  public void run() throws Exception {
                    File file = new File("README.md");

                    Request request = new Request.Builder()
                        .url("https://api.github.com/markdown/raw")
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                        .build();

                    try (Response response = client.newCall(request).execute()) {
                      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                      System.out.println(response.body().string());
                    }
                  }
            3。上传&下载
                1。下载
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
                2。上传文件
                public static final MediaType MEDIA_TYPE_MARKDOWN
                      = MediaType.parse("text/x-markdown; charset=utf-8");

                  private final OkHttpClient client = new OkHttpClient();

                  public void run() throws Exception {
                    File file = new File("README.md");

                    Request request = new Request.Builder()
                        .url("https://api.github.com/markdown/raw")
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                        .build();

                    try (Response response = client.newCall(request).execute()) {
                      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                      System.out.println(response.body().string());
                    }
                  }
                3。多文件上传
                private static final String IMGUR_CLIENT_ID = "...";
                private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "Square Logo")
                        .addFormDataPart("image", "logo-square.png",
                            RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                        .build();

                Request request = new Request.Builder()
                        .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                        .url("https://api.imgur.com/3/image")
                        .post(requestBody)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                  if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                  System.out.println(response.body().string());
                }
    2。问题：
        2。1 OKIO&IO&NIO
        2。2关于http的header
        2。3用的什么通信，连接是怎么建立的
        2。4一些常用的状态码
           100~199：指示信息，表示请求已接收，继续处理
           200~299：请求成功，表示请求已被成功接收、理解
           300~399：重定向，要完成请求必须进行更进一步的操作
           400~499：客户端错误，请求有语法错误或请求无法实现
           500~599：服务器端错误，服务器未能实现合法的请求
    3。官方文档翻译：
        The HTTP client’s job is to accept your request and produce its response.
        This is simple in theory but it gets tricky in practice.
            http client用来接收请求并产生响应信息，而这理论上简单，实际操作却比较棘手
        Requests¶
        Each HTTP request contains a URL, a method (like GET or POST), and a list of headers.
        Requests may also contain a body: a data stream of a specific content type.
            每个http请求都包含一个url，一个请求方法（get/post/delete。。。）还有一系列的header，当然
            请求也有可能包含一个请求体：一种指定内容的形式的数据流
        Responses¶
        The response answers the request with a code (like 200 for success or 404 for not found), headers, and its own optional body.
            响应，回复请求时带有一个响应码（200，成功，404 不存在），一系列的响应header，以及它自身可选的响应体
        Rewriting Requests
        When you provide OkHttp with an HTTP request, you’re describing the request at a high-level: “fetch me this URL with these headers.”
        For correctness and efficiency, OkHttp rewrites your request before transmitting it.
        重写请求
            当你提供给okhttp一个http请求是，你是在一个较高的级别在描述这个请求："给我带着这些header拿这个url的数据"，为了正确和高效，Okhttp会在发送合格请求前重写它
        OkHttp may add headers that are absent from the original request, including Content-Length, Transfer-Encoding, User-Agent, Host, Connection, and Content-Type.
        It will add an Accept-Encoding header for transparent response compression unless the header is already present. If you’ve got cookies, OkHttp will add a Cookie header with them.
            OkHttp可能会给你的请求添加一些原来并不存在的header，包括Content-Length，Transfer-Encoding，User-Agent，Host，Connection，以及Content-Type
            如果request中不存在Accept-Encoding 这个header，OkHttp也会增加一个用来压缩响应信息，如果你已经有Cookie了，OkHttp也会在header中增加一个Cookie
        Some requests will have a cached response. When this cached response isn’t fresh, OkHttp can do a conditional GET to download an updated response if it’s newer than what’s cached.
        This requires headers like If-Modified-Since and If-None-Match to be added.
            有些请求的响应已经被缓存了，如果这些缓存信息已经过期了，如果新的响应信息较缓存更新的画，OkHttp会做一个Get请求来拿到更新了的响应信息并且缓存起来
            这一操作需要在请求中增加If-Modified-Since and If-None-Match这两个header
        Rewriting Responses¶
        If transparent compression was used, OkHttp will drop the corresponding response headers Content-Encoding and Content-Length because they don’t apply to the decompressed response body.
        If a conditional GET was successful, responses from the network and cache are merged as directed by the spec.
        重写响应
            如果使用了透明压缩，OkHttp会丢掉响应中相关的Content-Encoding and Content-Length header，因为这些header并没有应用到解压响应体中
            如果一个条件性的Get请求成功了，网络响应以及缓存会直接在当前维度被合并
        Follow-up Requests¶
        When your requested URL has moved, the webserver will return a response code like 302 to indicate the document’s new URL.
        OkHttp will follow the redirect to retrieve a final response.
        后续请求
            当你请求的url已经移动了，web服务器会返回一个code为302的response来告诉你新的url，而OkHttp也会继续重定向到这个地址以便抓去最终的响应信息
        If the response issues an authorization challenge, OkHttp will ask the Authenticator (if one is configured) to satisfy the challenge.
        If the authenticator supplies a credential, the request is retried with that credential included.
            如果响应遇到了认证的挑战， OKHttp会获取Authenticator信息（如果设置了）来应对这个挑战，如果authenticator提供了一个可以用的信息，那么请求就会带着这个authenticator继续执行
        Retrying Requests¶
        Sometimes connections fail: either a pooled connection was stale and disconnected, or the webserver itself couldn’t be reached.
        OkHttp will retry the request with a different route if one is available.
        请求重试
        有些时候链接会失败，或许是因为链接池的链接过期了，或者是断开了，再或者是服务器不可达了，OKHttp会在其他线路可用的情况下再重试其他线路来执行这个请求
        Calls
        With rewrites, redirects, follow-ups and retries, your simple request may yield many requests and responses.
        OkHttp uses Call to model the task of satisfying your request through however many intermediate requests and responses are necessary.
        Typically this isn’t many! But it’s comforting to know that your code will continue to work if your URLs are redirected or if you failover to an alternate IP address.
        Calls
            再经过rewrites, redirects, follow-ups and retries之后，你的一个简单的请求可能会产生大量的请求和响应
            OKHttp使用Call来将你的任务模型化以便在不管有多少立即的请求以及多少必要的响应的条件下，满足你的请求
            典型的就是这个并不多，但是当知道你的url被重定向了或者你又选用了一个被选的ip地址后，你的代码依然继续工作时，这是很令人欣慰的
        Calls are executed in one of two ways:
        Synchronous: your thread blocks until the response is readable.
        Asynchronous: you enqueue the request on any thread, and get called back on another thread when the response is readable.
            Call会被以以下两种方式之一执行：
                1。同步，你的线程在响应可用之前一直是阻塞的
                2。异步，你在任意一个线程enqueue你的request，然后当你的响应在另一个线程可用时，它会给你一个callback
        Calls can be canceled from any thread. This will fail the call if it hasn’t yet completed!
        Code that is writing the request body or reading the response body will suffer an IOException when its call is canceled.
            Calls可以在任意一个线程取消，如果这个call还没完成，那么，此次call就会被cancel
            如果call被cancel了，写入request body中的代码或读取response body的代码会发生一个IOException的异常
        Dispatch
        For synchronous calls, you bring your own thread and are responsible for managing how many simultaneous requests you make.
        Too many simultaneous connections wastes resources; too few harms latency.
        调度
            对于同步的调用，你持有你的当前线程而且对于有多少了同步请求的创建也是有管理责任的，太多同时的链接会浪费资源，太少又会影响延迟
        For asynchronous calls, Dispatcher implements policy for maximum simultaneous requests.
        You can set maximums per-webserver (default is 5), and overall (default is 64).
            对于异步请求，Dispatcher对于同时请求的最大数量做了策略，你可以对每一个服务器设置最大数（默认是5个），以及全局的最大请求数（默认是64个）
        Caching¶
        OkHttp implements an optional, off by default, Cache.
        OkHttp aims for RFC correct and pragmatic caching behaviour, following common real-world browser like Firefox/Chrome and server behaviour when ambiguous.
        缓存
            OKHttp提供了一个可选的默认关闭的缓存策略
            OKHttp锚定RFC链接和务实的缓存行为，当模棱两可时，会跟随现实世界的如Firefox/Chrome的缓存策略
        Connections¶
        Although you provide only the URL, OkHttp plans its connection to your webserver using three types: URL, Address, and Route.
        链接
            尽管你只提供了Url，OkHttp却用了3个类型的数据组成了它自己的connection：URL，Address，Route
        URLs¶
        URLs (like https://github.com/square/okhttp) are fundamental to HTTP and the Internet.
        In addition to being a universal, decentralized naming scheme for everything on the web, they also specify how to access web resources.
        URLS
            URL是HTTP和Internet的基础
            另外，作为一个全站，去中心化的域名，他们也指明了如果访问web资源
        URLs are abstract:
            They specify that the call may be plaintext (http) or encrypted (https), but not which cryptographic algorithms should be used.
            Nor do they specify how to verify the peer’s certificates (the HostnameVerifier) or which certificates can be trusted (the SSLSocketFactory).

            They don’t specify whether a specific proxy server should be used or how to authenticate with that proxy server.
            URL是抽象的
            URL指明了Call是使用Http还是Https，但是并没指定使用那个加密算法。URL既没指明怎么校验服务器证书也没指明哪些证书可以被信任
            URL不指明应该使用那个代理服务器，也不指明怎么验证那个代理服务器
        They’re also concrete: each URL identifies a specific path (like /square/okhttp) and query (like ?q=sharks&lang=en). Each webserver hosts many URLs.
            他们同样是具体的，每个url指向了一个具体的路径以及对应的query信息，每个服务器的主机都有多个URL
        Addresses¶
        Addresses specify a webserver (like github.com) and all of the static configuration necessary to connect to that server: the port number, HTTPS settings, and preferred network protocols (like HTTP/2 or SPDY).
        地址
            Address指明了一个具体的服务器（github.com）以及链接这台服务器所需的静态配置，比如：the port number, HTTPS settings, and preferred network protocols (like HTTP/2 or SPDY).
        URLs that share the same address may also share the same underlying TCP socket connection. Sharing a connection has substantial performance benefits: lower latency, higher throughput (due to TCP slow start) and conserved battery.
        OkHttp uses a ConnectionPool that automatically reuses HTTP/1.x connections and multiplexes HTTP/2 and SPDY connections.
            共享同一个address的URL同样共享底层的TCP socket链接，共享一个链接有很多性能好处：低延迟，高吞吐，以及电量友好
            OKHTTP用了一个ConnectionPool来自动的重用HTTP/1.x链接和多路的HTTp/2和Spdy链接
        In OkHttp some fields of the address come from the URL (scheme, hostname, port) and the rest come from the OkHttpClient.
            在OKHttp中，address的一些字段来源于URL，（Scheme，hostname，port）其他的来源于OKHTTPClient
        Routes¶
        Routes supply the dynamic information necessary to actually connect to a webserver.
        This is the specific IP address to attempt (as discovered by a DNS query), the exact proxy server to use (if a ProxySelector is in use), and which version of TLS to negotiate (for HTTPS connections).

        There may be many routes for a single address.
        For example, a webserver that is hosted in multiple datacenters may yield multiple IP addresses in its DNS response.
        路由
            路由提供了连接到服务器的必须的动态信息，这个是具体的ip地址（dns解析），试探使用一个具体的代理服务器的，以及使用哪个版本的TLS来加密

            对于一个单一的address有多个路由
            比如：一个web服务器在dns响应里面可能产生多个IP地址
        Connections¶
        When you request a URL with OkHttp, here’s what it does:

            It uses the URL and configured OkHttpClient to create an address. This address specifies how we’ll connect to the webserver.
            It attempts to retrieve a connection with that address from the connection pool.
            If it doesn’t find a connection in the pool, it selects a route to attempt. This usually means making a DNS request to get the server’s IP addresses. It then selects a TLS version and proxy server if necessary.
            If it’s a new route, it connects by building either a direct socket connection, a TLS tunnel (for HTTPS over an HTTP proxy), or a direct TLS connection. It does TLS handshakes as necessary.
            It sends the HTTP request and reads the response.
        链接
            当你用OKHttp请求一个地址是，下面就是okhttp所做的事情
            1。它使用url和配置了的OKHttpClient来创建一个address，这个address指明了我们怎么连接到web服务器
            2。它尝试在链接池中拿到这个地址的链接
            3。如果池中没有当前地址的链接，它就选择一个路由尝试。这也通常意味着做一个dns的请求来拿到服务器的ip地址，然后如果有有必要的画，再选择一个TLS的version以及代理服务器
            4。如果是一个新的路由，它通过建立一个直接的socket链接/一个tls通道（对HTTPS）或者一个直接的TLS链接。然后有必要的话，做TLS握手
            5。发送http request，读取response
        If there’s a problem with the connection, OkHttp will select another route and try again. This allows OkHttp to recover when a subset of a server’s addresses are unreachable.
        It’s also useful when a pooled connection is stale or if the attempted TLS version is unsupported.
            如果这个链接有问题，OKHttp会选择另一个路由然后重试， 这允许OkHttp在一个服务器地址不可达的时候自己恢复请求，当一个链接池不可用或者尝试的TLS版本不被支持的时候也很有用
        Once the response has been received, the connection will be returned to the pool so it can be reused for a future request. Connections are evicted from the pool after a period of inactivity.
            一旦拿到了响应，链接必须马上回到池子里面，以便它可以被后面的请求重用，如果一个链接在一段时间内都不活跃，那么这个链接会被踢出链接池
    4。总结：
        OKHttp使用了责任链模式，将一个请求分成了多个步骤并交给不同的责任人去处理
        1。概览
            同步->new OkHttpClient().newCall(request).execute()
            异步->new OkHttpClient().newCall(request).enqueue(CallBack)
            OKHttpClient就成为了开发者与OKHttp之间的桥梁，开发者通过OKHttpClient配置请求所需的header，url，body等一系列参数，然后OkHttpClient再将这些参数传递给OKHttp
        2。所设计到的关键类
            2.1总体流程：
                OkHttpClient:
                    Dispatcher:线程调度
                Request:
                    HttpMethod
                    HttpUrl
                    Headers
                    RequestBody
                Call->RealCall
                    Transmitter->ExchangeFinder->RealConnectionPool
                Response
            2.2细节
                1。不管是同步请求还是异步请求，最终都会走到okhttp3.RealCall.getResponseWithInterceptorChain方法，不同点就是
                    1.同步请求执行的是RealCall，异步请求执行的是AsyncCall
                    2。异步执行，是Dispatcher将AsyncCall加入到readyAsyncCalls队列中，而同步请求是加入到runnignCalls队列中，执行完毕后从队列中移除
                2。责任链模式的体现：getResponseWithInterceptorChain方法
                     interceptors.add(new RetryAndFollowUpInterceptor(client));
                     interceptors.add(new BridgeInterceptor(client.cookieJar()));
                     interceptors.add(new CacheInterceptor(client.internalCache()));
                     interceptors.add(new ConnectInterceptor(client));
                     interceptors.add(new CallServerInterceptor(forWebSocket));
                     1.RetryAndFollowUpInterceptor:
                        负责请求准备，比如用url，address，route组成Transmitter进而组成
                     2.BridgeInterceptor
                        负责请求前Request的封装，比如添加必要的header，指定压缩类型，指定请求链接的是http/https
                        负责请求后的Response的解压缩
                     3.CacheInterceptor
                        负责请求的缓存处理
                     4.ConnectInterceptor
                        负责建立链接，并缓存可用链接
                        关键类：
                            Transmitter：Bridge between OkHttp's application and network layers.
                            ExchangeFinder：Attempts to find the connections for a sequence of exchanges
                                Address：A specification for a connection to an origin server.
                                RealConnectionPool：
                                    RealConnection：
                                RouteSelector：Selects routes to connect to an origin server.
                                    Route：The concrete route used by a connection to reach an abstract origin server.
                                    Selection
                        判断当前的连接是否可以使用：流是否已经被关闭，并且已经被限制创建新的流；
                        如果当前的连接无法使用，就从连接池中获取一个连接；
                        连接池中也没有发现可用的连接，创建一个新的连接，并进行握手，然后将其放到连接池中。
                     5.CallServerInterceptor
                        负责真正与服务器进行交互，使用Okio进行io的处理


