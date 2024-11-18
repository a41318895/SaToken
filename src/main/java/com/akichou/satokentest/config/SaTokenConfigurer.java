package com.akichou.satokentest.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@Slf4j
public class SaTokenConfigurer implements WebMvcConfigurer {

    @PostConstruct
    public void init() {

        SaTokenEventCenter.removeListener(SaTokenListenerForLog.class) ;

        log.info("Removed the default log listener from event center...") ;
    }

    // Register Sa-Token Interceptor in order to enable annotation authentication function
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SaInterceptor(handle -> {

            // Make all methods need login check, except login method itself.
            SaRouter
                    .match("/**").notMatch("/login/**", "/admin/**")
                    .check(check -> StpUtil.checkLogin()) ;

            SaRouter
                    .match("/user/**", "/logout")
                    .check(check -> StpUtil.checkPermission("user.operate")) ;

            SaRouter.match("/quit/**", check -> StpUtil.checkPermission("quit.operate")) ;
            SaRouter.match("/perm/**", check -> StpUtil.checkPermission("perm.operate")) ;
            SaRouter.match("/auth/**", check -> StpUtil.checkPermission("auth.operate")) ;
            SaRouter.match("/info/**", check -> StpUtil.checkPermission("info.operate")) ;

            SaRouter.match("/encryption/**", check -> StpUtil.checkRole("admin")) ;

        }).isAnnotation(false))

                .addPathPatterns("/**") ;
    }

    // The execute time of Filter is more previous than interceptor
    @Bean
    public SaServletFilter getSaServletFilter() {

        return new SaServletFilter()

                // Filtered paths
                .addInclude("/**").addExclude("/favicon.ico")

                .setBeforeAuth(response -> {

                    // Set response header
                    SaHolder.getResponse()

                            // Set server name
                            .setServer("SaServer")

                            // Make page can't display view port in other iframe
                            .setHeader("X-Frame-Options", "DENY")

                            // Enable XS protection, and stop rendering page when detects XSS attack
                            .setHeader("X-XSS-Protection", "1; mode=block")

                            // Forbid browser content sniff
                            .setHeader("X-Content-Type-Options", "nosniff") ;
                })

                .setAuth(auth -> {

                    log.info("----- Entered the global authentication of SaServletFilter -----") ;

                    SaRouter.match("/**").notMatch("/login/**", "/admin/**")
                            .check(StpUtil::checkLogin) ;

                    SaRouter
                            .match("/user/**", "/logout")
                            .check(check -> StpUtil.checkPermission("user.operate")) ;

                    SaRouter.match("/quit/**", check -> StpUtil.checkPermission("quit.operate")) ;
                    SaRouter.match("/perm/**", check -> StpUtil.checkPermission("perm.operate")) ;
                    SaRouter.match("/auth/**", check -> StpUtil.checkPermission("auth.operate")) ;
                    SaRouter.match("/info/**", check -> StpUtil.checkPermission("info.operate")) ;

                    SaRouter.match("/encryption/**", check -> StpUtil.checkRole("admin")) ;
                })

                // ( Global Exception handling )
                /*
                   Thrown exceptions in filter can't be caught by
                   Spring's Global Exception Handling Mechanism.
                 */
                .setError(e -> {

                    log.info("----- Entered the global exception handling of SaServletFilter -----") ;

                    return SaResult.error(e.getMessage()) ;
                }) ;
    }
}
