package com.humy.mycat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Author: Milo Hu
 * @Date: 11/1/2019 14:24
 * @Description: 通过此注解，可以通过Spring Aop自动记录日志，一般用再类上，为所有方法提供日志功能
 */
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Logging {

}
