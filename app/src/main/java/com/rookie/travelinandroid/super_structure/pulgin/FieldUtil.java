package com.rookie.travelinandroid.super_structure.pulgin;

import java.lang.reflect.Field;

public class FieldUtil {
    public static Object getFieldValue(Class clazz, Object target, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);//获取自身的属性（私有，公有，protected）
        field.setAccessible(true);
        return field.get(target);
    }

    public static Field getField(Class clazz, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    public static void setFieldValue(Class clazz, String fieldName, Object fieldValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(clazz, fieldValue);
    }
}
