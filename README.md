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
# 二。架构知识：2021-08-01～2021-08-31. 

# 三。性能优化：2021-09-01～2021-09-30. 

# 四。ndk：2021-10-01～2021-11-15. 

# 五。Kotlin：2021-11-16～2021-12-01  

# 六。Flutter：2021-12-15～2021-01-15. 

# 七。算法&数据结构：2021-01-16～2021-01-31. 

