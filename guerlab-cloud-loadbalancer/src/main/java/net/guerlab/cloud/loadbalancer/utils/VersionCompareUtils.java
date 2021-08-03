/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.loadbalancer.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 版本比较工具类<br>
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
 *
 * <tr><th scope="row">1.0</th>     <td>True</td>   <td>False</td>  <td>True</td>   <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.0.1</th>   <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.1</th>     <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.1.1</th>   <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.2</th>     <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>   <td>True</td>   <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">1.2.1</th>   <td>False</td>  <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>  <td>True</td>   <td>False</td>  <td>False</td>
 * <tr><th scope="row">str</th>     <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>False</td>  <td>True</td>   <td>True</td>
 * </tbody>
 * </table>
 *
 * @author guer
 */
@Slf4j
public class VersionCompareUtils {

    private static final String INTERVAL = "\\.";

    private static final String GROUP = ",";

    private static final String LEFT_CONTAIN = "[";

    private static final String RIGHT_CONTAIN = "]";

    private static final String LEFT_NOT_CONTAIN = "(";

    private static final String RIGHT_NOT_CONTAIN = ")";

    private VersionCompareUtils() {
    }

    /**
     * 匹配检查
     *
     * @param origin
     *         源版本，支持单版本号格式
     * @param range
     *         检查范围，支持单版本号格式和范围版本号格式
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
            } else if (hasLeftFlag && hasRightFlag && hasGroupFlag) {
                return rangeMatch(origin, range);
            } else {
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 相等匹配
     *
     * @param origin
     *         请求源版本
     * @param range
     *         范围版本
     * @return 是否匹配
     */
    private static boolean equalsMatch(String origin, String range) {
        String[] originValues = origin.split(INTERVAL);
        String[] rangeValues = range.split(INTERVAL);

        int size = Math.max(originValues.length, rangeValues.length);

        Integer[] origins = formatVersionString(originValues, size);
        Integer[] ranges = formatVersionString(rangeValues, size);

        for (int i = 0; i < size; i++) {
            Integer ov = origins[i];
            Integer rv = ranges[i];
            if (!Objects.equals(ov, rv)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 范围匹配
     *
     * @param origin
     *         请求源版本
     * @param range
     *         范围版本
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
        if (rangeGroup.length != 2) {
            return false;
        }

        boolean leftRangeMatchResult = rangeMatch(origin, StringUtils.trimToEmpty(rangeGroup[0]), leftContain, true);
        boolean rightRangeMatchResult = rangeMatch(origin, StringUtils.trimToEmpty(rangeGroup[1]), rightContain, false);

        return leftRangeMatchResult && rightRangeMatchResult;
    }

    /**
     * 范围匹配
     *
     * @param origin
     *         请求源版本
     * @param range
     *         范围版本
     * @param contain
     *         是否包含
     * @param leftMath
     *         左匹配模式
     * @return 是否匹配
     */
    private static boolean rangeMatch(String origin, String range, boolean contain, boolean leftMath) {
        String[] originValues = versionStringFilter(origin).split(INTERVAL);
        String[] rangeValues = versionStringFilter(range).split(INTERVAL);

        int size = Math.max(originValues.length, rangeValues.length);

        Integer[] origins = formatVersionString(originValues, size);
        Integer[] ranges = formatVersionString(rangeValues, size);

        for (int i = 0; i < size; i++) {
            Integer ov = origins[i];
            Integer rv = ranges[i];
            int compareResult = ov.compareTo(rv);

            if (compareResult < 0) {
                return !leftMath;
            } else if (compareResult > 0) {
                return leftMath;
            } else if (i == size - 1) {
                return contain;
            }
        }
        return false;
    }

    /**
     * 字符串版本过滤器
     *
     * @param origin
     *         待过滤字符串
     * @return 过滤后的字符串
     */
    private static String versionStringFilter(String origin) {
        origin = StringUtils.trimToEmpty(origin);
        StringBuilder builder = new StringBuilder();

        int i = 0;
        int end = origin.length();
        for (; i < end; i++) {
            char cv = origin.charAt(i);
            boolean isNumber = cv >= '0' && cv <= '9';
            boolean isPoint = cv == '.';
            if (isNumber || isPoint) {
                builder.append(cv);
            }
        }

        return builder.toString();
    }

    /**
     * 格式化字符串版本数组
     *
     * @param values
     *         字符串版本数组
     * @param length
     *         数值版本数组长度
     * @return 数值版本数组
     */
    private static Integer[] formatVersionString(String[] values, int length) {
        Integer[] result = new Integer[length];

        int i = 0;
        int end = Math.min(values.length, length);
        for (; i < end; i++) {
            result[i] = stringToInteger(values[i]);
        }

        if (values.length < length) {
            for (; i < length; i++) {
                result[i] = 0;
            }
        }

        return result;
    }

    /**
     * 将string转换为integer
     *
     * @param str
     *         待转换字符串
     * @return 转换后数值
     */
    private static Integer stringToInteger(String str) {
        try {
            return Math.max(Integer.parseInt(str), 0);
        } catch (Exception ignore) {
            return 0;
        }
    }
}
