package com.asanhospital.server.resolver;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.service.Jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class ManagerArgumentResolver implements HandlerMethodArgumentResolver{
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ManagerObject.class) != null
            && parameter.getParameterType().equals(Manager.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = jwtTokenProvider.resolveToken(webRequest.getNativeRequest(HttpServletRequest.class));

        if(token != null){
            return jwtTokenProvider.getManagerFromToken(token);
        }

        return null;
    }
}
