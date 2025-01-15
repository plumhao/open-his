package com.jinken.openhis.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.jinken.openhis.commons.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.jinken.openhis.commons.enums.ErrorCodeAndMessageEnum.NO_LOGIN_ERROR;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!StpUtil.isLogin()) {
            throw new BusinessException(NO_LOGIN_ERROR);
        }
        return true;
    }
}
