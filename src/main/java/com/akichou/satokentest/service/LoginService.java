package com.akichou.satokentest.service;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.entity.dto.UserIdAndDeviceDto;

public interface LoginService {

    SaResult loginWithDefaultRememberMe(LoginDto loginDto) ;

    SaResult loginWithSaLoginModel(LoginDto loginDto);

    SaResult loginWithForget(LoginDto loginDto);

    SaResult logout();

    SaResult loginWithDevicePc(LoginDto loginDto);

    SaResult logoutWithIdAndDevice(UserIdAndDeviceDto userIdAndDeviceDto);
}
