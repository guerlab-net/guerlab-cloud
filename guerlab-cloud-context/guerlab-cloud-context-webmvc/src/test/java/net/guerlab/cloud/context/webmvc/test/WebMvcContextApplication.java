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

package net.guerlab.cloud.context.webmvc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;

/**
 * webmvc上下文测试应用.
 *
 * @author guer
 */
@RestController
@RequestMapping
@SpringBootApplication
public class WebMvcContextApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMvcContextApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		ContextAttributes contextAttributes = ContextAttributesHolder.get();
		return contextAttributes.getInitSource();
	}
}
