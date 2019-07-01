package com.crm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {

    public static enum emObjType {
            Obj_Int,Obj_Str,Obj_Double,Obj_Long,Obj_Float,
            Obj_Boolean,Obj_Date,Obj_normal,Obj_Array
    };

    public static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }


    public static emObjType getObjectType(Object obj)
    {
        if(obj.getClass().isArray())
        {
            return emObjType.Obj_Array;
        }
        if (obj instanceof Integer) {
          return emObjType.Obj_Int;
        } else if (obj instanceof String) {
           return emObjType.Obj_Str;
        } else if (obj instanceof Double) {
            return emObjType.Obj_Double;
        } else if (obj instanceof Float) {
            return emObjType.Obj_Float;
        } else if (obj instanceof Long) {
            return emObjType.Obj_Long;
        } else if (obj instanceof Boolean) {
            return emObjType.Obj_Boolean;
        } else if (obj instanceof Date) {
            return emObjType.Obj_Date;
        }
        else
        {
            return emObjType.Obj_normal;
        }
    }

    public static Date convertStrToDate(Object dateObj) throws ParseException {
        return  sdf.parse((String) dateObj);
    }

    public boolean matchPattern(String patternStr,String srcStr)
    {
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(srcStr).find();
    }


}
