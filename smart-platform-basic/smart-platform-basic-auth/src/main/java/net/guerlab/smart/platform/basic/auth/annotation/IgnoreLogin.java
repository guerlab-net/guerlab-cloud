package net.guerlab.smart.platform.basic.auth.annotation;

import java.lang.annotation.*;

/**
 * 忽略登录
 *
 * @author guer
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreLogin {

}
