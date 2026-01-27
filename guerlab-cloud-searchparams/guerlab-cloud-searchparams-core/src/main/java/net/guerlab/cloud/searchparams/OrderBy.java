/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 排序字段.
 *
 * @author guer
 */
@Data
@Accessors(chain = true)
@Builder
@Schema(description = "排序字段")
public class OrderBy {

	private static final String COLUMN_NAME_REGEX = "^(?:[a-zA-Z0-9_]+|'[a-zA-Z0-9_]+')$";

	private static final String QUOTATION_MARKS = "'";

	private static final List<String> ALLOWS = new CopyOnWriteArrayList<>();

	/**
	 * 字段名.
	 */
	@Schema(description = "字段名")
	private String columnName;

	/**
	 * 是否升序.
	 */
	@Schema(description = "是否升序", defaultValue = "true")
	@Builder.Default
	private boolean asc = true;

	/**
	 * 创建排序字段.
	 */
	@SuppressWarnings("unused")
	public OrderBy() {
		this(null, true);
	}

	/**
	 * 排序字段.
	 *
	 * @param columnName 字段名
	 */
	public OrderBy(String columnName) {
		this(columnName, true);
	}

	/**
	 * 排序字段.
	 *
	 * @param columnName 字段名
	 * @param asc        是否升序
	 */
	public OrderBy(String columnName, boolean asc) {
		this.columnName = columnName;
		this.asc = asc;
	}

	/**
	 * 添加白名单字段.
	 *
	 * @param columnName 字段名称
	 */
	public static void addAllow(String columnName) {
		columnName = StringUtils.trimToNull(columnName);
		if (columnName == null || ALLOWS.contains(columnName)) {
			return;
		}
		ALLOWS.add(columnName);
	}

	/**
	 * 是否通过检查.
	 * @return 是否通过检查
	 */
	public boolean accept() {
		columnName = StringUtils.trimToNull(columnName);
		if (columnName == null) {
			return false;
		}

		if (ALLOWS.contains(columnName)) {
			return true;
		}

		if (columnName.contains(QUOTATION_MARKS)) {
			if (!columnName.startsWith(QUOTATION_MARKS) || !columnName.endsWith(QUOTATION_MARKS)) {
				return false;
			}
		}

		return columnName.matches(COLUMN_NAME_REGEX);
	}
}
