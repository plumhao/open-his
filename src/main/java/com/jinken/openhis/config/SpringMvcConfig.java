package com.jinken.openhis.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.jinken.openhis.interceptor.CheckLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {


    @Autowired
    private CheckLoginInterceptor checkLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册登录检查拦截器
        registry.addInterceptor(checkLoginInterceptor)
                .addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns("/login/doLogin"); //放行登录

        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**");
    }
}
