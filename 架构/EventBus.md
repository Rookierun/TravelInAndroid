# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31. 
### 5.事件总线
    EventBus-->07-29
    EventBus是观察者模式，基于发布-订阅的，大小只有60K，EventBus可以理解为Observable与Observer之间的纽带
    1.implementation 'org.greenrobot:eventbus:3.2.0'
    2.implementation 'org.greenrobot:eventbus:2.4.0'
    3。（有注解用注解，没注解用反射）
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
            3。post-发送普通事件
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
            4。postStickyEvent:发送黏性事件
                关键代码：
                 public void postSticky(Object event) {
                        synchronized (stickyEvents) {
                        //将事件假如到黏性事件的队列里面去，由于stickyEvents是一个map，所以多次发送同一个sticky event，只会保留最后一个
                            stickyEvents.put(event.getClass(), event);
                        }
                        // Should be posted after it is putted, in case the subscriber wants to remove immediately
                        post(event);
                 }
                 解释：EventBus是单例的，所以，在发送黏性事件时，EventBus先将事件加入到stickyEvents的map集合中去，然后，再下一个界面调用register然后subscribe时，
                      如果当前subscribeMethod是sticky的，会马上遍历stickyEvents拿到stickyEvent的eventType与subscribeMethod感兴趣的eventType对比，若一致，那么
                      直接调用checkPostStickyEventToSubscription(newSubscription, stickyEvent)方法，完成黏性事件的发送及对应方法的调用
            5。注解：
                 javaCompileOptions {
                        annotationProcessorOptions {
                            arguments = [ eventBusIndex : 'com.rookie.travelinandroid.MyEventBusIndex' ]
                        }
                 }
                 dependencies{
                     implementation 'org.greenrobot:eventbus:3.2.0'
                     annotationProcessor 'org.greenrobot:eventbus-annotation-processor:3.0.1'
                 }

                 /** This class is generated by EventBus, do not edit. */
                 public class MyEventBusIndex implements SubscriberInfoIndex {
                     private static final Map<Class<?>, SubscriberInfo> SUBSCRIBER_INDEX;

                     static {
                         SUBSCRIBER_INDEX = new HashMap<Class<?>, SubscriberInfo>();

                         putIndex(new SimpleSubscriberInfo(com.rookie.travelinandroid.super_structure.event_bus.EventBusMainActivity.class,
                                 true, new SubscriberMethodInfo[] {
                             new SubscriberMethodInfo("update", String.class, ThreadMode.MAIN, 10, true),
                         }));

                     }

                     private static void putIndex(SubscriberInfo info) {
                         SUBSCRIBER_INDEX.put(info.getSubscriberClass(), info);
                     }

                     @Override
                     public SubscriberInfo getSubscriberInfo(Class<?> subscriberClass) {
                         SubscriberInfo info = SUBSCRIBER_INDEX.get(subscriberClass);
                         if (info != null) {
                             return info;
                         } else {
                             return null;
                         }
                     }
                 }

                编译时生成MyEventBusIndex，避免运行时大量的反射


