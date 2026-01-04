/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.lock.idempotent.aspect;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;

import net.guerlab.cloud.commons.exception.IdempotentBlockException;
import net.guerlab.cloud.lock.core.aspect.AbstractLockAspect;
import net.guerlab.cloud.lock.idempotent.annotation.Idempotent;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 幂等处理切面.
 *
 * @author guer
 */
@Slf4j
@Aspect
public class IdempotentAspect extends AbstractLockAspect {

	private final StringRedisTemplate redisTemplate;

	/**
	 * 创建幂等处理切面.
	 *
	 * @param redisTemplate redisTemplate
	 * @param messageSource messageSource
	 */
	public IdempotentAspect(StringRedisTemplate redisTemplate, MessageSource messageSource) {
		super(messageSource);
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 幂等处理.
	 *
	 * @param point      切入点
	 * @param idempotent 幂等注解
	 * @return 方法返回信息
	 * @throws Throwable 当方法内部抛出异常时候抛出Throwable
	 */
	@Around("@annotation(idempotent)")
	public Object handler(ProceedingJoinPoint point, Idempotent idempotent) throws Throwable {
		Signature signature = point.getSignature();
		if (!(signature instanceof MethodSignature methodSignature)) {
			return point.proceed();
		}

		Object[] args = point.getArgs();

		String lockKey = buildLockKey(methodSignature, args, idempotent.lockKey());
		log.debug("lockKey[lockKey={}]", lockKey);
		Boolean lockResult = redisTemplate.opsForValue()
				.setIfAbsent(lockKey, Thread.currentThread().getName(),
						idempotent.lockTime(), idempotent.lockTimeUnit());
		boolean lockSuccess = Objects.equals(true, lockResult);

		if (!lockSuccess) {
			Object fallbackObject = getFallback(idempotent.fallBackFactory(), args);
			if (fallbackObject != null) {
				return fallbackObject;
			}

			throw buildException(idempotent, args);
		}

		log.debug("lock success[lockKey={}, lockTime={}, lockTimeUnit={}]", lockKey, idempotent.lockTime(),
				idempotent.lockTimeUnit());
		try {
			return point.proceed();
		}
		finally {
			if (idempotent.unlockWhenEndOfOperation()) {
				log.debug("operation end, unlock[lockKey={}]", lockKey);
				redisTemplate.delete(lockKey);
			}
			else {
				log.debug("operation end, is held by current thread[lockKey={}]", lockKey);
			}
		}
	}

	private ApplicationException buildException(Idempotent idempotent, Object[] args) {
		ApplicationException exception = annotationMessage(idempotent.messageKey(), args);
		if (exception != null) {
			return exception;
		}

		return new IdempotentBlockException(idempotent.lockTime(), getTimeUnitName(idempotent.lockTimeUnit()));
	}
}
