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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合助手.
 *
 * @author guer
 */
final class CollectionHelper {

	/**
	 * 默认批次数量.
	 */
	private static final int DEFAULT_BATCH_SIZE = 1000;

	/**
	 * 批次数量.
	 */
	private static int BATCH_SIZE = DEFAULT_BATCH_SIZE;

	private CollectionHelper() {
	}

	/**
	 * 设置批次数量.
	 *
	 * @param batchSize 批次数量
	 */
	public static void setBatchSize(int batchSize) {
		if (batchSize > 0) {
			BATCH_SIZE = batchSize;
		}
	}

	/**
	 * 将元素列表按批次数量分割.
	 *
	 * @param list 元素列表
	 * @param <T>  元素对象类型
	 * @return 分割后元素列表
	 */
	public static <T> List<List<T>> split(List<T> list) {
		final List<List<T>> result = new ArrayList<>();
		if (list.isEmpty()) {
			return result;
		}

		int length = list.size();
		if (length <= BATCH_SIZE) {
			result.add(list);
			return result;
		}

		for (int i = 0; i < length; i += BATCH_SIZE) {
			result.add(list.subList(i, Math.min(length, i + BATCH_SIZE)));
		}

		return result;
	}
}
