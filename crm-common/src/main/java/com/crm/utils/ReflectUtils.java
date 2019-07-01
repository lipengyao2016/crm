package com.crm.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectUtils {

    public static void setField(Object obj,String fieldName,Object value,boolean bOverWrite)
            throws NoSuchFieldException, IllegalAccessException {
        Class cls = obj.getClass();
        Field f = null;
        //f = cls.getDeclaredField(fieldName);
        f = ReflectUtils.getFieldInfo(cls,fieldName);
        System.out.println(f);
        if(f!= null)
        {
            f.setAccessible(true);

            if(!bOverWrite)
            {
                Object   oldObj = f.get(obj);
                if(oldObj == null)
                {
                    f.set(obj, value);
                }
            }
            else
            {
                f.set(obj, value);
            }
        }

    }
    public static void setFieldBySuperClass(Object obj,Map<String,Object> chanageMap,boolean isNewEntity)
            throws NoSuchFieldException, IllegalAccessException {
        List<Field> fieldList = new ArrayList<>();
        Class<? extends Object> tempClass = obj.getClass();
        while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
        }

        List<Map.Entry<String, Object>> collect = chanageMap.entrySet().stream().collect(Collectors.toList());
        Iterator<Map.Entry<String, Object>> iterator = chanageMap.entrySet().iterator();

        for (Field p : fieldList) {
            for (Map.Entry<String, Object> op : collect) {
                String key = op.getKey();
                if(p.getName().equals(key)){
                    p.setAccessible(true);
                    if(isNewEntity){
                        p.set(obj,op.getValue());
                    }else {
                        Object initValue = p.get(obj);
                        if(initValue == null){
                            p.set(obj,op.getValue());
                        }
                    }
                    continue;
                }
            }
        }
    }

    public static Object  createInstance(Class cls) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor con = cls.getConstructor(null);
        return con.newInstance();
    }

    public static Object getField(Object obj,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class cls = obj.getClass();
        Field f = null;
        // f = cls.getDeclaredField(fieldName);
        f = ReflectUtils.getFieldInfo(cls,fieldName);
        System.out.println(f);
        f.setAccessible(true);
        return f.get(obj);
    }

    public static Field getFieldInfo(Class cls ,String fieldName)
    {
        Field[] fieldArray = cls.getDeclaredFields();
        for (Field f : fieldArray) {
            if (f.getName().compareToIgnoreCase(fieldName) == 0) {
                return f;
            }
        }
        return null;
    }

    public static Method getMethodInfo(Class cls ,String methodName)
    {
        Method[] methodArray = cls.getDeclaredMethods();
        Method fMethod = null;
        for(Method m : methodArray){
            if(m.getName().compareToIgnoreCase(methodName)  == 0 )
            {
                return  m;
            }
        }
        return null;
    }


    public static Object callMethod(Object obj ,String methodName, Object... args)
    {
        Method m1 = ReflectUtils.getMethodInfo(obj.getClass(),methodName);
        if(m1 != null)
        {
            try {
                Object retValue =   m1.invoke(obj,args);
                return  retValue;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return  null;
    }
}
