package com.akichou.satokentest.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.util.StpKit;
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

        SaManager.getConfig()
                        .setTokenName("saToken")
                        .setTokenStyle("uuid")
                        .setIsShare(true)
                        .setIsConcurrent(true)
                        .setIsReadCookie(true)
                        .setIsReadHeader(true)
                        .setAutoRenew(true)
                        .setActiveTimeout(-1)
                        .setIsLog(true)
                        .setTokenSessionCheckLogin(true) ;

        log.info("Removed the default log listener from event center...") ;
    }

    // Register Sa-Token Interceptor in order to enable annotation authentication function
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new SaInterceptor(handle -> {
//
//            // Make all methods need login check, except login method itself.
//            SaRouter.match("/user/**").notMatch("/user/login")
//                    .check(StpKit.USER::checkLogin) ;
//
//            SaRouter.match("/admin/**").notMatch("/admin/login")
//                    .check(StpKit.ADMIN::checkLogin) ;
//
//            SaRouter.match("/user/**", "/user/login", check -> StpKit.USER.checkRole("user")) ;
//
//            SaRouter.match("/admin/**", "/admin/login", check -> StpKit.ADMIN.checkRole("admin")) ;
//
//            SaRouter.match("/quit/**", check -> StpUtil.checkPermission("quit.operate")) ;
//            SaRouter.match("/perm/**", check -> StpUtil.checkPermission("perm.operate")) ;
//            SaRouter.match("/auth/**", check -> StpUtil.checkPermission("auth.operate")) ;
//            SaRouter.match("/info/**", check -> StpUtil.checkPermission("info.operate")) ;
//
//            SaRouter.match("/encryption/**", check -> StpUtil.checkRole("admin")) ;
//
//        }).isAnnotation(false))
//
//                .addPathPatterns("/**") ;
//    }

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
                            .setHeader("X-Content-Type-Options", "nosniff")

                            // Set Cors supports
                            .setHeader("Access-Control-Allow-Origin", "*")
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
                            .setHeader("Access-Control-Max-Age", "3600")
                            .setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, saToken") ;
                })

                .setAuth(auth -> {

                    log.info("----- Entered the global authentication of SaServletFilter -----") ;

                    // Pass the login API
                    if (SaHolder.getRequest().getRequestPath().equals("/user/login") ||
                        SaHolder.getRequest().getRequestPath().equals("/admin/login")) return ;

                    SaRouter.match("/user/**").check(r -> {

                        StpKit.USER.checkLogin() ;
                        StpKit.USER.checkRole("user") ;
                    }) ;

                    SaRouter.match("/admin/**").check(r -> {

                        StpKit.ADMIN.checkLogin() ;
                        StpKit.ADMIN.checkRole("admin") ;
                    }) ;

//                    SaRouter.match("/quit/**", check -> StpUtil.checkPermission("quit.operate")) ;
//                    SaRouter.match("/perm/**", check -> StpUtil.checkPermission("perm.operate")) ;
//                    SaRouter.match("/auth/**", check -> StpUtil.checkPermission("auth.operate")) ;
//                    SaRouter.match("/info/**", check -> StpUtil.checkPermission("info.operate")) ;

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
