package com.akichou.satokentest.service;

import cn.dev33.satoken.util.SaResult;

public interface AdminService {

    SaResult disableAccount(Long userId);

    SaResult kickThenDisableAccount(Long userId);

    SaResult untieAccountFromDisable(Long userId);

    SaResult getDisableInfo(Long userId);
}
