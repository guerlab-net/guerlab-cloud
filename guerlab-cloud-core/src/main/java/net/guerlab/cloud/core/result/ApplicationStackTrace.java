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

package net.guerlab.cloud.core.result;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * 应用堆栈跟踪.
 *
 * @author guer
 */
@Data
public class ApplicationStackTrace implements Serializable {

	/**
	 * 应用名称.
	 */
	private String applicationName;

	/**
	 * 堆栈跟踪.
	 */
	@Nullable
	private List<String> stackTrace;
}
