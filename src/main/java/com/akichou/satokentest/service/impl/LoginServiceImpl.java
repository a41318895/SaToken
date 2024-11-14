package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.global.exception.UserNotFoundException;
import com.akichou.satokentest.repository.LoginRepository;
import com.akichou.satokentest.service.interfaces.LoginService;
import com.akichou.satokentest.entity.bo.UserBo ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository ;

    @Override
    public SaResult loginWithDefaultRememberMe(LoginDto loginDto) {

        User user = queryLoginUser(loginDto) ;

        // Token : Login with remember me mode, and token exists for 30 days (default : sa-token.timeout=2592000)
        StpUtil.login(user.getId(), true) ;

        // Logging
        logUserLogin(user.getId()) ;

        // Session
        setUserBoToSession(user) ;

        return SaResult.ok("Login Successfully") ;
    }

    @Override
    public SaResult loginWithSaLoginModel(LoginDto loginDto) {

        final long timeout = 60 * 60 * 24 * 7 ;

        User user = queryLoginUser(loginDto) ;

        StpUtil.login(user.getId(),
                new SaLoginModel()
                        .setIsLastingCookie(true)      // Token : Login with remember me mode
                        .setTimeout(timeout)) ;      // Token : exist for 7 days

        // Logging
        logUserLogin(user.getId()) ;

        // Session
        setUserBoToSession(user, timeout) ;

        return SaResult.ok("Login Successfully") ;
    }

    @Override
    public SaResult loginWithForget(LoginDto loginDto) {

        User user = queryLoginUser(loginDto) ;

        // Send a temporary cookie with token which is available in session one time to client side
        StpUtil.login(user.getId(), false) ;

        // Logging
        logUserLogin(user.getId()) ;

        // Session
        setUserBoToSession(user) ;

        return SaResult.ok("Login Successfully") ;
    }

    @Override
    public SaResult logout() {

        Long loginId = Long.valueOf((String)StpUtil.getLoginId()) ;

        StpUtil.logout() ;

        logUserLogout(loginId) ;

        return SaResult.ok("Logout Successfully") ;
    }

    private User queryLoginUser(LoginDto loginDto) {

        final var username = loginDto.getUsername() ;

        return loginRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username: [ " + username + " ] Not Found")) ;
    }

    private void logUserLogin(Long userId) {

        log.info("(ID={}) Login Successfully", userId) ;
    }

    private void logUserLogout(Long userId) {

        log.info("(ID={}) Logout Successfully", userId) ;
    }

    private void setUserBoToSession(User user) {

        // Session : Set UserBo entity to account session with "user" key
        UserBo userBo = new UserBo(user.getId(), user.getUsername()) ;
        StpUtil.getSession().set("user", userBo) ;
    }

    private void setUserBoToSession(User user, long timeout) {

        SaSession saSession = StpUtil.getSession().setCreateTime(timeout) ;

        // Session : Set UserBo entity to account session with "user" key
        UserBo userBo = new UserBo(user.getId(), user.getUsername()) ;
        saSession.set("user", userBo) ;
    }
}
