package com.akichou.satokentest.global.exception;

import com.akichou.satokentest.enumeration.HttpCodeEnum;

public class SystemException extends RuntimeException {

    public SystemException(HttpCodeEnum httpCodeEnum) {

        super(httpCodeEnum.getMessage()) ;
    }
}
