package com.akichou.satokentest.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaAnnotationStrategy;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.util.StpKit;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.core.annotation.AnnotatedElementUtils;

@Configuration
@Slf4j
public class SaTokenConfigurer implements WebMvcConfigurer {

    @PostConstruct
    public void init() {

        // Remove the default event listener
        SaTokenEventCenter.removeListener(SaTokenListenerForLog.class) ;

        // Set a custom config
        SaManager.getConfig()
                        .setTokenName("saToken")    // Token name ( Cookie name as well )
                        .setTokenStyle("uuid")      // ( uuid, simple-uuid, random-32, random-64, random-128, tik )
                        .setIsShare(true)       // Is the same token when login with same username
                        .setIsConcurrent(true)      // Can same username login with multiple devices
                        .setIsReadCookie(true)      // Does read token from cookie
                        .setIsReadHeader(true)      // Does read token from header
                        .setAutoRenew(true)         // Automatically renew token timeout
                        .setSameTokenTimeout(2592000)    // Token expiration (s),
                                                         // default 30 days, -1: permanent, -2: unable to use
                        .setActiveTimeout(-1)   // token min active timeout (s)
                                                // If token hasn't visit over this timeout,
                                                // it will be frozen, default: -1 which it represented that never be frozen
                        .setIsLog(true)     // Active log displaying
                        .setTokenSessionCheckLogin(true) ;      // Check isLogin when get token session


        // Rewrite sa annotation strategy, adding annotation merging function
        SaAnnotationStrategy.instance.getAnnotation =
                AnnotatedElementUtils::getMergedAnnotation ;
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

                    log.error("Error Message: {}", e.getMessage()) ;

                    return SaResult.error(e.getMessage()) ;
                }) ;
    }
}
