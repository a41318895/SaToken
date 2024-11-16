package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static com.akichou.satokentest.constant.Constant.*;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

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
}
