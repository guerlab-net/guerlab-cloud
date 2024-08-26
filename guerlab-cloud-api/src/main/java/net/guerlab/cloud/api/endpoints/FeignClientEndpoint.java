/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.api.endpoints;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import feign.Target;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.cloud.openfeign.FeignClient;

import net.guerlab.cloud.core.util.SpringUtils;

/**
 * FeignClient实例监控端点.
 *
 * @author guer
 */
@Slf4j
@Endpoint(id = "feign-client")
public class FeignClientEndpoint {

	/**
	 * 构建feign客户端实例信息.
	 *
	 * @param beanName bean名称
	 * @param obj      代理对象
	 * @return feign客户端实例信息
	 */
	@Nullable
	private static FeignClientInfo buildFeignClientInfo(String beanName, Object obj) {
		Target.HardCodedTarget<?> target = getProxyTarget(obj);
		if (target == null) {
			return null;
		}

		FeignClient feignClientAnnotation = target.type().getAnnotation(FeignClient.class);
		if (feignClientAnnotation == null) {
			return null;
		}

		FeignClientAnnotationInfo annotationInfo = new FeignClientAnnotationInfo();
		annotationInfo.setName(feignClientAnnotation.name());
		annotationInfo.setContextId(feignClientAnnotation.contextId());
		annotationInfo.setUrl(feignClientAnnotation.url());
		annotationInfo.setDismiss404(feignClientAnnotation.dismiss404());
		annotationInfo.setPath(feignClientAnnotation.path());
		annotationInfo.setPrimary(feignClientAnnotation.primary());

		FeignClientInfo info = new FeignClientInfo();
		info.setBeanName(beanName);
		info.setClassPath(target.type().getName());
		info.setUrl(target.url());
		info.setAnnotation(annotationInfo);
		return info;
	}

	/**
	 * 获取代理对象.
	 *
	 * @param obj 对象
	 * @return 获取代理对象
	 */
	@Nullable
	private static Target.HardCodedTarget<?> getProxyTarget(Object obj) {
		if (!(obj instanceof Proxy)) {
			return null;
		}

		InvocationHandler invocationHandler = Proxy.getInvocationHandler(obj);
		Class<? extends InvocationHandler> invocationHandlerClass = invocationHandler.getClass();

		try {
			Field field = invocationHandlerClass.getDeclaredField("target");
			field.setAccessible(true);
			Object target = field.get(invocationHandler);

			if (target instanceof Target.HardCodedTarget<?>) {
				return (Target.HardCodedTarget<?>) target;
			}

			return null;
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			return null;
		}
	}

	/**
	 * 获取feign客户端实例信息列表.
	 *
	 * @return feign客户端实例信息列表
	 */
	@SuppressWarnings("unused")
	@ReadOperation
	public Collection<FeignClientInfo> getFeignInstances() {
		Map<String, Object> beanMap = SpringUtils.getBeanMapWithAnnotation(FeignClient.class);

		if (beanMap.isEmpty()) {
			return Collections.emptyList();
		}

		return beanMap.entrySet().stream().map(entry -> buildFeignClientInfo(entry.getKey(), entry.getValue()))
				.filter(Objects::nonNull).toList();
	}

	/**
	 * 根据名称获取feign客户端实例信息.
	 *
	 * @param beanName bean名称
	 * @return feign客户端实例信息
	 */
	@SuppressWarnings("unused")
	@Nullable
	@ReadOperation
	public FeignClientInfo getFeignInstance(@Selector String beanName) {
		Map<String, Object> beanMap = SpringUtils.getBeanMapWithAnnotation(FeignClient.class);
		Object obj = beanMap.get(beanName);

		if (obj == null) {
			return null;
		}

		return buildFeignClientInfo(beanName, obj);
	}
}
