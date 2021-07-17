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
package net.guerlab.cloud.auth.web.autoconfig;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.web.interceptor.AbstractHandlerInterceptor;
import net.guerlab.cloud.auth.web.interceptor.AbstractTokenHandlerInterceptor;
import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
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
@Slf4j
public abstract class AbstractAuthInterceptorAutoconfigure<A extends AuthWebProperties> implements WebMvcConfigurer {

    private A properties;

    private List<? extends AbstractHandlerInterceptor> tokenHandlerInterceptors;

    @Override
    public final void addInterceptors(InterceptorRegistry registry) {
        addTokenHandlerInterceptors(registry);
        addInterceptorsInternal(registry);
    }

    private void addTokenHandlerInterceptors(InterceptorRegistry registry) {
        if (tokenHandlerInterceptors == null || tokenHandlerInterceptors.isEmpty()) {
            log.debug("tokenHandlerInterceptors is empty");
            return;
        }

        for (AbstractHandlerInterceptor interceptor : tokenHandlerInterceptors) {
            log.debug("register token interceptor[{}]", interceptor);
            setPathPatterns(registry.addInterceptor(interceptor).order(AbstractTokenHandlerInterceptor.DEFAULT_ORDER));
        }
    }

    /**
     * 添加其他拦截器处理
     *
     * @param registry
     *         InterceptorRegistry实例
     */
    @SuppressWarnings({ "EmptyMethod", "unused" })
    protected void addInterceptorsInternal(InterceptorRegistry registry) {
        /* 默认空实现 */
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

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setProperties(A properties) {
        this.properties = properties;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired(required = false)
    public void setTokenHandlerInterceptors(AbstractTokenHandlerInterceptor<A>[] tokenHandlerInterceptors) {
        this.tokenHandlerInterceptors = Arrays.stream(tokenHandlerInterceptors).filter(Objects::nonNull)
                .filter(interceptor -> Objects.equals(interceptor.getAuthProperties(), this.properties))
                .collect(Collectors.toList());
    }
}
