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

package net.guerlab.cloud.web.core.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.web.core.aspect.DataAccessAspect;

/**
 * 数据处理切面自动配置.
 *
 * @author guer
 */
@Configuration(proxyBeanMethods = false)
public class DataAccessAspectAutoConfigure {

	/**
	 * 创建数据处理切面.
	 *
	 * @return 数据处理切面
	 */
	@Bean
	public DataAccessAspect dataAccessAspect() {
		return new DataAccessAspect();
	}
}
