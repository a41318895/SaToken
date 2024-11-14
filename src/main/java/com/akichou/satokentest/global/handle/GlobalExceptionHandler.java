package com.akichou.satokentest.global.handle;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.global.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public SaResult handleException(UserNotFoundException e) {

        log.error(e.getMessage()) ;

        return SaResult.error(e.getMessage()) ;
    }

    @ExceptionHandler
    public SaResult handleException(Exception e) {

        log.error(e.getMessage()) ;

        return SaResult.error(e.getMessage()) ;
    }
}
