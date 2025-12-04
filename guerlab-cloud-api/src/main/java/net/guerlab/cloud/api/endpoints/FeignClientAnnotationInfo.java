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

package net.guerlab.cloud.api.endpoints;

import lombok.Data;

/**
 * feign客户端注解信息.
 *
 * @author guer
 */
@Data
public class FeignClientAnnotationInfo {

	/**
	 * 带有可选协议前缀的服务id.
	 */
	private String name;

	/**
	 * 如果存在，它将用作bean名称而不是名称，但不会用作服务id.
	 */
	private String contextId;

	/**
	 * 绝对URL或可解析的主机名（协议是可选的）.
	 */
	private String url;

	/**
	 * 是否应该解码404而不是抛出FeignExceptions.
	 */
	private boolean dismiss404;

	/**
	 * 所有方法级映射使用的路径前缀.
	 */
	private String path;

	/**
	 * 是否将外部代理标记为主bean.
	 */
	private boolean primary;
}
