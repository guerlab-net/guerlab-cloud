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

package net.guerlab.cloud.server.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 动态数据源bean构造处理过程.
 *
 * @author guer
 */
@Slf4j
public class MultiDataSourceBeanPostProcessor implements BeanPostProcessor {

	private final List<String> basePackages;

	private final List<Advisor> advisors;

	@Getter
	private final List<String> beanClassNames = new ArrayList<>();

	public MultiDataSourceBeanPostProcessor(List<String> basePackages, List<Advisor> advisors) {
		this.basePackages = basePackages;
		this.advisors = advisors;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		String beanClassName;
		try {
			beanClassName = getRealClassName(bean);
		}
		catch (Exception e) {
			throw new ApplicationException("cannot get bean className, bean is %s".formatted(bean), e);
		}

		if (basePackages.stream().anyMatch(beanClassName::startsWith)) {
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setTarget(bean);
			advisors.forEach(proxyFactory::addAdvisor);

			beanClassNames.add(beanClassName);

			return proxyFactory.getProxy();
		}

		return bean;
	}

	private String getRealClassName(Object bean) throws Exception {
		Object target = bean;
		if (bean instanceof Proxy proxy) {
			target = getTarget(proxy);
		}

		if (bean instanceof MapperFactoryBean<?> mapper) {
			return mapper.getMapperInterface().getName();
		}
		else if (bean instanceof ProxyFactory proxy) {
			target = getTarget(proxy);
		}

		return target.getClass().getName();
	}

	private Object getTarget(Proxy proxy) {
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
		if (invocationHandler instanceof Proxy p) {
			return getTarget(p);
		}
		return invocationHandler;
	}

	private Object getTarget(ProxyFactory proxyFactory) throws Exception {
		TargetSource targetSource = proxyFactory.getTargetSource();
		if (targetSource.getTargetClass() == null) {
			return proxyFactory;
		}
		Object target = proxyFactory.getTargetSource().getTarget();
		if (target == null) {
			return proxyFactory;
		}
		if (target instanceof ProxyFactory proxy) {
			return getTarget(proxy);
		}
		return target;
	}

}
