package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.SpecificUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.akichou.satokentest.constant.Constant.*;
import static com.akichou.satokentest.constant.Constant.MOCK_USER_ID;

@RestController
@RequestMapping
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public SaResult login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        if (MOCK_USER_USERNAME.equals(username) && MOCK_USER_PASSWORD.equals(password)) {

            StpUtil.login(MOCK_USER_ID) ;

            log.info("(ID={}) Login Successfully", MOCK_USER_ID) ;

            // Set SpecificUser entity to account session with "user" key
            StpUtil.getSession().set("user", new SpecificUser()) ;

            return SaResult.ok("Login Successfully") ;
        }

        return SaResult.error("Login Failed") ;
    }


    @PostMapping("/logout")
    public SaResult logout() {

        Long LoginId = Long.valueOf((String)StpUtil.getLoginId()) ;

        StpUtil.logout() ;

        log.info("(ID={}) Logout Successfully", LoginId) ;

        return SaResult.ok("Logout Successfully") ;
    }
}
