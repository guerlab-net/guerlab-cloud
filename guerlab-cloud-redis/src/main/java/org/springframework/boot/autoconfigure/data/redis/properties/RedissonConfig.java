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

package org.springframework.boot.autoconfigure.data.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.redisson.config.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Redisson配置.
 *
 * @author guer
 */
@Setter
@Getter
@RefreshScope
@ConfigurationProperties("spring.redis.redisson")
public class RedissonConfig extends Config {
}
