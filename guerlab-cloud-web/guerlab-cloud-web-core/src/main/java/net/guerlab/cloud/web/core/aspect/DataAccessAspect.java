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

package net.guerlab.cloud.web.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import net.guerlab.cloud.web.core.annotation.DataAccess;
import net.guerlab.cloud.web.core.data.DataHandler;
import net.guerlab.cloud.web.core.data.NopeDataHandler;
import net.guerlab.cloud.web.core.utils.DataAccessUtils;

/**
 * 数据处理切面.
 *
 * @author guer
 */
@Slf4j
@Aspect
public class DataAccessAspect implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Around("@annotation(dataAccess)")
	public Object dataAccess(ProceedingJoinPoint point, DataAccess dataAccess) throws Throwable {
		Object result = point.proceed();
		String filedName = StringUtils.trimToNull(dataAccess.fieldName());
		if (ObjectUtils.isEmpty(result) || filedName == null) {
			return result;
		}
		DataHandler dataHandler = getDataHandler(dataAccess);
		if (dataHandler == null) {
			return result;
		}
		DataAccessUtils.objectHandler(filedName, result, dataHandler);
		return result;
	}

	@Nullable
	private DataHandler getDataHandler(DataAccess dataAccess) {
		DataHandler dataHandler = null;
		String handlerName = StringUtils.trimToNull(dataAccess.handlerName());
		if (handlerName != null) {
			try {
				dataHandler = applicationContext.getBean(handlerName, DataHandler.class);
			}
			catch (Exception e) {
				log.debug("get DataHandler fail:{}", e.getLocalizedMessage(), e);
			}
		}
		if (dataHandler == null) {
			Class<? extends DataHandler> dataHandlerClass = dataAccess.handlerClass();
			if (dataHandlerClass.isAssignableFrom(NopeDataHandler.class)) {
				log.debug("dataHandlerClass is NopeDataHandler");
				return null;
			}

			try {
				dataHandler = applicationContext.getBean(dataHandlerClass);
			}
			catch (Exception e) {
				log.debug("get DataHandler fail:{}", e.getLocalizedMessage(), e);
			}
		}
		return dataHandler;
	}
}
