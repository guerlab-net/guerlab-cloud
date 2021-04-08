package net.guerlab.smart.platform.server.autoconfigure;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.server.properties.NacosServerProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多实例注册自动配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(NacosServerProperties.class)
public class MultiNacosApplicationAutoConfigure
        implements ApplicationListener<WebServerInitializedEvent>, DisposableBean {

    private final NamingService namingService;

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosServerProperties serverProperties;

    private final Map<String, Instance> instanceMap = new HashMap<>();

    private final AtomicInteger port = new AtomicInteger(0);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MultiNacosApplicationAutoConfigure(NacosServerProperties serverProperties,
            NacosDiscoveryProperties discoveryProperties) {
        this.serverProperties = serverProperties;
        this.discoveryProperties = discoveryProperties;
        this.namingService = discoveryProperties.namingServiceInstance();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port.compareAndSet(0, event.getWebServer().getPort());

        if (serverProperties.getAppNames() == null) {
            return;
        }

        serverProperties.getAppNames().forEach(appName -> registerInstance(appName, discoveryProperties));
    }

    private void registerInstance(String appName, NacosDiscoveryProperties discoveryProperties) {
        String clusterName = StringUtils.trimToNull(appName);
        if (clusterName == null) {
            return;
        }

        Instance instance = new Instance();
        instance.setIp(discoveryProperties.getIp());
        instance.setPort(port.get());
        instance.setClusterName(clusterName);
        instance.setMetadata(discoveryProperties.getMetadata());

        if (instanceMap.putIfAbsent(clusterName, instance) == null) {
            try {
                namingService.registerInstance(clusterName, instance);
            } catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage(), e);
            }
        }
    }

    @Override
    public void destroy() {
        instanceMap.forEach((clusterName, instance) -> {
            try {
                namingService.deregisterInstance(clusterName, instance);
            } catch (Exception e) {
                log.debug(e.getLocalizedMessage(), e);
            }
        });
    }
}
