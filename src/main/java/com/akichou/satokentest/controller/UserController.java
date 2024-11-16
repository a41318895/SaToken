package com.akichou.satokentest.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;
import com.akichou.satokentest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService ;

    @SaCheckRole("admin")
    @GetMapping("/loginStatus")
    public SaResult isLogin(@RequestParam(value = "userId") Long userId) {

        return userService.isLogin(userId) ;
    }

    @SaCheckRole("admin")
    @GetMapping("/tokenInfo")
    public SaResult getTokenInfo(@RequestParam(value = "userId") Long userId) {

        return userService.getTokenInfo(userId) ;
    }

    @GetMapping("/note")
    public SaResult getNoteContent() {

        return userService.getNoteContent() ;
    }

    @PutMapping("/note")
    public SaResult updateUserNote(@Validated @RequestBody UserNoteDto userNoteDto) {

        return userService.updateUserNote(userNoteDto) ;
    }

    @DeleteMapping("/note")
    public SaResult deleteUserNote() {

        return userService.deleteUserNote() ;
    }

    @PostMapping("/twoFactorAuth")
    public SaResult doTwoFactorAuthentication(@Validated @RequestBody TwoFactorAuthDto twoFactorAuthDto) {

        return userService.doTwoFactorAuthentication(twoFactorAuthDto) ;
    }
}
