package com.humy.mycat.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Milo Hu
 * @Date: 11/6/2019 15:35
 * @Description:
 */
public class BeanUtil {

    public static <T> void copyPropertiesIgnoreNull(Object source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object obj) {
        BeanWrapperImpl src = new BeanWrapperImpl(obj);
        PropertyDescriptor[] ps = src.getPropertyDescriptors();
        Set<String> set = new HashSet<>(ps.length);
        for (PropertyDescriptor p : ps) {
            Object value = src.getPropertyValue(p.getName());
            if (value == null) {
                set.add(p.getName());
            }
        }
        return set.toArray(new String[0]);
    }

}
