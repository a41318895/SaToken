package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;
import com.akichou.satokentest.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.akichou.satokentest.constant.Constant.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService ;

    private SaResult isLogin() {

        if (StpUtil.isLogin()) {

            log.info("(ID={}) was checked for its login status", StpUtil.getLoginId()) ;

            return SaResult.ok(LOGIN_STATUS) ;
        }

        log.info("(ID=NOT EXIST) was checked for its login status") ;

        return SaResult.ok(NO_LOGIN_STATUS) ;
    }

    private SaResult getTokenInfo() {

        log.info("The token information of (ID={}) was gotten", StpUtil.getLoginId()) ;

        return SaResult.data(StpUtil.getTokenInfo()) ;
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
