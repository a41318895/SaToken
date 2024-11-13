package com.akichou.satokentest.controller;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

import static com.akichou.satokentest.constant.Constant.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @SaCheckLogin
    @GetMapping("/userIdAndUsername")
    public SaResult getUserIdAndUsername() {

        String result =
                MessageFormat.format("UserId: {0}, Username: {1}",
                                StpUtil.getLoginId(), MOCK_USER_USERNAME) ;

        log.info("Passed the SaCheckLogin annotation authentication to get the user id and username") ;

        return SaResult.data(result) ;
    }

    @SaCheckPermission("user.add")
    @PostMapping("/addOperation")
    public SaResult addOperation() {

        log.info("Passed the SaCheckPermission annotation authentication to perform add Operation") ;

        return SaResult.ok("Added Successfully") ;
    }

    @SaCheckPermission("user.delete")
    @DeleteMapping("/deleteOperation")
    public SaResult deleteOperation() {

        log.info("Passed the SaCheckPermission annotation authentication to perform delete Operation") ;

        return SaResult.ok("Deleted Successfully") ;
    }

    @SaCheckRole(value = {"writer", "admin"}, mode = SaMode.AND)
    @PutMapping("/article")
    public SaResult updateArticleOperation() {

        log.info("Passed the SaCheckRole annotation authentication to perform update article Operation") ;

        return SaResult.ok("Updated Article Successfully") ;
    }

    @SaCheckPermission(value = "user.manage", orRole = {"admin", "tester"})
    @GetMapping("/user")
    public SaResult getAllUsersInfo() {

        log.info("Passed the SaCheckPermission annotation authentication to get all users' info") ;

        return SaResult.ok("All Users' Information Here") ;
    }

    @SaIgnore
    @GetMapping("/ignore")
    public SaResult getPublicInfo() {

        return SaResult.ok("Public Info") ;
    }
}
