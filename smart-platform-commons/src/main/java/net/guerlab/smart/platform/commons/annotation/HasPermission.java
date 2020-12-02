package net.guerlab.smart.platform.commons.annotation;

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
     * 权限检查el表达式
     *
     * @return 权限检查el表达式
     */
    String value();
}
