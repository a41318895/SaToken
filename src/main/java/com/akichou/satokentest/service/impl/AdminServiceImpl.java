package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.Admin;
import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.bo.AdminBo;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.entity.dto.UserDto;
import com.akichou.satokentest.entity.dto.UserUpdateDto;
import com.akichou.satokentest.entity.vo.UserUpdatedVo;
import com.akichou.satokentest.enumeration.CRUDLogType;
import com.akichou.satokentest.enumeration.HttpCodeEnum;
import com.akichou.satokentest.global.exception.SystemException;
import com.akichou.satokentest.mapper.UserMapper;
import com.akichou.satokentest.repository.AdminRepository;
import com.akichou.satokentest.repository.UserRepository;
import com.akichou.satokentest.service.AdminService;
import com.akichou.satokentest.util.StpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static com.akichou.satokentest.constant.Constant.*;
import static com.akichou.satokentest.enumeration.HttpCodeEnum.ADMIN_NOT_FOUND;
import static com.akichou.satokentest.enumeration.HttpCodeEnum.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository ;
    private final UserRepository userRepository ;

    @Override
    public SaResult disableAccount(Long userId) {

        disableAccount(userId, DISABLE_TIME_SECOND) ;

        logDisableAccountMsg(userId) ;

        return SaResult.ok(ACCOUNT_DISABLED_MESSAGE + userId) ;
    }

    @Override
    public SaResult kickThenDisableAccount(Long userId) {

        StpUtil.kickout(userId) ;

        disableAccount(userId, DISABLE_TIME_SECOND) ;

        logDisableAccountMsg(userId) ;

        return SaResult.ok(ACCOUNT_DISABLED_MESSAGE + userId) ;
    }

    @Override
    public SaResult untieAccountFromDisable(Long userId) {

        StpUtil.untieDisable(userId) ;

        String result = MessageFormat.format(UNTIE_DISABLE_MESSAGE, userId) ;

        log.info(result) ;

        return SaResult.ok(result) ;
    }

    private void disableAccount(Long userId, long timeout) {

        StpUtil.disable(userId, timeout) ;
    }

    private void logDisableAccountMsg(Long userId) {

        log.warn(ACCOUNT_DISABLED_MESSAGE + "{}", userId) ;
    }

    @Override
    public SaResult getDisableInfo(Long userId) {

        boolean isDisabled = StpUtil.isDisable(userId) ;

        long disabledTime = StpUtil.getDisableTime(userId) ;

        String disabledTimeDescription ;

        if (disabledTime == -1) {

            disabledTimeDescription = DISABLE_TIME_INFINITE_DESCRIPTION ;

        } else if (disabledTime == -2) {

            disabledTimeDescription = DISABLE_TIME_ZERO ;

        } else {

            disabledTimeDescription = disabledTime + " sec" ;
        }

        String result = MessageFormat.format(DISABLE_INFO_RETRIEVAL_MESSAGE,
                userId,
                isDisabled ? ACCOUNT_STATUS_DISABLED : ACCOUNT_STATUS_HEALTHY,
                disabledTimeDescription) ;

        log.info(result) ;

        return SaResult.ok(result) ;
    }

    @Override
    public SaResult loginAdmin(LoginDto loginDto) {

        Admin admin = queryLoginAdmin(loginDto) ;
        Long adminId = admin.getId() ;

        StpKit.ADMIN.logout(adminId) ;

        StpKit.ADMIN.login(adminId,
                new SaLoginModel()
                        .setDevice(LOGIN_DEVICE)
                        .setTimeout(TOKEN_TIMEOUT)
                        .setIsLastingCookie(false));

        SaTokenInfo tokenInfo = StpKit.ADMIN.getTokenInfo() ;

        // Logging
        logAdminLogin(adminId) ;

        // Session Setting
        setAdminBoToSession(admin) ;

        SaResult saResult = new SaResult() ;
        saResult.setMsg(ADMIN_LOGIN_SUCCESS_MSG) ;
        saResult.setData(tokenInfo) ;

        return saResult ;
    }

    @Override
    public SaResult isLogin(Long adminId) {

        // Check existence from DB
        checkIsAdminExists(adminId, ADMIN_LOGIN_STATUS_CHECK_FAIL_MSG_WITH_ID) ;

        boolean isLogin = StpKit.ADMIN.isLogin(adminId) ;

        log.info(ADMIN_LOGIN_STATUS_CHECK_MSG_WITH_ID, adminId, isLogin ? ADMIN_LOGIN_MESSAGE : ADMIN_NO_LOGIN_MESSAGE) ;

        return SaResult.ok(isLogin ? LOGIN_STATUS : NO_LOGIN_STATUS) ;
    }

    @Override
    public SaResult getTokenInfo(Long adminId) {

        // Check existence from DB
        checkIsAdminExists(adminId, ADMIN_TOKEN_INFO_RETRIEVAL_FAIL_MSG_WITH_ID) ;

        SaTokenInfo saTokenInfo = StpKit.ADMIN.getTokenInfo() ;

        // Logging
        log.info(ADMIN_TOKEN_INFO_RETRIEVAL_SUCCESS_MSG_WITH_ID, adminId) ;

        return SaResult.data(saTokenInfo) ;
    }

    @Override
    public SaResult logout() {

        Object loginId = Long.valueOf(StpKit.ADMIN.getLoginId().toString());

        StpKit.ADMIN.logout() ;

        // Logging
        logAdminLogout(loginId) ;

        return SaResult.ok(ADMIN_LOGOUT_SUCCESS_MSG) ;
    }

    @Override
    public SaResult createUser(UserDto userDto) {

        Long adminId = fetchAdminLoginIdInternal() ;

        // Create new user
        User createdUser = UserMapper.mapUserDtoToUser(userDto, adminId) ;
        User savedUser = userRepository.save(createdUser) ;
        Long userId = savedUser.getId() ;

        // Logging
        logCRUDOperation(adminId, userId, CRUDLogType.CREATE) ;

        // Returning Handling
        String result = MessageFormat.format(ADMIN_CREATE_USER_MSG, userId, adminId) ;
        SaResult saResult = new SaResult() ;
        saResult.setCode(201) ;
        saResult.setMsg(result) ;

        return saResult ;
    }

    @Override
    public SaResult updateUser(UserUpdateDto userUpdateDto) {

        Long adminId = fetchAdminLoginIdInternal() ;

        // Fetch origin user from DB
        User user = userRepository.findById(userUpdateDto.getId()).orElseThrow(() -> new SystemException(USER_NOT_FOUND)) ;

        // Update user
        User updatedUser = UserMapper.mapUserUpdateDtoToUser(user, userUpdateDto, adminId) ;
        User savedUser = userRepository.save(updatedUser) ;
        Long userId = savedUser.getId() ;

        // Logging
        logCRUDOperation(adminId, userId, CRUDLogType.UPDATE) ;

        // Returning Handling
        String result = MessageFormat.format(ADMIN_UPDATE_USER_MSG, userId, adminId) ;
        UserUpdatedVo userUpdatedVo = UserMapper.mapUserToUserUpdatedVo(savedUser) ;
        SaResult saResult = new SaResult() ;
        saResult.setCode(200) ;
        saResult.setMsg(result) ;
        saResult.setData(userUpdatedVo) ;

        return saResult ;
    }

    @Override
    public SaResult deleteUser(Long userId) {

        Long adminId = fetchAdminLoginIdInternal() ;

        // Delete user
        User deletedUser = userRepository.findById(userId).orElseThrow(() -> new SystemException(HttpCodeEnum.USER_NOT_FOUND)) ;
        userRepository.delete(deletedUser) ;

        // Logging
        logCRUDOperation(adminId, userId, CRUDLogType.DELETE) ;

        // Returning Handling
        String result = MessageFormat.format(ADMIN_DELETE_USER_MSG, userId, adminId) ;
        SaResult saResult = new SaResult() ;
        saResult.setCode(204) ;
        saResult.setMsg(result) ;

        return saResult ;
    }

    private Admin queryLoginAdmin(LoginDto loginDto) {

        final var username = loginDto.getUsername() ;
        final var password = loginDto.getPassword() ;

        return adminRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new SystemException(ADMIN_NOT_FOUND)) ;
    }

    private void logAdminLogin(Object adminId) {

        log.info(ADMIN_LOGIN_SUCCESS_MSG_WITH_ID, adminId) ;
    }

    private void setAdminBoToSession(Admin admin) {

        // Session : Set AdminBo entity to account session with "admin" key
        AdminBo adminBo = new AdminBo(admin.getId(), admin.getUsername()) ;

        StpKit.ADMIN.getSession().set(ACCOUNT_SESSION_KEY_ADMIN, adminBo) ;
    }

    private void checkIsAdminExists(Long adminId, String loggedMessage) {

        if (!adminRepository.existsById(adminId)) {

            log.warn(loggedMessage, adminId) ;

            throw new SystemException(ADMIN_NOT_FOUND) ;
        }
    }

    private void logAdminLogout(Object adminId) {

        log.info(ADMIN_LOGOUT_SUCCESS_MSG_WITH_ID, adminId) ;
    }

    private void logCRUDOperation(Object adminId, Object userId, CRUDLogType type) {

        String typeString = "" ;
        switch (type) {

            case READ -> typeString = "read" ;
            case CREATE -> typeString = "create" ;
            case UPDATE -> typeString = "update" ;
            case DELETE -> typeString = "delete" ;
        }

        log.info("Admin (ID = {}) {} the user (ID = {})", adminId, typeString, userId) ;
    }

    private Long fetchAdminLoginIdInternal() {

        return StpKit.ADMIN.getLoginIdAsLong() ;
    }
}
