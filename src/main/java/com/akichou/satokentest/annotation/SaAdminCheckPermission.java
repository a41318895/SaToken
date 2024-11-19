package com.akichou.satokentest.annotation;

import cn.dev33.satoken.annotation.SaCheckPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SaCheckPermission()
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SaAdminCheckPermission {

    String value() ;
}
