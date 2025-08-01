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

package net.guerlab.cloud.api.feign;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class TestAutoConfigure {

	@Bean
	public IntegerHttpMessageConverter integerHttpMessageConverter() {
		return new IntegerHttpMessageConverter();
	}

	@Bean
	public PdfHttpMessageConverter pdfHttpMessageConverter() {
		return new PdfHttpMessageConverter();
	}
}
