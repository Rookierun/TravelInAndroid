package com.rookie.my_annotation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//@Retention(RetentionPolicy.SOURCE)//源码有效，编译后会丢失
//@Retention(RetentionPolicy.RUNTIME)//运行时有效

@Target(ElementType.TYPE)//类/接口/枚举
@Retention(RetentionPolicy.CLASS)//字节码有效，运行时会丢失
public @interface ShapeFactoryAnnotation {
    String id();

    Class type();
}
