package com.akichou.satokentest.controller;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService ;

    @GetMapping("/disableAccount/Info")
    public SaResult getDisableInfo(@RequestParam("userId") Long userId) {

        return adminService.getDisableInfo(userId) ;
    }

    @PostMapping("/disableAccount")
    public SaResult disableAccount(@RequestParam("userId") Long userId) {

        return adminService.disableAccount(userId) ;
    }

    @PostMapping("/kick/disableAccount")
    public SaResult kickThenDisableAccount(@RequestParam("userId") Long userId) {

        return adminService.kickThenDisableAccount(userId) ;
    }

    @PostMapping("/accountUntie")
    public SaResult untieAccountFromDisable(@RequestParam("userId") Long userId) {

        return adminService.untieAccountFromDisable(userId) ;
    }

    @PostMapping("/login")
    public SaResult loginAdmin(@Validated @RequestBody LoginDto loginDto) {

        return adminService.loginAdmin(loginDto) ;
    }

    @GetMapping("/loginStatus")
    public SaResult isLogin(@RequestParam(value = "adminId") Long adminId) {

        return adminService.isLogin(adminId) ;
    }

    @GetMapping("/tokenInfo")
    public SaResult getTokenInfo(@RequestParam(value = "adminId") Long adminId) {

        return adminService.getTokenInfo(adminId) ;
    }

    @PostMapping("/logout")
    public SaResult logout() {

        return adminService.logout() ;
    }
}
