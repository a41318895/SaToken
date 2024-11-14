package com.akichou.satokentest.service.interfaces;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.LoginDto;

public interface LoginService {

    SaResult loginWithDefaultRememberMe(LoginDto loginDto) ;

    SaResult loginWithSaLoginModel(LoginDto loginDto);

    SaResult loginWithForget(LoginDto loginDto);

    SaResult logout();
}
