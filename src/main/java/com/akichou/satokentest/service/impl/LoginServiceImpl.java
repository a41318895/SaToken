package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.entity.dto.UserIdAndDeviceDto;
import com.akichou.satokentest.enumeration.HttpCodeEnum;
import com.akichou.satokentest.global.exception.SystemException;
import com.akichou.satokentest.repository.UserRepository;
import com.akichou.satokentest.service.LoginService;
import com.akichou.satokentest.entity.bo.UserBo ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static com.akichou.satokentest.constant.Constant.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public SaResult loginWithDefaultRememberMe(LoginDto loginDto) {

        User user = queryLoginUser(loginDto) ;

        // Token : Login with remember me mode, and token exists for 30 days (default : sa-token.timeout=2592000)
        StpUtil.login(user.getId(), true) ;

        // Logging
        logUserLogin(user.getId()) ;

        // Session
        setUserBoToSession(user) ;

        return SaResult.ok(LOGIN_SUCCESS_MESSAGE) ;
    }

    @Override
    public SaResult loginWithSaLoginModel(LoginDto loginDto) {

        final long timeout = TOKEN_TIMEOUT ;

        User user = queryLoginUser(loginDto) ;

        StpUtil.login(user.getId(),
                new SaLoginModel()
                        .setIsLastingCookie(true)      // Token : Login with remember me mode
                        .setTimeout(timeout)) ;      // Token : exist for 7 days

        // Logging
        logUserLogin(user.getId()) ;

        // Session
        setUserBoToSession(user, timeout) ;

        return SaResult.ok(LOGIN_SUCCESS_MESSAGE) ;
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

        return SaResult.ok(LOGIN_SUCCESS_MESSAGE) ;
    }

    @Override
    public SaResult loginWithDevicePc(LoginDto loginDto) {

        User user = queryLoginUser(loginDto) ;

        // Login with device PC
        StpUtil.login(user.getId(),
                new SaLoginModel()
                        .setIsLastingCookie(true)
                        .setTimeout(TOKEN_TIMEOUT)
                        .setDevice(LOGIN_DEVICE)) ;

        // Logging
        logUserLogin(user.getId()) ;

       // Session
       setUserBoToSession(user, TOKEN_TIMEOUT) ;

       return SaResult.ok(LOGIN_SUCCESS_MESSAGE) ;
    }

    private User queryLoginUser(LoginDto loginDto) {

        final var username = loginDto.getUsername() ;
        final var password = loginDto.getPassword() ;

        User foundUser = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new SystemException(HttpCodeEnum.USER_NOT_FOUND)) ;

        return foundUser ;
    }

    private void setUserBoToSession(User user) {

        // Session : Set UserBo entity to account session with "user" key
        UserBo userBo = new UserBo(user.getId(), user.getUsername()) ;
        StpUtil.getSession().set(ACCOUNT_SESSION_KEY_USER, userBo) ;
    }

    private void setUserBoToSession(User user, long timeout) {

        SaSession saSession = StpUtil.getSession().setCreateTime(timeout) ;

        // Session : Set UserBo entity to account session with "user" key
        UserBo userBo = new UserBo(user.getId(), user.getUsername()) ;
        saSession.set(ACCOUNT_SESSION_KEY_USER, userBo) ;
    }

    @Override
    public SaResult logout() {

        Long loginId = Long.valueOf((String)StpUtil.getLoginId()) ;

        StpUtil.logout() ;

        logUserLogout(loginId) ;

        return SaResult.ok(LOGOUT_SUCCESS_MESSAGE) ;
    }

    @Override
    public SaResult logoutWithIdAndDevice(UserIdAndDeviceDto userIdAndDeviceDto) {

        Long targetId = userIdAndDeviceDto.getUserId() ;
        String device = userIdAndDeviceDto.getDevice() ;

        StpUtil.logout(targetId, device) ;

        // Logging
        logUserLogout(targetId) ;

        String result =
                MessageFormat.format(LOGOUT_SUCCESS_MESSAGE_WITH_ID_AND_DEVICE,
                        targetId,
                        device) ;

        return SaResult.ok(result) ;
    }

    private void logUserLogin(Long userId) {

        log.info(LOGIN_SUCCESS_MESSAGE_WITH_ID, userId) ;
    }

    private void logUserLogout(Long userId) {

        log.info(LOGOUT_SUCCESS_MESSAGE_WITH_ID, userId) ;
    }
}
