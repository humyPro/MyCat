package com.humy.mycat.interceptor.auth;

import com.humy.mycat.constant.Header;
import com.humy.mycat.util.RedisUtil;
import com.humy.mycat.vo.Device;
import com.humy.mycat.vo.Token;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:47
 * @Description:
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final boolean needAuth = true;

    @Autowired
    private RedisUtil redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!needAuth) {
            return true;
        }
        String tokenStr = request.getHeader(Header.AUTHORIZATION);
        String deviceId = request.getHeader(Header.DEVICE_ID);
        if (tokenStr == null || deviceId == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Claims claims = Jwt.getClaims(tokenStr);
        if (claims == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        Long id = Long.valueOf(claims.getSubject());
        Token token = redis.getToken(id);

        if (token == null) {

        }

        List<Device> devices = token.getDevices();
        if (devices == null || devices.size() == 0) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        for (Device device : devices) {
            if (deviceId.equals(device.getDeviceId())) {
                return true;
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
