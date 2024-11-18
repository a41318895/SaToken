package com.akichou.satokentest.util;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * Manage StpLogic System in Sa-Token
 */
public class StpKit {

    // Default Origin Session Object
    public static final StpLogic DEFAULT = StpUtil.stpLogic ;

    // User Session Object
    public static final StpLogic USER = new StpLogic("user") ;

    // Admin Session Object
    public static final StpLogic ADMIN = new StpLogic("admin") ;
}
