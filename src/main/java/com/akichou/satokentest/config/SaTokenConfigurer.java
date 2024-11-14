package com.akichou.satokentest.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class SaTokenConfigurer implements WebMvcConfigurer {

    // Register Sa-Token Interceptor in order to enable annotation authentication function
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SaInterceptor(handle -> {

            // Make all methods need login check, except login method itself.
            SaRouter
                    .match("/**").notMatch("/login/**")
                    .check(check -> StpUtil.checkLogin()) ;

            SaRouter
                    .match("/user/**", "/logout")
                    .check(check -> StpUtil.checkPermission("user.operate")) ;

            SaRouter.match("/quit/**", check -> StpUtil.checkPermission("quit.operate")) ;
            SaRouter.match("/perm/**", check -> StpUtil.checkPermission("perm.operate")) ;
            SaRouter.match("/auth/**", check -> StpUtil.checkPermission("auth.operate")) ;
            SaRouter.match("/info/**", check -> StpUtil.checkPermission("info.operate")) ;

        }).isAnnotation(false))

                .addPathPatterns("/**") ;
    }
}
