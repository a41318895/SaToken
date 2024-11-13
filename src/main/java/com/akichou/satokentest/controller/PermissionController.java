package com.akichou.satokentest.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.enumeration.CheckPermType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perm")
@Slf4j
public class PermissionController {

    @GetMapping
    public SaResult getPermissionsAndRoles() {

        List<String> permissionList = StpUtil.getPermissionList() ;
        log.info("All Permissions of this username: {}", permissionList) ;

        List<String> roleList = StpUtil.getRoleList() ;
        log.info("All Roles of this username: {}", roleList) ;

        return SaResult.ok()
                .set("Permissions", permissionList)
                .set("Roles", roleList) ;
    }

    @GetMapping("/permission/possession")
    public SaResult hasPermission(@RequestBody List<String> permissionList,
                                  @RequestParam("checkPermType") CheckPermType checkPermType) {

        // Make List to Array
        String[] permissionListArray = permissionList.toArray(new String[0]) ;

        boolean hasPermission ;
        try {
            switch (checkPermType) {

                case SINGLE:
                    hasPermission = StpUtil.hasPermission(permissionList.get(0));
                    break;
                case AND:
                    hasPermission = StpUtil.hasPermissionAnd(permissionListArray);
                    break;
                case OR:
                    hasPermission = StpUtil.hasPermissionOr(permissionListArray);
                    break;
                default:
                    return SaResult.error("Please key in the correct CheckPermType");
            }
        } finally {
            log.info("The permission possession of (ID={}) was checked", StpUtil.getLoginId()) ;
        }

        return SaResult.ok("Has permission ?: " + hasPermission) ;
    }

    @GetMapping("/role/confirmation")
    public SaResult checkRole(@RequestBody List<String> roleList,
                              @RequestParam("checkPermType") CheckPermType checkPermType) {

        String[] roleListArray = roleList.toArray(new String[0]) ;

        try {
            switch (checkPermType) {

                case SINGLE:
                    StpUtil.checkRole(roleList.get(0)) ;
                    break;
                case AND:
                    StpUtil.checkRoleAnd(roleListArray) ;
                    break;
                case OR:
                    StpUtil.checkRoleOr(roleListArray) ;
                    break;
                default:
                    return SaResult.error("Please key in the correct CheckPermType") ;
            }
        } finally {
            log.info("The role possession of (ID={}) was confirmed", StpUtil.getLoginId()) ;
        }

        return SaResult.ok("You actually have the role(s)") ;
    }
}
