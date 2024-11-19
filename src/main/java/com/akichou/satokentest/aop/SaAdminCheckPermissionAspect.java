package com.akichou.satokentest.aop;

import com.akichou.satokentest.annotation.SaAdminCheckPermission;
import com.akichou.satokentest.util.StpKit;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SaAdminCheckPermissionAspect {

    @Before("@annotation(saAdminCheckPermission)")
    public void checkPermission(SaAdminCheckPermission saAdminCheckPermission) {

        String permission = saAdminCheckPermission.value() ;

        StpKit.ADMIN.checkPermission(permission) ;
    }
}
