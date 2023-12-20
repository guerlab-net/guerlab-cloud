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

package net.guerlab.cloud.openapi.core;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;

import net.guerlab.cloud.openapi.core.properties.OpenApiProperties;

/**
 * 自定义头信息处理.
 *
 * @author guer
 */
public class CustomerHeaderGlobalOpenApiCustomizer implements GlobalOpenApiCustomizer {

	private final OpenApiProperties properties;

	public CustomerHeaderGlobalOpenApiCustomizer(OpenApiProperties properties) {
		this.properties = properties;
	}

	@Override
	public void customise(OpenAPI openApi) {
		Paths paths = openApi.getPaths();
		if (paths == null) {
			return;
		}

		List<String> names = properties.getComponents().getSecuritySchemes().values().stream()
				.filter(Objects::nonNull)
				.map(SecurityScheme::getName)
				.distinct().toList();

		if (names.isEmpty()) {
			return;
		}

		for (PathItem pathItem : paths.values()) {
			List<Operation> readOperations = pathItem.readOperations();
			for (Operation readOperation : readOperations) {
				for (String name : names) {
					readOperation.addSecurityItem(new SecurityRequirement().addList(name));
				}
			}
		}
	}
}
