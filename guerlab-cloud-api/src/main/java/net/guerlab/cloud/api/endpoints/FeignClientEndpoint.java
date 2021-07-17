package net.guerlab.cloud.api.endpoints;

import feign.Target;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.cloud.openfeign.FeignClient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FeignClient实例监控端点
 *
 * @author guer
 */
@Slf4j
@Endpoint(id = "feign-client")
public class FeignClientEndpoint {

    private static FeignClientInfo buildFeignClientInfo(String beanName, Object obj) {
        Target.HardCodedTarget<?> target = getProxyTarget(obj);
        if (target == null) {
            return null;
        }

        FeignClient feignClientAnnotation = target.type().getAnnotation(FeignClient.class);
        if (feignClientAnnotation == null) {
            return null;
        }

        FeignClientAnnotationInfo annotationInfo = new FeignClientAnnotationInfo();
        annotationInfo.setName(feignClientAnnotation.name());
        annotationInfo.setContextId(feignClientAnnotation.contextId());
        annotationInfo.setUrl(feignClientAnnotation.url());
        annotationInfo.setDecode404(feignClientAnnotation.decode404());
        annotationInfo.setPath(feignClientAnnotation.path());
        annotationInfo.setPrimary(feignClientAnnotation.primary());

        FeignClientInfo info = new FeignClientInfo();
        info.setBeanName(beanName);
        info.setClassPath(target.type().getName());
        info.setUrl(target.url());
        info.setAnnotation(annotationInfo);
        return info;
    }

    private static Target.HardCodedTarget<?> getProxyTarget(Object obj) {
        if (!(obj instanceof Proxy)) {
            return null;
        }

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(obj);
        Class<? extends InvocationHandler> invocationHandlerClass = invocationHandler.getClass();

        try {
            Field field = invocationHandlerClass.getDeclaredField("target");
            field.setAccessible(true);
            Object target = field.get(invocationHandler);

            if (target instanceof Target.HardCodedTarget<?>) {
                return (Target.HardCodedTarget<?>) target;
            }

            return null;
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unused")
    @ReadOperation
    public Collection<FeignClientInfo> getFeignInstances() {
        Map<String, Object> beanMap = SpringApplicationContextUtil.getContext()
                .getBeansWithAnnotation(FeignClient.class);

        if (beanMap.isEmpty()) {
            return Collections.emptyList();
        }

        return beanMap.entrySet().stream().map(entry -> buildFeignClientInfo(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    @ReadOperation
    public FeignClientInfo getFeignInstance(@Selector String arg0) {
        Map<String, Object> beanMap = SpringApplicationContextUtil.getContext()
                .getBeansWithAnnotation(FeignClient.class);
        Object obj = beanMap.get(arg0);

        if (obj == null) {
            return null;
        }

        return buildFeignClientInfo(arg0, obj);
    }
}
