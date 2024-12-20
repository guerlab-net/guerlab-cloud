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

package net.guerlab.cloud.web.core.properties;

import java.util.Locale;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * i18n配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties("spring.i18n")
public class I18nProperties {

	/**
	 * 默认语言.
	 */
	private Locale defaultLocale = Locale.getDefault();

}
