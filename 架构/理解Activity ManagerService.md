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
    2。AMS重要的数据结构
        1。ActivityRecord:存储了Activity的所有信息：
            AMS的引用
            AndroidManifest.xml中activity的节点信息
            Activity的资源信息
            Activity的进程相关信息
        2。TaskRecord： 用来描述一个Activity任务栈，其内部存储了任务栈的所有信息，包括：
            任务栈的唯一标识符
            任务栈的倾向性
            任务栈中Activity记录
            AMS的引用
        3。ActivityStack：用来管理所有Activity，
            内部维护了Activity的所有状态，特殊状态的Activity以及和 Activity相关的列表数据等
            ActivityStack是由ActivityStackSupervisor来进行管理的,其中包含了：
                1。ActivityState：Activity的所有状态
                      enum ActivityState {
                            INITIALIZING,
                            RESUMED,
                            PAUSING,
                            PAUSED,
                            STOPPING,
                            STOPPED,
                            FINISHING,
                            DESTROYING,
                            DESTROYED
                        }
                2。List<TaskRecord>而TaskRecord中又包含了List<ActivitRecord>
        4。ActivityStackSupervisor：中存储了
            1。ActivityStack mHomeStack：
                用来存储Launcher App的所有Activity
            2。ActivityStack mFocusedStack
                用来表示当前正在接受输入或启动下一个Activity 的所有Activity
            3。ActivityStack mLastFocusedStack
                表示此前接受输入的所有Activity
    3。Launch Mode
        1.standard
        2.singleTop：
            1。在栈顶：onNewIntent
            2。不在栈顶，类似于standard
        3.singleTask:
            1。栈里有：它上面的弹栈，自己的onNewIntent被调用
            2。栈里没有，类似于standard
        4.singleInstance:单独一个任务栈
    4。Intent的FLAG
        1。FLAG_ACTIVITY_SINGLE_TOP==singleTop
        2.FLAG_ACTIVITY_NEW_TASK==singleTask
        3.FLAG_ACTIVITY_CLEAR_TOP:没有与之对应的launch mode，singleTask默认有此FLAG的效果
        4.FLAG_ACTIVITY_NO_HISTORY:Activity退出后，就不会存在于栈中==android.noHistory
        5.FLAG_ACTIVITY_MULTIPLE_TASK:需要和2一同使用，系统会启动一个新的栈来容纳新启动的Activity
        6.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS:Activity不会被放入到"最近启动的Activity"列表中，也就是手机的最近任务
        7.FLAG_ACTIVITY_BROUGHT_TO_FRONT:这个标志位通常不是由应用程序中的代码设置的，singleTask时，自动加上的
        8.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY：这个标志位也不是代码加的而是从历史记录中启动的（长按Home）
        9.FLAG_ACTIVITY_CLEAR_TASK：需要和2一起使用，用于清除与启动的 Activity相关栈的所有其他 Activity
    5.taskAffinity:
        AndroidManifest.xml中设置：android.taskAffinity,用来指定Activity希望归属的栈，默认情况下，同一个应用程序所有的 Activity都有着相同的taskAffinity
            1。taskAffinity与FLAG_ACTIVITY_NEW_TASK或者singleTask结合
                如果新启动的 Activity的taskAffinity和当前栈的taskAffinity相同，则加入到该栈中，否则创建新栈然后进栈
            2。taskAffinity与allowTaskReparenting配合。如果allowTaskReparenting为true，则 Activity有转移能力。
                比如：微信启动了发邮件的Activity，此时发邮件的 Activity和微信在同一个栈中，并且这个栈位于前台，如果，发送邮件的 Activity的allowTaskReparenting为true，
                伺候，Email应用所在的栈处于前台时，发送邮件的 Activity就会从微信的栈中转移到与它跟亲近的邮件应用所在的栈中






