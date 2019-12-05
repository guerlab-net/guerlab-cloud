package net.guerlab.smart.platform.basic.auth.autoconfig;

import net.guerlab.smart.platform.basic.auth.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 抽象鉴权拦截器配置
 *
 * @author guer
 */
public abstract class AbstractAuthInterceptorAutoconfigure<A extends AuthProperties> implements WebMvcConfigurer {

    private A properties;

    protected final void setPathPatterns(InterceptorRegistration interceptor) {
        AntPathMatcher pathMatcher = properties.getPathMatcher();
        List<String> includePatterns = properties.getIncludePatterns();
        List<String> excludePatterns = properties.getExcludePatterns();

        interceptor.pathMatcher(pathMatcher).addPathPatterns(includePatterns).excludePathPatterns(excludePatterns);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setProperties(A properties) {
        this.properties = properties;
    }
}
