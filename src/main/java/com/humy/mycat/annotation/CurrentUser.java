package com.humy.mycat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Milo Hu
 * @Date: 11/11/2019 10:51
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CurrentUser {

    String value() default "currentUser";
}
