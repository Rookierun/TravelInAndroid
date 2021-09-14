# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.

## 占位式插件化- Activity通信
### 涉及到的概念
    1。宿主：提供运行时环境，并加载插件到内存
    2。插件：供宿主加载
    3。接口：面向接口编程
### 步骤
    1。宿主提供占位Activity
    2。设计供宿主和插件实现的接口
    3。插件的BaseActivity提供插入宿主环境的入口，并提供app运行相关的方法，如onCreate，findViewById等等
    4。宿主Activity拿到targetClassName后，反射获取目标对象并启动
### 问题：
    1。@hide Google对反射的限制
    2。A.class.newInstance与A.class.getConstructor.newInstance()的区别
    3。Could not find method loadPlugin(View) in a parent or ancestor Context for android:onClick attribute defined on view class androidx.appcompat.widget.AppCompatButton
        A:Activity启动错了
    4。Error: Invoke-customs are only supported starting with Android O (--min-api 26)
        A：compileOptions {
                  sourceCompatibility JavaVersion.VERSION_1_8
                  targetCompatibility JavaVersion.VERSION_1_8
              }
    5.Attempt to invoke virtual method 'void android.view.Window.setContentView(int)' on a null object reference
        A:pluginActivity没有上下文环境，所以需要以来宿主的setContentView,故，需要重写Base Activity的setContentView,然后把具体工作交给AppActivity
    6.Unable to start activity ComponentInfo{com.rookie.travelinandroid/com.rookie.travelinandroid.super_structure.plugin_netease.NetEaseProxyActivity}: android.content.res.Resources$NotFoundException: Resource ID #0x7f0b001c
        A:插件的资源文件找不到，同5，需要依赖宿主的能力
        问题应该是出现在了NetEaseProxyActivity的getResource方法的重写上，因为，不再重写这个方法就是ok的
    7. You need to use a Theme.AppCompat theme (or descendant) with this activity.
        A:继承自AppcompatActivity的Activity主题必须是Theme.AppCompat或者 其子类
    8。总结：
        昨天没办法打开的原因是：ProxyActivity里面写了setContentView，同时又重写了get Resource，插件里面并没有宿主的资源，所以就报错了