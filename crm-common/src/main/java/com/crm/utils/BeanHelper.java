package com.crm.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

public class BeanHelper {

      /**
              * @author 郑明亮
     * @Email zhengmingliang911@gmail.com
     * @Time 2017年2月14日 下午5:14:25
            * @Description <p>获取到对象中属性为null的属性名  </P>
            * @param source 要拷贝的对象
     * @return
             */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /*//**
            * @author 郑明亮
     * @Email zhengmingliang911@gmail.com
     * @Time 2017年2月14日 下午5:15:30
            * @Description <p> 拷贝非空对象属性值 </P>
            * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getExcludePropertyNames(Object source, List<String> excludeAttrs) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if(!excludeAttrs.contains(pd.getName()))
            {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesExcludeAttr(Object source, Object target, String[] excludeAttrs) {
        BeanUtils.copyProperties(source, target, excludeAttrs);
    }

    public static void copyPropertiesExcludeSomeAndNullAttr(Object source, Object target, String[] excludeAttrs) {
        String[] nullProperties =getNullPropertyNames(source);
        List<String> newExcludeAttr = new ArrayList<>();
        List<String> nullL = Arrays.asList(nullProperties);
        newExcludeAttr.addAll(nullL);
        List<String>  excueL = Arrays.asList(excludeAttrs);
        newExcludeAttr.addAll(excueL);

        String[] newExcludes =  newExcludeAttr.toArray(new String[newExcludeAttr.size()]);
        BeanUtils.copyProperties(source, target, newExcludes);
    }
}
