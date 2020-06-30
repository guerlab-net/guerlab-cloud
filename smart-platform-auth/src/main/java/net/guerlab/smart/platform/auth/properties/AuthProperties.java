package net.guerlab.smart.platform.auth.properties;

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
public class AuthProperties {

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
