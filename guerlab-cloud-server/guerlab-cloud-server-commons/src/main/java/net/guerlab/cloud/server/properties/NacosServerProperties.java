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

package net.guerlab.cloud.server.properties;

import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * NACOS服务配置.
 *
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = "nacos")
public class NacosServerProperties {

	/**
	 * NACOS主机名称.
	 */
	private String hostname = "nacos";

	/**
	 * NACOS端口.
	 */
	private Integer port = 8848;

	/**
	 * 命名空间.
	 */
	private String namespace = "";

	/**
	 * 用户名.
	 */
	private String username;

	/**
	 * 密码.
	 */
	private String password;

	/**
	 * 批量注册应用名列表.
	 */
	private List<String> appNames;
}
