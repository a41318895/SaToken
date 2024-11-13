package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.akichou.satokentest.constant.Constant.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/login")
    public SaResult isLogin() {

        if (StpUtil.isLogin()) {

            log.info("(ID={}) was checked for its login status", StpUtil.getLoginId()) ;

            return SaResult.ok(LOGIN_STATUS) ;
        }

        log.info("(ID=NOT EXIST) was checked for its login status") ;

        return SaResult.ok(NO_LOGIN_STATUS) ;
    }

    @GetMapping("/tokenInfo")
    public SaResult getTokenInfo() {

        log.info("The token information of (ID={}) was gotten", StpUtil.getLoginId()) ;

        return SaResult.data(StpUtil.getTokenInfo()) ;
    }
}
