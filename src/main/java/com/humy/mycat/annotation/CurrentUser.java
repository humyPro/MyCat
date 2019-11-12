package com.humy.mycat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Milo Hu
 * @Date: 11/11/2019 10:51
 * @Description: 在handler上通过此注解，可以注入当前请求用户的User对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CurrentUser {

    /**
     * 在request scope中作为attribute的属性名
     */
    String value() default "currentUser";
}
