package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InformationController {

    @GetMapping
    public SaResult getUserInfo() {

        Object user = StpUtil.getSession().get("user") ;

        return SaResult.data(user.toString()) ;
    }
}
