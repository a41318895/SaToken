package com.akichou.satokentest.component;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Override the permission & role getting logic by ArrayList-Mocking
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<String> permissionList = new ArrayList<>() ;

        permissionList.add("user.create") ;
        permissionList.add("user.update") ;
        permissionList.add("user.delete") ;

        return permissionList ;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        List<String> roleList = new ArrayList<>() ;

        roleList.add("admin") ;
        roleList.add("user") ;

        return roleList ;
    }
}
