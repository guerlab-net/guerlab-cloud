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

package net.guerlab.cloud.web.core.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;

import net.guerlab.cloud.web.core.annotation.ResponseObjectWrapper;

/**
 * http响应数据处理配置参数.<br>
 * 符合以下条件之一将不会被包装成{@link net.guerlab.cloud.core.result.Result}类型<br>
 * <ul>
 * <li>请求路径符合排除列表前缀</li>
 * <li>使用了{@link ResponseObjectWrapper}注解且ignore为true</li>
 * </ul>
 *
 * @author guer
 */
@Getter
@SuppressWarnings("unused")
@RefreshScope
@ConfigurationProperties("spring.response-advisor")
public class ResponseAdvisorProperties {

	/**
	 * 排除路径,支持ant风格路径和类路径.<br>
	 * <p>
	 * 范例：
	 * <ul>
	 *     <li>/a/c/d</li>
	 *     <li>/&#8270;&#8270;/example/&#8270;</li>
	 *     <li>com.example.controller#getData</li>
	 * </ul>
	 */
	private List<String> excluded = new ArrayList<>();

	/**
	 * 设置排除路径.
	 *
	 * @param excluded 排除路径
	 */
	public void setExcluded(@Nullable List<String> excluded) {
		if (excluded != null) {
			this.excluded = excluded;
		}
	}

	/**
	 * 添加排除路径.
	 *
	 * @param excluded 排除路径
	 */
	public void addExcluded(List<String> excluded) {
		this.excluded.addAll(excluded);
	}

	/**
	 * 添加排除路径.
	 *
	 * @param excluded 排除路径
	 */
	public void addExcluded(String... excluded) {
		this.excluded.addAll(Arrays.asList(excluded));
	}
}
