# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.

## 占位式插件化-APK解析原理系统源码分析
### 涉及到的概念
    1。宿主：提供运行时环境，并加载插件到内存
    2。插件：供宿主加载
    3。接口：面向接口编程
### 涉及到的关键类：
    PackageManagerService
    PackageParser
    PackageParser.Package
    PackageParser.Component
    PackageParser.Activity
### 步骤
### 原理
    解析目标apk的manifest，通过反射拿到receiver节点及对应的intent-filter内容，然后调用registerReceiver的方法