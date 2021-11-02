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

    /**
     * 包含路径
     */
    private List<String> includePatterns = Collections.emptyList();

    /**
     * 排除路径
     */
    private List<String> excludePatterns = Collections.emptyList();

    /**
     * 路径匹配
     */
    private AntPathMatcher pathMatcher;
}
