package com.akichou.satokentest.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpCodeEnum {

    USER_NOT_FOUND(601, "用戶不存在"),
    USER_NOT_LOGIN(602, "用戶處於未登入狀態"),
    NO_SUCH_ALGORITHM(603, "無支持此加密演算法"),
    RSA_KEY_SHOULD_EXIST(604, "使用RSA解密時, 必須帶有key"),
    ADMIN_NOT_FOUND(605, "管理員不存在"),
    USERNAME_PASSWORD_REQUIRED(606, "用戶帳號密碼為必須") ;

    private final int code ;
    private final String message;
}
