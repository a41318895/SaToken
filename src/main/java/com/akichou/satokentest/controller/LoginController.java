package com.akichou.satokentest.controller;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.entity.dto.UserIdAndDeviceDto;
import com.akichou.satokentest.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService ;

//    @PostMapping("/login/remember/default")
//    public SaResult loginWithDefaultRememberMe(@Validated @RequestBody LoginDto loginDto) {
//
//        return loginService.loginWithDefaultRememberMe(loginDto) ;
//    }
//
//    @PostMapping("/login/remember/model")
//    public SaResult loginWithSaLoginModel(@Validated @RequestBody LoginDto loginDto) {
//
//        return loginService.loginWithSaLoginModel(loginDto) ;
//    }
//
//    @PostMapping("/login/forget")
//    public SaResult loginWithForget(@Validated @RequestBody LoginDto loginDto) {
//
//        return loginService.loginWithForget(loginDto) ;
//    }
//
//    @PostMapping("/login/device/pc")
//    public SaResult loginWithDevicePc(@Validated @RequestBody LoginDto loginDto) {
//
//        return loginService.loginWithDevicePc(loginDto) ;
//    }
//
//    @PostMapping("/logout")
//    public SaResult logout() {
//
//        return loginService.logout() ;
//    }
//
//    @PostMapping("/logout/idAndDevice")
//    public SaResult logoutWithIdAndDevice(@Validated @RequestBody UserIdAndDeviceDto userIdAndDeviceDto) {
//
//        return loginService.logoutWithIdAndDevice(userIdAndDeviceDto) ;
//    }
}
