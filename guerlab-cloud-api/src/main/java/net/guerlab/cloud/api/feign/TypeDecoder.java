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

import feign.codec.Decoder;

import org.springframework.http.MediaType;

/**
 * 指定类型解析器.
 *
 * @author guer
 */
public interface TypeDecoder extends Decoder {

	/**
	 * 判断当前类型是否支持
	 *
	 * @param mediaType 媒体类型
	 * @return 是否支持
	 */
	boolean isSupport(MediaType mediaType);
}
