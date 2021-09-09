# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.

## 理解ActivityManagerService
### 问题
    1.SystemServer进程是什么，都包括什么，干什么用的，怎么启动？
        frameworks/base/services/java/com/android/server/SystemServer.java
        1。SystemServer进程：系统进程
        2。
        3。干什么：
            1。startBootStrapService()
                1.startActivityManagerService
                2.startPowerManagerService
                3.startRecoveryManagerService
                4.startPackageManagerService
                ...
            2.startCoreService()
                1.startBatteryService
                2.startUsageService
                3.startWebViewSUpdateService
                4.statBinderCallsStatsService
            3.startOtherServices()
                1.startWindowManagerService
                2.startNetworkManagementService
                3.startInputManagerService
                4.startConnectivityService
                ...
            总结：启动各种系统服务，包括AMS，WMS，PMS等等
                SystemServiceManager同样在SystemServer中启动，它会对系统的服务进行创建，启动和生命周期进行管理
                官方把服务分成了3组，分别对应上面的123
                    1。引导服务
                    2。核心服务
                    3。其他服务
    2.Android 系统中有多少进程
###
### AIDL
### AMS家族
    1。ActivityManager是一个和AMS相关联的类。主要对运行中的Activity进行管理，这些管理工作并不是ActivityManager来处理的，而是有AMS处理。
    2。源码差异：
        1。Android7.0中：
            ActivityManager->ActivityAcManagerNative->ActivityManagerProxy->ActivityManagerService
            ActivityManagerProxy是ActivityManagerService的代理类。
### AMS的启动过程
    1。涉及到的类：
        1.frameworks/base/services/java/com/android/server/SystemServer.java
            main->run->startBootstrapServices->mSystemServiceManager.startService(ActivityManagerService.Lifecycle.class).getService()
            getService实际上返回的就是ActivityManagerService
        2.frameworks/base/services/core/java/com/android/server/SystemServiceManager.java
            startService(String className)
                通过Class.forName拿到对应的Class文件
            ->startService(Class serviceClass)
                serviceClass.getConstructor-> constructor.newInstance(mContext);
                反射拿到构造方法，并构造出ActivityManagerService.Lifecycle的实例

            ->startService(@NonNull final SystemService service)
                  mServices.add(service);//添加到list中
                  service.onStart();//调用Service的onStart方法
        3.frameworks/base/services/core/java/com/android/server/am/ActivityManagerService.LifeCycle
            ActivityManagerService mService
            ->onStart
                mService.Start
        4.frameworks/base/services/core/java/com/android/server/am/ActivityManagerService
            start





