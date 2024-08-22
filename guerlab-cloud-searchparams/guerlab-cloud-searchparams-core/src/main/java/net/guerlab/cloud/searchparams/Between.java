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

package net.guerlab.cloud.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * 范围.
 *
 * @param <E> 参数类型
 * @author guer
 */
@Data
@Schema(description = "范围")
public class Between<E> {

	/**
	 * 开始范围.
	 */
	@Schema(description = "开始范围")
	private E start;

	/**
	 * 结束范围.
	 */
	@Schema(description = "结束范围")
	private E end;

	/**
	 * 判断该范围是否无效.
	 *
	 * @return 是否无效
	 */
	public boolean isInvalid() {
		return start == null || end == null;
	}

	/**
	 * 基于开始值和结束值快速构造范围对象
	 *
	 * @param start 开始值
	 * @param end   结束值
	 * @param <E>   值类型
	 * @return 范围对象
	 */
	public static <E> Between<E> of(@Nullable E start, @Nullable E end) {
		Between<E> between = new Between<>();
		between.start = start;
		between.end = end;
		return between;
	}
}
