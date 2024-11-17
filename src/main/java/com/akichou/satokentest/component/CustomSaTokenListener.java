package com.akichou.satokentest.component;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.util.SaFoxUtil;
import org.springframework.stereotype.Component;

import static cn.dev33.satoken.SaManager.log;

@Component
public class CustomSaTokenListener implements SaTokenListener {

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {

        String loginDevice = loginModel.getDevice() == null ? "Unknown" : loginModel.getDevice() ;

        log.info("帳號ID: {} 登入成功 [ loginType = {} ], 會話憑證: [ Session Token = {} ], 登入設備端: [ {} ]",
                loginId, loginType, tokenValue, loginDevice) ;
    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {

        log.info("帳號ID: {} 登出成功 [ loginType = {} ], 會話憑證: [ Session Token = {} ]",
                loginId, loginType, tokenValue) ;
    }

    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {

        log.info("帳號ID: {} 被強制踢出登入狀態 [ loginType = {} ], 會話憑證: [ Session Token = {} ]",
                loginId, loginType, tokenValue) ;
    }

    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

        log.info("帳號ID: {} 被從其他設備登入 [ loginType = {} ], 會話憑證: [ Session Token = {} ]",
                loginId, loginType, tokenValue) ;
    }

    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

        log.info("帳號ID: {} -- [ {} 相關服務使用權 ] 被禁用... 當前登入狀態: [ loginType={} ], 禁用級數: [ Level = {} ], 解禁時間點為 {}",
                loginId, loginType, service, level, SaFoxUtil.formatAfterDate(disableTime * 1000)) ;
    }

    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

        log.info("帳號ID: {} -- [ {} 相關服務使用權 ] 解禁成功... 當前登入狀態: [ loginType = {} ]",
                loginId, service, loginType) ;
    }

    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

        log.info("Token 二級驗證成功, 服務標示: [ {} ], 持續有效時間: [ {} 秒 ], 目標令牌值: [ Token Value = {} ]",
                service, safeTime, tokenValue) ;
    }

    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

        log.info("Token 二級驗證關閉, 服務標示: [ {} ], 目標令牌值: [ Token Value = {} ]", service, tokenValue) ;
    }

    @Override
    public void doCreateSession(String id) {

        log.info("會話 SaSession [ SessionID = {} ] 創建成功", id) ;
    }

    @Override
    public void doLogoutSession(String id) {

        log.info("會話 SaSession [ SessionID = {} ] 移除成功", id) ;
    }

    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

        log.info("Token 續期成功, 尚餘 {} 秒到期, 帳號ID: {}, 目標令牌值: [ Token Value = {} ]",
                timeout, loginId, tokenValue) ;
    }
}

