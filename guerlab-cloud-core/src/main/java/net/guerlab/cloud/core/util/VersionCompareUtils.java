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

package net.guerlab.cloud.core.util;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.core.domain.Version;

/**
 * 版本比较工具类.<br>
 * 对于非数值类型的版本号将忽略匹配直接通过
 * <table class="striped">
 * <caption><b>版本号匹配结果</b></caption>
 * <thead>
 * <tr>
 *     <th scope="col" rowspan="2">输入值</th>
 *     <th scope="col"colspan=8>匹配结果</th>
 * <tr style="vertical-align:top">
 *     <th>1.0.0</th>
 *     <th>1.0.1</th>
 *     <th>[1.0.0, 1.2.1]</th>
 *     <th>(1.0.0, 1.2.1)</th>
 *     <th>[1.0.0, 1.2.1)</th>
 *     <th>(1.0.0, 1.2.1]</th>
 *     <th>string</th>
 *     <th>[str, str]</th>
 * </thead>
 * <tbody style="text-align:right">
 * <tr><th scope="row">1.0</th>     <td>True</td>   <td>False</td>  <td>True</td>   <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.0.1</th>   <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.1</th>     <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.1.1</th>   <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.2</th>     <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.2.1</th>   <td>False</td>  <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">str</th>     <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>   <td>False</td>
 * </tbody>
 * </table>
 *
 * @author guer
 */
@Slf4j
public final class VersionCompareUtils {

	/**
	 * 分组间隔符.
	 */
	private static final String GROUP = ",";

	/**
	 * 左包含.
	 */
	private static final String LEFT_CONTAIN = "[";

	/**
	 * 右包含.
	 */
	private static final String RIGHT_CONTAIN = "]";

	/**
	 * 左不包含.
	 */
	private static final String LEFT_NOT_CONTAIN = "(";

	/**
	 * 右不包含.
	 */
	private static final String RIGHT_NOT_CONTAIN = ")";

	/**
	 * 分组尺寸.
	 */
	private static final int GROUP_SIZE = 2;

	private VersionCompareUtils() {
	}

	/**
	 * 匹配检查.
	 *
	 * @param origin 源版本，支持单版本号格式
	 * @param range  检查范围，支持单版本号格式和范围版本号格式
	 * @return 是否匹配
	 */
	public static boolean match(String origin, String range) {
		origin = StringUtils.trimToNull(origin);
		range = StringUtils.trimToNull(range);

		if (origin == null || range == null) {
			return false;
		}

		boolean hasLeftFlag = range.startsWith(LEFT_CONTAIN) || range.startsWith(LEFT_NOT_CONTAIN);
		boolean hasRightFlag = range.endsWith(RIGHT_CONTAIN) || range.endsWith(RIGHT_NOT_CONTAIN);
		boolean hasGroupFlag = range.contains(GROUP);

		boolean noRangeMath = !hasLeftFlag && !hasRightFlag && !hasGroupFlag;

		try {
			if (noRangeMath) {
				return equalsMatch(origin, range);
			}
			else if (hasLeftFlag && hasRightFlag && hasGroupFlag) {
				return rangeMatch(origin, range);
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			return false;
		}
	}

	/**
	 * 相等匹配.
	 *
	 * @param origin 请求源版本
	 * @param range  范围版本
	 * @return 是否匹配
	 */
	private static boolean equalsMatch(String origin, String range) {
		Version originValue = Version.parse(origin);
		Version rangeValue = Version.parse(range);

		if (originValue == null || rangeValue == null) {
			return false;
		}

		while (!Objects.equals(originValue, rangeValue)) {
			if (!Objects.equals(originValue.value(), rangeValue.value())) {
				return false;
			}

			originValue = originValue.safeChildren();
			rangeValue = rangeValue.safeChildren();
		}

		return true;
	}

	/**
	 * 范围匹配.
	 *
	 * @param origin 请求源版本
	 * @param range  范围版本
	 * @return 是否匹配
	 */
	private static boolean rangeMatch(String origin, String range) {
		boolean leftContain = range.startsWith(LEFT_CONTAIN);
		boolean rightContain = range.endsWith(RIGHT_CONTAIN);

		range = range.replace(LEFT_CONTAIN, "");
		range = range.replace(LEFT_NOT_CONTAIN, "");
		range = range.replace(RIGHT_CONTAIN, "");
		range = range.replace(RIGHT_NOT_CONTAIN, "");

		String[] rangeGroup = range.split(GROUP);
		if (rangeGroup.length != GROUP_SIZE) {
			return false;
		}

		boolean leftRangeMatchResult = rangeMatch(origin, StringUtils.trimToEmpty(rangeGroup[0]), leftContain, true);
		boolean rightRangeMatchResult = rangeMatch(origin, StringUtils.trimToEmpty(rangeGroup[1]), rightContain, false);

		return leftRangeMatchResult && rightRangeMatchResult;
	}

	/**
	 * 范围匹配.
	 *
	 * @param origin   请求源版本
	 * @param range    范围版本
	 * @param contain  是否包含
	 * @param leftMath 左匹配模式
	 * @return 是否匹配
	 */
	private static boolean rangeMatch(String origin, String range, boolean contain, boolean leftMath) {
		Version originValue = Version.parse(origin);
		Version rangeValue = Version.parse(range);

		if (originValue == null || rangeValue == null) {
			return false;
		}

		while (!Objects.equals(originValue, rangeValue)) {
			int compareResult = Long.compare(originValue.value(), rangeValue.value());

			if (compareResult < 0) {
				return !leftMath;
			}
			else if (compareResult > 0) {
				return leftMath;
			}

			originValue = originValue.safeChildren();
			rangeValue = rangeValue.safeChildren();
		}

		return contain;
	}
}
