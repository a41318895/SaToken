package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserIdAndDeviceDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;
import com.akichou.satokentest.entity.vo.UserNoteVo;
import com.akichou.satokentest.enumeration.HttpCodeEnum;
import com.akichou.satokentest.global.exception.SystemException;
import com.akichou.satokentest.repository.UserRepository;
import com.akichou.satokentest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static com.akichou.satokentest.constant.Constant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;

    @Override
    public SaResult getNoteContent() {

        User user = findUserByIdInternal() ;

        return SaResult.data(new UserNoteVo(user.getNote())) ;
    }

    @Override
    public SaResult updateUserNote(UserNoteDto userNoteDto) {

        User user = findUserByIdInternal() ;

        user.setNote(userNoteDto.getNoteContent()) ;

        userRepository.save(user) ;

        log.info(NOTE_SAVE_SUCCESS_MESSAGE_WITH_ID, user.getId()) ;

        return SaResult.ok(NOTE_SAVE_SUCCESS_MESSAGE) ;
    }

    @Override
    public SaResult deleteUserNote() {

        if (!StpUtil.isSafe(DELETE_SIGN)) {

            return SaResult.error(NOTE_DELETE_FAIL_MESSAGE) ;
        }

        User user = findUserByIdInternal() ;

        user.setNote("") ;

        log.info(NOTE_DELETE_SUCCESS_MESSAGE_WITH_ID, user.getId()) ;

        userRepository.save(user) ;

        return SaResult.ok(NOTE_DELETE_SUCCESS_MESSAGE) ;
    }

    @Override
    public SaResult doTwoFactorAuthentication(TwoFactorAuthDto twoFactorAuthDto) {

        User user = findUserByIdInternal() ;

        if (user.getPassword().equals(twoFactorAuthDto.getPassword())) {

            StpUtil.openSafe(DELETE_SIGN, SAFE_TIME_SECOND) ;     // Open two-factor authentication with 2 sec

            return SaResult.ok(AUTH_PASS_MESSAGE) ;
        }

        return SaResult.error(AUTH_FAIL_MESSAGE) ;
    }

    private User findUserByIdInternal() {

        Long userId = Long.valueOf((String)StpUtil.getLoginId()) ;

        return userRepository.findById(userId)
                .orElseThrow(() -> new SystemException(HttpCodeEnum.USER_NOT_FOUND)) ;
    }

    @Override
    public SaResult isLogin(Long userId) {

        if (!userRepository.existsById(userId)) {

            log.warn(LOGIN_STATUS_CHECK_FAIL_MESSAGE_WITH_ID, userId) ;
            return SaResult.error(USER_NOT_FOUND_MESSAGE) ;
        }

        boolean isLogin = StpUtil.isLogin(userId) ;
        log.info(LOGIN_STATUS_CHECK_MESSAGE_WITH_ID, userId, isLogin ? LOGIN_MESSAGE : NO_LOGIN_MESSAGE) ;

        return SaResult.ok(isLogin ? LOGIN_STATUS : NO_LOGIN_STATUS) ;
    }

    @Override
    public SaResult getTokenInfo(Long userId) {

        if(!userRepository.existsById(userId)) {

            log.warn(TOKEN_INFO_RETRIEVAL_FAIL_MESSAGE_WITH_ID, userId) ;
            return SaResult.error(USER_NOT_FOUND_MESSAGE) ;
        }

        SaTokenInfo saTokenInfo = switchToAndGet(userId, StpUtil::getTokenInfo) ;
        log.info(TOKEN_INFO_RETRIEVAL_SUCCESS_MESSAGE_WITH_ID, userId) ;

        return SaResult.data(saTokenInfo) ;
    }

    /**
     * Execute identification switch and return the getting result
     * @param userId user id of switching
     * @param supplier execute function
     * @return result
     */
    public static <T> T switchToAndGet(Long userId, Supplier<T> supplier) {

        AtomicReference<T> ref = new AtomicReference<>() ;

        try {

            StpUtil.switchTo(userId, () -> ref.set(supplier.get())) ;

        } catch (Exception e) {

            log.error(SESSION_SWITCH_FAIL_MESSAGE_WITH_ID_AND_EXCEPTION_MSG, userId, e.getMessage()) ;
            throw new RuntimeException(SESSION_SWITCH_FAIL_MESSAGE, e) ;
        }

        return ref.get() ;
    }

    @Override
    public SaResult getUserLoginDevice() {

        String loginDevice = StpUtil.getLoginDevice() ;

        return SaResult.ok(LOGIN_DEVICE_MESSAGE + loginDevice) ;
    }

    @Override
    public SaResult getTokenByIdAndDevice(UserIdAndDeviceDto userIdAndDeviceDto) {

        Long id = userIdAndDeviceDto.getUserId() ;
        String device = userIdAndDeviceDto.getDevice() ;

        String tokenValue = StpUtil.getTokenValueByLoginId(id, device) ;

        String result =
                MessageFormat.format(TOKEN_RETRIEVAL_WITH_ID_AND_DEVICE_MESSAGE,
                        id,
                        tokenValue) ;

        log.info(result) ;

        return SaResult.ok(result) ;
    }
}
