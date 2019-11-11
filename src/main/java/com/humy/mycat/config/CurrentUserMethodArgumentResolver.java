package com.humy.mycat.config;

import com.humy.mycat.annotation.CurrentUser;
import com.humy.mycat.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @Author: Milo Hu
 * @Date: 11/11/2019 11:43
 * @Description:
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentUser ano = parameter.getParameterAnnotation(CurrentUser.class);
        if (ano == null)
            throw new MissingServletRequestPartException(CurrentUser.class.getName());
        return webRequest.getAttribute(ano.value(), RequestAttributes.SCOPE_REQUEST);
    }
}
