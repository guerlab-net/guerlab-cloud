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

package net.guerlab.cloud.auth.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字符串类型 token 工厂配置.
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StringValueTokenFactoryProperties extends EncryptionSupportTokenFactoryProperties<String> { }
