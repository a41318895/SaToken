package com.akichou.satokentest.global;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public SaResult handleException(Exception e) {

        log.error(e.getMessage()) ;

        return SaResult.error(e.getMessage()) ;
    }
}
