# TravelInAndroid. 

# 一。高级UI：2021-07-01～2021-07-31. 
## 07-05. 
### 1。刮刮卡xfermode. 
### 2。粒子爆炸特效. 
### 3。canvas的 translate与scale，save和restore.
## 07-06.
### Canvas实际案例操作
### 贝塞尔曲线与计算规则
## 07-07
### 事件传递机制的详解
    1。涉及到的类
    Activity->PhoneWindow->DecorView->ViewGroup->View
    2。涉及到的方法
    dispatchTouchEvent/onInterceptTouchEvent/requestDisallowTouchEvent/onTouchEvent
    3。逻辑
        3.1ViewGroup
            public boolean dispatchTouchEvent(MotionEvent event){
                boolean consumed=false;
                if(onInterceptTouchEvent(event)){
                    consumed=onTouchEvent(event);
                }else{
                    consumed=child.dispatchTouchEvent(event);
                }
                return consumed;
            }
        3.2View
        onTouchListener.onTouch()如果return true，则onTouchEvent不再执行，也就是onTouch方法优先级高于onTouchEvent
        onLongClick如果返回为false，那么onClick可以得到执行
        Disable的View也可以消费事件
        某个View一旦开始处理时间，如果它不消耗ACTION_DOWN事件，也就是onTouchEvent返回false，那么同一事件序列中的其他事件都不会再交给这个view处理了，
        并且重新交由它的父元素处理（父元素的onTouchEvent被调用）
        View的enable属性不影响onTouchEvent的默认返回值，哪怕一个View是disabled状态的，只要他的clickable/longClickable有一个为true，那么它的onTouchEvent就返回true
        onClick得到响应的前提是View可点击的，并且收到了ACTION_DOWN和ACTION_UP事件，并且受长按事件的影响，长按事件返回为true时，onClick不会响应
        onLongClick在ACTION_DOWN里判断是否进行响应，要想执行长按事件，那么该view必须是longClickable并设置了OnLongClickListener

### 属性动画分析
    1。原理
    实际上，通过一个线程每隔一段时间，通过调用view.setXXX()值，这也是属性动画的原理
    2。对于自定义的属性，如果需要执行动画则必须要提供对应的get/set方法，并且再set方法上面调用对应的invalidate方法
    3。对于TypeEvaluator的自定义，则需要根据情况，在evaluate方法进行针对性的计算
### 平行动画
## 07-08
### 刻度表
    1.画弧形
        canvas.drawArc(RectF,startAngle,sweepAngle,useCenter,Paint);
        canvas.drawArc(left,top,right,bottom,startAngle,sweepAngle,useCenter,Paint);
        画弧形时，需要给canvas一个具体的矩形的绘制范围区域，其中，矩形的中心点即为圆弧的圆心，useCenter的参数为true时，表示绘制圆心到圆弧之间的区域
    2.画刻度
        画刻度时，使用了paint.setEffectPath(PathDashPathEffect)方法
        PathDashPathEffect(Path shape, float advance, float phase, Style style)。虚线类型的Path，其中可以通过 arg1：shape指定虚线的形状
        需要注意的是，setPath后，会把之前的弧形清除，所以需要把原来的弧形画完后，再setEffectPath,并在画完后，将此置为null
    3.画指针
        画指针时，需要注意指针stopX，stopY的计算，此处借助了Math.toRadians()将角度转换为弧度，然后再计算cos/sin
### 饼状图
    主要涉及到的是，canvas的状态的保存，由于只有一块区域需要translate，所以，需要在translate之前save画布状态，并且在完成translate后，restore恢复画布状态，
    以免对后续的绘制产生影响
### 圆形头像
    主要涉及到的是xferMode的使用：Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    由于我们的头像是方的，那么要生成圆形图像的画，就必须有个圆形的离屏区域供操作，所以，先调用了canvas.saveLayer(left,top,right,bottom)用以确定该区域
    然后canvas.drawCircle将这部分圆形区域画出来，然后setXferMode()指定重叠的逻辑后，再去绘制bitmap就生成了圆形的图像了,最后恢复图层canvas.restoreToCount()
    public enum Mode {
            // these value must match their native equivalents. See SkXfermode.h
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_CLEAR.png" />
             *     <figcaption>Destination pixels covered by the source are cleared to 0.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = 0\)</p>
             * <p>\(C_{out} = 0\)</p>
             */
            CLEAR       (0),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SRC.png" />
             *     <figcaption>The source pixels replace the destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src}\)</p>
             * <p>\(C_{out} = C_{src}\)</p>
             */
            SRC         (1),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DST.png" />
             *     <figcaption>The source pixels are discarded, leaving the destination intact.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{dst}\)</p>
             */
            DST         (2),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SRC_OVER.png" />
             *     <figcaption>The source pixels are drawn over the destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} + (1 - \alpha_{src}) * \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{src} + (1 - \alpha_{src}) * C_{dst}\)</p>
             */
            SRC_OVER    (3),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DST_OVER.png" />
             *     <figcaption>The source pixels are drawn behind the destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{dst} + (1 - \alpha_{dst}) * \alpha_{src}\)</p>
             * <p>\(C_{out} = C_{dst} + (1 - \alpha_{dst}) * C_{src}\)</p>
             */
            DST_OVER    (4),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SRC_IN.png" />
             *     <figcaption>Keeps the source pixels that cover the destination pixels,
             *     discards the remaining source and destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{src} * \alpha_{dst}\)</p>
             */
            SRC_IN      (5),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DST_IN.png" />
             *     <figcaption>Keeps the destination pixels that cover source pixels,
             *     discards the remaining source and destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{dst} * \alpha_{src}\)</p>
             */
            DST_IN      (6),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SRC_OUT.png" />
             *     <figcaption>Keeps the source pixels that do not cover destination pixels.
             *     Discards source pixels that cover destination pixels. Discards all
             *     destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = (1 - \alpha_{dst}) * \alpha_{src}\)</p>
             * <p>\(C_{out} = (1 - \alpha_{dst}) * C_{src}\)</p>
             */
            SRC_OUT     (7),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DST_OUT.png" />
             *     <figcaption>Keeps the destination pixels that are not covered by source pixels.
             *     Discards destination pixels that are covered by source pixels. Discards all
             *     source pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = (1 - \alpha_{src}) * \alpha_{dst}\)</p>
             * <p>\(C_{out} = (1 - \alpha_{src}) * C_{dst}\)</p>
             */
            DST_OUT     (8),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SRC_ATOP.png" />
             *     <figcaption>Discards the source pixels that do not cover destination pixels.
             *     Draws remaining source pixels over destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{dst}\)</p>
             * <p>\(C_{out} = \alpha_{dst} * C_{src} + (1 - \alpha_{src}) * C_{dst}\)</p>
             */
            SRC_ATOP    (9),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DST_ATOP.png" />
             *     <figcaption>Discards the destination pixels that are not covered by source pixels.
             *     Draws remaining destination pixels over source pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src}\)</p>
             * <p>\(C_{out} = \alpha_{src} * C_{dst} + (1 - \alpha_{dst}) * C_{src}\)</p>
             */
            DST_ATOP    (10),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_XOR.png" />
             *     <figcaption>Discards the source and destination pixels where source pixels
             *     cover destination pixels. Draws remaining source pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = (1 - \alpha_{dst}) * \alpha_{src} + (1 - \alpha_{src}) * \alpha_{dst}\)</p>
             * <p>\(C_{out} = (1 - \alpha_{dst}) * C_{src} + (1 - \alpha_{src}) * C_{dst}\)</p>
             */
            XOR         (11),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_DARKEN.png" />
             *     <figcaption>Retains the smallest component of the source and
             *     destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} + \alpha_{dst} - \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = (1 - \alpha_{dst}) * C_{src} + (1 - \alpha_{src}) * C_{dst} + min(C_{src}, C_{dst})\)</p>
             */
            DARKEN      (16),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_LIGHTEN.png" />
             *     <figcaption>Retains the largest component of the source and
             *     destination pixel.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} + \alpha_{dst} - \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = (1 - \alpha_{dst}) * C_{src} + (1 - \alpha_{src}) * C_{dst} + max(C_{src}, C_{dst})\)</p>
             */
            LIGHTEN     (17),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_MULTIPLY.png" />
             *     <figcaption>Multiplies the source and destination pixels.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{src} * C_{dst}\)</p>
             */
            MULTIPLY    (13),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_SCREEN.png" />
             *     <figcaption>Adds the source and destination pixels, then subtracts the
             *     source pixels multiplied by the destination.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} + \alpha_{dst} - \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(C_{out} = C_{src} + C_{dst} - C_{src} * C_{dst}\)</p>
             */
            SCREEN      (14),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_ADD.png" />
             *     <figcaption>Adds the source pixels to the destination pixels and saturates
             *     the result.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = max(0, min(\alpha_{src} + \alpha_{dst}, 1))\)</p>
             * <p>\(C_{out} = max(0, min(C_{src} + C_{dst}, 1))\)</p>
             */
            ADD         (12),
            /**
             * <p>
             *     <img src="{@docRoot}reference/android/images/graphics/composite_OVERLAY.png" />
             *     <figcaption>Multiplies or screens the source and destination depending on the
             *     destination color.</figcaption>
             * </p>
             * <p>\(\alpha_{out} = \alpha_{src} + \alpha_{dst} - \alpha_{src} * \alpha_{dst}\)</p>
             * <p>\(\begin{equation}
             * C_{out} = \begin{cases} 2 * C_{src} * C_{dst} & 2 * C_{dst} \lt \alpha_{dst} \\
             * \alpha_{src} * \alpha_{dst} - 2 (\alpha_{dst} - C_{src}) (\alpha_{src} - C_{dst}) & otherwise \end{cases}
             * \end{equation}\)</p>
             */
            OVERLAY     (15);

            Mode(int nativeInt) {
                this.nativeInt = nativeInt;
            }

            /**
             * @hide
             */
            @UnsupportedAppUsage
            public final int nativeInt;
        }
## 文字的测量和几何变换
### 文字的测量与绘制
    1。文字的居中测量
        paint.getTextBonds()
        paint.getFontMetrics()
    2。文字的左对齐
        rect.left
    3。文字的换行
        1.StaticLayout(text,TextPaint,width,Align,)
        2.breakText()
### canvas的变换
    1。canvas的范围裁切
        canvas.clipRect()
        canvas.clipOutRect()
        canvas.clipPath(),被切掉的部分由毛边
    2。canvas的几何变换
        canvas.translate()
        canvas.rotate()
        canvas.scale()
        canvas.skew()
    3。Matrix的几何变换
        preTranslate/postTranslate
        preRotate/postRotate
        preScale/postScale
        preSkew/postSkew
    4。使用Camera做三维旋转
## 图片的缩放与拖动
    ### ScalableImageView
# 二。架构知识：2021-08-01～2021-08-31. 
## 07-26
    ### 手写动态换肤框架及高可扩展性换肤应用回放
        1.registerActivityLifecycleCallbacks执行在Activity.setContentView之前
        2.registerActivityLifecycleCallbacks执行在Activity.onCreate之后
        3.涉及到android.view.LayoutInflater.Factory2的功能：
            1。小红书的平行动画
                思路就是设置Factory2，重写onCreateView拿到对应的attr，然后再通过属性动画改变期动画效果
            2。防止重复点击
                思路就是通过设置Factory，然后替换view的创建，并重写onClick的执行逻辑com.rookie.travelinandroid.super_structure.click.FilterClickListener
            3。动态换肤通用做法
            4。动态换肤扩展性的做法
                1.内置皮肤资源
                    主要思路就是在BaseActivity设置LayoutInflater.Factory，告诉系统使用我们自己的factory.createView方法，然后自定义
                    CustomAppCompatViewInflater继承自AppCompatViewInflater，通过拦截对应的tag，完成对指定view的换肤
                2。加载apk中的皮肤包资源
                    主要思路就是将apk中的资源，通过反射调用addAssetPath方法后，加入到Resource中，以便app可以访问到对应的资源文件
        4。mfactorySet置为false

## 热门开源库源码分析
### 1.网络
#### 1.1 Retrofit
#### 1.2 OKHttp
### 2.数据库
    GreenDao
### 3.图片
#### 3.1 Glide
#### 3.2 Picasso
#### 3.3 Fresco
### 4.依赖注入
#### 4.1 ButterKnife
#### 4.2 Dagger2
### 5.事件总线
    EventBus-->07-29
    EventBus是观察者模式，基于发布-订阅的，大小只有60K，EventBus可以理解为Observable与Observer之间的纽带
    1.implementation 'org.greenrobot:eventbus:3.2.0'
    2.implementation 'org.greenrobot:eventbus:2.4.0'
    问题：
        1。事件-观察者->方法回调是如何完成的？
        2。线程的切换是如何完成的
        3。不同版本之间有什么区别
    解答：
        Q：事件-观察者->方法回调是如何完成的？
        A：1。Events are posted ({@link #post(Object)}) to the bus, which delivers it to subscribers that have a matching handler
           method for the event type.
           从注释上看，EventBus通过post方法，将事件（Event）post到bus，然后，这个事件就会被deliver到对应的观察者，
           前提，该观察者可以处理该类型的事件，也就是被Subscribe注解的方法的参数和上面的Event相同的类型
           2。而被Subscribe注解的方法也有要求：must be public, return nothing (void),* and have exactly one parameter (the event)
           源码：
           EventBus.getDefault().register(this);//注册观察者
           1。首先是getDefault的 单例
           2。register方法概括下来就是：找到当前类中所有：1。public 2。参数长度为1 3。被Subscribe注解的方法，然后以当前类class为key，满足条件的List<SubscribeMethod>为value存储在MethodCache中，方便后续使用
           3。细节
            1。register-注册
            public void register(Object subscriber) {
                    Class<?> subscriberClass = subscriber.getClass();//当前类的class
                    //3。1找到所有符合条件的注解方法，最终调用了findUsingReflectionInSingleClass方法
                    List<SubscriberMethod> subscriberMethods = subscriberMethodFinder.findSubscriberMethods(subscriberClass);
                    synchronized (this) {
                        for (SubscriberMethod subscriberMethod : subscriberMethods) {
                        //3。2 订阅
                            subscribe(subscriber, subscriberMethod);
                        }
                    }
                }
            private void findUsingReflectionInSingleClass(FindState findState) {
                    Method[] methods;
                    try {
                        // This is faster than getMethods, especially when subscribers are fat classes like Activities
                        //3。1。1拿到所有方法
                        methods = findState.clazz.getDeclaredMethods();
                    } catch (Throwable th) {
                      。。。
                    }
                    for (Method method : methods) {
                        int modifiers = method.getModifiers();
                        //3。1。2拿到方法是public/protected/private。。。必须是public的
                        if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            //3。1。3方法参数必须是1个
                            if (parameterTypes.length == 1) {
                                Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                                //3。1。4有Subscribe注解
                                if (subscribeAnnotation != null) {
                                    Class<?> eventType = parameterTypes[0];
                                    if (findState.checkAdd(method, eventType)) {
                                        ThreadMode threadMode = subscribeAnnotation.threadMode();
                                        //3。1。5构建SubscriberMethod对象，里面有Method,threadMode,priority,sticky
                                        findState.subscriberMethods.add(new SubscriberMethod(method, eventType, threadMode,
                                                subscribeAnnotation.priority(), subscribeAnnotation.sticky()));
                                    }
                                }
                            } else if (strictMethodVerification && method.isAnnotationPresent(Subscribe.class)) {
                              ...
                            }
                        } else if (strictMethodVerification && method.isAnnotationPresent(Subscribe.class)) {
                           ...
                        }
                    }
                }
                register阶段关键的数据结构是：
                                private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();
                                它是以Class为key，Class中所有符合条件的注解的方法的List为value保存起来，以达到缓存的目的
            2。subscribe-订阅
                org.greenrobot.eventbus.EventBus.subscribe
                    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
                        //2。1拿到当前订阅方法（被Subscribe注解的方法）的参数类型（如：java.lang.String.class），也就是说，当前方法只对这种参数类型感兴趣
                        Class<?> eventType = subscriberMethod.eventType;
                        Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
                        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
                         //2。2以参数类型为key,对应的方法的List为value存储
                         subscriptionsByEventType.put(eventType, subscriptions);
                         。。。

                        int size = subscriptions.size();
                        for (int i = 0; i <= size; i++) {
                            //检查priority，根据priority来决定插入的位置
                           。。。
                        }
                        //2。3拿到当前订阅者（XXXActivity.class）
                        List<Class<?>> subscribedEvents = typesBySubscriber.get(subscriber);
                         。。。
                        //2。4将当前订阅者与感兴趣的参数类型的List关联起来
                        subscribedEvents.add(eventType);

                        if (subscriberMethod.sticky) {
                        //处理粘性事件
                           。。。
                        }
                    }
                subscribe阶段关键的数据结构是：
                    1。
                    //key为参数类型的Class，value为所有的对此参数类型感兴趣的观察者Subscription(Subscriber,SubscriberMethod)，如String.class->List<AXXActivity.class,BXXActivity.class>
                    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType;
                    可以通过这个结构找到所有对该事件类型感兴趣的Subscription->Subscriber->SubscriberMethod->method然后调用

                    2。
                    //key为具体的订阅者（如AActivity.class）,value是当前订阅者内的所有感兴趣的类型，如AXXActivity.class->List<String.class,Integer.class>
                    private final Map<Object, List<Class<?>>> typesBySubscriber;
                    可以通过这个类判断当前类是否被注册过
                    public synchronized boolean isRegistered(Object subscriber) {
                            return typesBySubscriber.containsKey(subscriber);
                    }


                    3。
                    //key为当前的事件类型，也就是订阅方法中的参数类型，value就是当前的事件对象
                    private final Map<Class<?>, Object> stickyEvents;
            3。post-发送事件
                3。1概括：
                  调用链：
                       org.greenrobot.eventbus.EventBus.post->postSingleEvent->postSingleEventForEventType->postToSubscription
                       ->invokeSubscriber/mainThreadPoster/backgroundPoster/asyncPoster
                3。2源码
                  关键代码：
                    private boolean postSingleEventForEventType(Object event, PostingThreadState postingState, Class<?> eventClass) {
                          CopyOnWriteArrayList<Subscription> subscriptions;
                          synchronized (this) {
                          //通过方法类型找到对应的所有的观察者，然后回调其订阅方法
                              subscriptions = subscriptionsByEventType.get(eventClass);
                          }
                          if (subscriptions != null && !subscriptions.isEmpty()) {
                              for (Subscription subscription : subscriptions) {
                                  postingState.event = event;
                                  postingState.subscription = subscription;
                                  boolean aborted;
                                  try {
                                      postToSubscription(subscription, event, postingState.isMainThread);
                                      aborted = postingState.canceled;
                                  } finally {
                                      postingState.event = null;
                                      postingState.subscription = null;
                                      postingState.canceled = false;
                                  }
                                  if (aborted) {
                                      break;
                                  }
                              }
                              return true;
                          }
                          return false;
                      }


### 6.响应式编程
     RXJava
### 7.内存泄漏
     LeakCanary


# 三。性能优化：2021-09-01～2021-09-30.

# 四。ndk：2021-10-01～2021-11-15. 

# 五。Kotlin：2021-11-16～2021-12-01  

# 六。Flutter：2021-12-15～2021-01-15. 

# 七。算法&数据结构：2021-01-16～2021-01-31. 
## 01.数据结构&算法总览-->07-29
数据结构
### 一维数据结构
    基础：数组array，链表linked-list
    高级：栈stack，队列queue，双端队列deque，集合list/set，映射map，
### 二维数据结构
    基础：树tree，图graph
    高级：二叉搜索树 binary-search-tree（red-black tree，AVL），堆heap，并查集 disjoint set 字典树 Trie
### 特殊数据结构
    位运算，布隆过滤器，LRU
算法
    if-else ,switch->branch
    for,while-loop->Iteration
    递归Recursion（Divide&Conquer，Backtrace）
    搜索Search：深度优先Depth first Search，广度优先Breadth first search
    动态规划 Dynamic Programming
    二分查找Binary Search
    贪心算法Greedy
    数学Math，几何Geometry
    注意：在头脑中会议上面几种算法的思想和代码模版
算法题，做一遍远远不够

