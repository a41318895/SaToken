package com.akichou.satokentest.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpCodeEnum {

    USER_NOT_FOUND(601, "用戶不存在"),
    USER_NOT_LOGIN(602, "用戶處於未登入狀態") ;

    private final int code ;
    private final String message;
}
