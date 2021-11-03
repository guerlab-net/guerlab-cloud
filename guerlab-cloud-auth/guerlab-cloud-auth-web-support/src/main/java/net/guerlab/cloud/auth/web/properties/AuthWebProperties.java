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
package net.guerlab.cloud.auth.web.properties;

import lombok.Data;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.util.AntPathMatcher;

import java.util.Collections;
import java.util.List;

/**
 * 认证配置
 *
 * @author guer
 */
@Data
public class AuthWebProperties {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * 包含路径
     */
    private List<String> includePatterns = Collections.emptyList();

    /**
     * 排除路径
     */
    private List<String> excludePatterns = Collections.emptyList();

    /**
     * 判断路径是否匹配
     *
     * @param path
     *         路径
     * @return 是否匹配
     */
    public boolean match(String path) {
        boolean include = include(path);
        boolean notExclude = !exclude(path);
        return include && notExclude;
    }

    /**
     * 判断路径是否在包含列表中
     *
     * @param path
     *         路径
     * @return 是否在包含列表中
     */
    private boolean include(String path) {
        if (CollectionUtil.isEmpty(includePatterns)) {
            return true;
        }

        return includePatterns.stream().anyMatch(pattern -> MATCHER.match(pattern, path));
    }

    /**
     * 判断路径是否在排除列表中
     *
     * @param path
     *         路径
     * @return 是否在排除列表中
     */
    private boolean exclude(String path) {
        if (CollectionUtil.isEmpty(excludePatterns)) {
            return false;
        }

        return excludePatterns.stream().anyMatch(pattern -> MATCHER.match(pattern, path));
    }
}
