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

package net.guerlab.cloud.web.core.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guerlab.cloud.auth.annotation.IgnoreLogin;
import net.guerlab.cloud.commons.entity.EnumDescription;
import net.guerlab.cloud.commons.util.EnumDescriptionUtils;

/**
 * 枚举说明.
 *
 * @author guer
 */
@Tag(name = "枚举说明")
@RestController
@RequestMapping("/enumDescription")
public class EnumDescriptionController {

	/**
	 * 根据类路径获取枚举说明列表.
	 *
	 * @param path 类路径
	 * @return 枚举说明列表
	 */
	@IgnoreLogin
	@Operation(summary = "根据类路径获取枚举说明列表")
	@GetMapping("/{path}")
	public List<EnumDescription> get(
			@Parameter(description = "路径", required = true) @PathVariable("path") String path) {
		return EnumDescriptionUtils.getDescriptions(path);
	}
}
