package com.crm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {
    private static  Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<List<Object>>(){
    }.getType(), new GsonTypeAdapter()).create();


    public static Object ToObject(String jsonStr,Class cls)
    {
      return  gson.fromJson(jsonStr,cls);
    }

    public static List<Object> ToObjectList(String jsonStr)
    {
        Type type = new TypeToken<List<Object>>() {}.getType();
        return gson.fromJson(jsonStr, type);
    }


    public static String ToJson(Object obj,Type tp)
    {
        return  gson.toJson(obj,tp);
    }
}
