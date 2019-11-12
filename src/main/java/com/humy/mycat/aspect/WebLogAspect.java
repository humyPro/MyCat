package com.humy.mycat.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.LinkedList;

/**
 * @Author: Milo Hu
 * @Date: 11/1/2019 14:19
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * com.humy.mycat.controller..*.*(..))")//切入点描述 这个是controller包的切入点
    public void controllerLog() {
    }//签名，可以理解成这个切入点的一个名称

    @Pointcut("@within(com.humy.mycat.annotation.Logging)")
    public void logAnnotation() {
    }

//    @Before("logAnnotation()")
//    public void LoggerAnnotationBeforeLog(JoinPoint point) {
//
//    }
//
//    @AfterReturning(value = "logAnnotation()", returning = "returnVal")
//    public void LoggerAnnotationAfterLog(JoinPoint point, Object returnVal) {
//        Object[] args = point.getArgs();
//        Signature signature = point.getSignature();
//        logger.info(signature.getName(), args);
//    }

    @Around("logAnnotation()")
    public Object LoggerAnnotationAroundLog(ProceedingJoinPoint point) throws Throwable {
        String name = point.getSignature().toString();
        Object object = null;
        Object[] args = point.getArgs();
        LinkedList<Object> ps = new LinkedList<>();
        if (args != null) {
            for (Object arg : args) {
                if (!(arg instanceof ServletRequest || arg instanceof ServletResponse)) {
                    ps.add(arg);
                }
            }
        }
        try {
            object = point.proceed();
            log.info("\r\n{\"success\":{}}", new LogValue(name, ps, object));
            return object;
        } catch (Throwable throwable) {
            log.error("\r\n{\"error\":{}}", new LogValue(name, ps, object), throwable);
            throw throwable;
        }
    }

    @AllArgsConstructor
    @Getter
    private static class LogValue {

        private String method;

        private Object paramIn;

        private Object paramOut;

        @Override
        public String toString() {
            return JSON.toJSONString(this, SerializerFeature.IgnoreErrorGetter);
        }
    }
}
