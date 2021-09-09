package net.guerlab.cloud.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.api.headers.HeadersContextCleanHandlerInterceptor;
import net.guerlab.cloud.api.headers.HeadersRequestInterceptor;
import net.guerlab.cloud.loadbalancer.autoconfigure.GlobalLoadBalancerAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * 请求头处理相关自动配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@AutoConfigureAfter(GlobalLoadBalancerAutoConfiguration.class)
public class HeadersContextAutoConfigure {

    /**
     * 构建请求头处理请求拦截器
     *
     * @return 请求头处理请求拦截器
     */
    @Bean
    public HeadersRequestInterceptor headersRequestInterceptor() {
        return new HeadersRequestInterceptor();
    }

    /**
     * 构造请求信息清理拦截器
     *
     * @return 请求信息清理拦截器
     */
    @Bean
    @Conditional(HeadersContextAutoConfigure.WrapperCondition.class)
    public HeadersContextCleanHandlerInterceptor headersContextCleanHandlerInterceptor() {
        return new HeadersContextCleanHandlerInterceptor();
    }

    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("org.springframework.web.servlet.HandlerInterceptor") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
