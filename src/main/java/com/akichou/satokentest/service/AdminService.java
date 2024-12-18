package com.akichou.satokentest.service;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.LoginDto;
import com.akichou.satokentest.entity.dto.UserDto;
import com.akichou.satokentest.entity.dto.UserUpdateDto;

public interface AdminService {

    SaResult disableAccount(Long userId);

    SaResult kickThenDisableAccount(Long userId);

    SaResult untieAccountFromDisable(Long userId);

    SaResult getDisableInfo(Long userId);

    SaResult loginAdmin(LoginDto loginDto);

    SaResult isLogin(Long adminId);

    SaResult getTokenInfo(Long adminId);

    SaResult logout();

    SaResult createUser(UserDto userDto);

    SaResult updateUser(UserUpdateDto userUpdateDto);

    SaResult deleteUser(Long userId);
}
