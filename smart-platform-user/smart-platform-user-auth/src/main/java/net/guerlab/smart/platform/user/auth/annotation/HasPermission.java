package net.guerlab.smart.platform.user.auth.annotation;

import java.lang.annotation.*;

/**
 * 权限注解-权限
 *
 * @author guer
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HasPermission {

    /**
     * 权限关键字列表
     *
     * @return 权限关键字列表
     */
    String[] value();
}
