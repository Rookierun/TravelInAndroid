# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.

## Hook入门到熟练
### 1.Hook入门
    原理就是反射+动态代理
    反射拿到某些需要代理的对象，然后动态代理生成代理对象，再用反射将原来的对象替换为动态代理生成的对象
    Object object=Proxy.newInstance(ClassLoader,Class[]{Object.class},new InvocationHandler(){
            public Object invoke(Proxy proxy,Method method,Object[] args){
                return method.invoke();
            }
    });
