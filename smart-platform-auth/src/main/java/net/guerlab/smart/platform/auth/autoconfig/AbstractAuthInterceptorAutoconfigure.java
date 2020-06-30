package net.guerlab.smart.platform.auth.autoconfig;

import net.guerlab.smart.platform.auth.interceptor.AbstractHandlerInterceptor;
import net.guerlab.smart.platform.auth.interceptor.AbstractTokenHandlerInterceptor;
import net.guerlab.smart.platform.auth.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 抽象鉴权拦截器配置
 *
 * @author guer
 */
public abstract class AbstractAuthInterceptorAutoconfigure<A extends AuthProperties> implements WebMvcConfigurer {

    private A properties;

    private List<? extends AbstractHandlerInterceptor> tokenHandlerInterceptors;

    @Override
    public final void addInterceptors(InterceptorRegistry registry) {
        addTokenHandlerInterceptors(registry);
        addInterceptorsInternal(registry);
    }

    private void addTokenHandlerInterceptors(InterceptorRegistry registry) {
        if (tokenHandlerInterceptors != null) {
            for (AbstractHandlerInterceptor handlerInterceptor : tokenHandlerInterceptors) {
                setPathPatterns(registry.addInterceptor(handlerInterceptor));
            }
        }
    }

    /**
     * 添加其他拦截器处理
     *
     * @param registry
     *         InterceptorRegistry实例
     */
    protected void addInterceptorsInternal(InterceptorRegistry registry) {
    }

    /**
     * 设置路径信息
     *
     * @param interceptor
     *         拦截器
     */
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

    @Autowired(required = false)
    public void setTokenHandlerInterceptors(AbstractTokenHandlerInterceptor<A>[] tokenHandlerInterceptors) {
        if (tokenHandlerInterceptors == null) {
            return;
        }

        List<AbstractHandlerInterceptor> interceptors = Arrays.stream(tokenHandlerInterceptors).filter(Objects::nonNull)
                .filter(interceptor -> Objects.equals(interceptor.getAuthProperties(), this.properties))
                .collect(Collectors.toList());

        this.tokenHandlerInterceptors = interceptors;
    }
}
