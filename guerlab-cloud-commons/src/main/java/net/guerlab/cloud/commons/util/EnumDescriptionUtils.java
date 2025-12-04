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

package net.guerlab.cloud.commons.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.commons.entity.EnumDescription;
import net.guerlab.cloud.commons.enums.EnumDescriptionSupport;

/**
 * 枚举说明工具类.
 *
 * @author guer
 */
public final class EnumDescriptionUtils {

	private EnumDescriptionUtils() {

	}

	/**
	 * 根据类路径获取枚举说明列表.
	 *
	 * @param path 类路径
	 * @return 枚举说明列表
	 */
	public static List<EnumDescription> getDescriptions(String path) {
		path = StringUtils.trimToNull(path);
		if (path == null) {
			return Collections.emptyList();
		}

		Class<?> clazz;
		try {
			clazz = Class.forName(path);
		}
		catch (Exception ignored) {
			return Collections.emptyList();
		}

		if (!clazz.isEnum() || !EnumDescriptionSupport.class.isAssignableFrom(clazz)) {
			return Collections.emptyList();
		}

		EnumDescriptionSupport[] enums = (EnumDescriptionSupport[]) clazz.getEnumConstants();
		if (enums == null) {
			return Collections.emptyList();
		}

		return Arrays.stream(enums).map(EnumDescriptionUtils::buildDescription).toList();
	}

	private static EnumDescription buildDescription(EnumDescriptionSupport item) {
		return new EnumDescription(((Enum<?>) item).name(), item.getDescription());
	}
}
