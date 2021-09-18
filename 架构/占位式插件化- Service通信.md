# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.

## 占位式插件化- Service通信
### 涉及到的概念
    1。宿主：提供运行时环境，并加载插件到内存
    2。插件：供宿主加载
    3。接口：面向接口编程
### 步骤
    1。宿主提供占位ProxyService
    2。设计供宿主和插件实现的接口
    3。插件的BaseService提供插入宿主环境的入口，并提供app运行相关的方法，如onCreate，onStartCommend等
    4。宿主ProxyService拿到targetClassName后，反射获取目标对象并启动