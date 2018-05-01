package com.kaicom.api.util;

import static com.kaicom.api.util.StringUtil.isBlank;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.text.TextUtils;

/**
 * 反射工具类
 * 
 * @author scj
 */
public final class ReflectUtil {

    private ReflectUtil() {
        throw new RuntimeException("…（⊙＿⊙；）…");
    }

    /**
     * 根据对象，获取某个方法
     * @param obj obj对象
     * @param methodName 方法名
     * @param parameterTypes 参数类型
     * @return Method对象
     */
    public static Method getMethod(Object obj, String methodName,
            Class<?>... parameterTypes) {
        if (obj == null || isBlank(methodName))
            return null;
        
        Method method = null;
        try {
            method = obj.getClass().getDeclaredMethod(methodName,
                    parameterTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 获取某个方法
     * @param clz 类字面常量
     * @param methodName 方法名
     * @param parameterTypes 参数类型
     * @return Method对象
     */
    public static Method getMethod(Class<?> clz, String methodName,
            Class<?>... parameterTypes) {
        if (clz == null || isBlank(methodName))
            return null;

        Method method = null;
        try {
            method = clz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 直接传入对象、方法名、参数，直接调用某个方法
     * @param obj
     * @param methodName
     * @param parameter
     */
    public static Object invoke(Object obj, String methodName,
            Object... parameter) {
        return invoke(obj.getClass(), obj, methodName, parameter);
    }

    /**
     * 直接传入类名、方法名、参数，即可使用该对象的隐藏静态方法
     * 
     * @param clz
     * @param methodName
     * @param parameter
     */
    public static Object invoke(Class<?> clz, Object obj, String methodName,
            Object... parameter) {
        int len = 0;
        if (clz == null || isBlank(methodName))
            return null;
        
        if (parameter != null)
            len = parameter.length ;

        Class<?>[] parameterTypes = new Class<?>[len];
        for (int i = 0; i < len; i++)
            parameterTypes[i] = parameter[i].getClass();
        try {
            return getMethod(clz, methodName, parameterTypes).invoke(obj,
                    parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取属性值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getValue(Object obj, String fieldName) {
        return getValue(obj.getClass(), obj, fieldName);
    }
    
    /**
     * 反射获取属性
     * @param clz
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getValue(Class<?> clz, Object obj, String fieldName) {
    	 if (obj == null || TextUtils.isEmpty(fieldName))
             return null;
         try {
             Field field = clz.getDeclaredField(fieldName);
             field.setAccessible(true);
             return field.get(obj);
         } catch (NoSuchFieldException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (IllegalArgumentException e) {
             e.printStackTrace();
         }
         return null;
    }

}