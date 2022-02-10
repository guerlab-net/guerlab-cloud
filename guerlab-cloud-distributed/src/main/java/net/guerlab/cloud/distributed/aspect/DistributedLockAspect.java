/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.distributed.aspect;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.exception.DistributedLockBlockException;
import net.guerlab.cloud.distributed.annotation.DistributedLock;
import net.guerlab.commons.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.MessageSource;

/**
 * 分布式锁处理切面
 *
 * @author guer
 */
@Slf4j
@Aspect
public class DistributedLockAspect extends AbstractDistributedLockAspect {

    private final RedissonClient redissonClient;

    /**
     * 创建分布式锁处理切面
     *
     * @param redissonClient
     *         redisTemplate
     * @param messageSource
     *         messageSource
     */
    public DistributedLockAspect(RedissonClient redissonClient, MessageSource messageSource) {
        super(messageSource);
        this.redissonClient = redissonClient;
    }

    /**
     * 分布式锁处理
     *
     * @param point
     *         切入点
     * @param distributedLock
     *         分布式锁注解
     * @return 方法返回信息
     * @throws Throwable
     *         当方法内部抛出异常时候抛出Throwable
     */
    @Around("@annotation(distributedLock)")
    public Object handler(ProceedingJoinPoint point, DistributedLock distributedLock) throws Throwable {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature methodSignature)) {
            return point.proceed();
        }

        Object[] args = point.getArgs();

        String lockKey = buildLockKey(methodSignature, args, distributedLock.lockKey());
        log.debug("lockKey[lockKey={}]", lockKey);
        RLock lock = redissonClient.getFairLock(lockKey);

        if (!lock.tryLock(distributedLock.waitTime(), distributedLock.lockTime(), distributedLock.lockTimeUnit())) {
            Object fallbackObject = getFallback(distributedLock.fallBackFactory(), args);
            if (fallbackObject != null) {
                return fallbackObject;
            }

            throw buildException(distributedLock, args);
        }

        log.debug("lock success[lockKey={}, waitTime={}, lockTime={}, lockTimeUnit={}]", lockKey,
                distributedLock.waitTime(), distributedLock.lockTime(), distributedLock.lockTimeUnit());
        try {
            return point.proceed();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                log.debug("operation end, unlock[lockKey={}]", lockKey);
                lock.unlock();
            } else {
                log.debug("operation end, is held by current thread[lockKey={}]", lockKey);
            }
        }
    }

    private ApplicationException buildException(DistributedLock distributedLock, Object[] args) {
        ApplicationException exception = annotationMessage(distributedLock.messageKey(), args);
        if (exception != null) {
            return exception;
        }

        return new DistributedLockBlockException(distributedLock.lockTime(),
                getTimeUnitName(distributedLock.lockTimeUnit()));
    }
}
