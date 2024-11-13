package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quit")
@Slf4j
public class KickOutController {

    // sa token timeout turns to -2 (enable to use this token)
    @GetMapping("/logout")
    public SaResult getLogoutTokenInfo() {

        log.info("(ID={}) was be logout for forcibly logout operation", StpUtil.getLoginId()) ;

        StpUtil.logout(StpUtil.getLoginId()) ;

        return SaResult.ok(String.valueOf(StpUtil.getTokenInfo())) ;
    }

    // sa token timeout still exist (but the userId bound was erased from its sa token)
    @GetMapping("/kickOut")
    public SaResult getKickOutTokenInfo() {

        log.info("(ID={}) was kicked out for forcibly kick out operation", StpUtil.getLoginId()) ;

        StpUtil.kickout(StpUtil.getLoginId()) ;

        return SaResult.ok(String.valueOf(StpUtil.getTokenInfo())) ;
    }
}