package net.guerlab.smart.platform.user.auth.annotation;

import java.lang.annotation.*;

/**
 * 需要双因子认证
 *
 * @author guer
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NeedTwoFactorAuthentication {}
