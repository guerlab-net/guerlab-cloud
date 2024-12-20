/*
 * Copyright 2018-2025 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.lock.idempotent.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import net.guerlab.cloud.lock.idempotent.fallback.IdempotentFallbackFactory;
import net.guerlab.cloud.lock.idempotent.fallback.NoopIdempotentFallbackFactory;

/**
 * 幂等.
 *
 * @author guer
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Idempotent {

	/**
	 * 锁的Key.
	 *
	 * @return 锁的Key
	 */
	String lockKey() default "";

	/**
	 * 加锁时长.
	 *
	 * @return 加锁时长
	 */
	long lockTime() default 1L;

	/**
	 * 加锁时长单位.
	 *
	 * @return 加锁时长单位
	 */
	TimeUnit lockTimeUnit() default TimeUnit.SECONDS;

	/**
	 * 自定义异常消息KEY.
	 *
	 * @return 自定义异常消息KEY
	 */
	String messageKey() default "";

	/**
	 * 操作结束后是否释放锁.
	 *
	 * @return 操作结束后是否释放锁
	 */
	boolean unlockWhenEndOfOperation() default false;

	/**
	 * 快速失败工厂类型.
	 *
	 * @return 快速失败工厂类型
	 */
	Class<? extends IdempotentFallbackFactory> fallBackFactory() default NoopIdempotentFallbackFactory.class;
}
