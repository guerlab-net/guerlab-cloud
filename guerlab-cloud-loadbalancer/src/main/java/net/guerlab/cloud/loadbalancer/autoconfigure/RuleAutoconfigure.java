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
package net.guerlab.cloud.loadbalancer.autoconfigure;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import net.guerlab.cloud.loadbalancer.properties.ClusterSameProperties;
import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import net.guerlab.cloud.loadbalancer.rule.ClusterSameRule;
import net.guerlab.cloud.loadbalancer.rule.IRule;
import net.guerlab.cloud.loadbalancer.rule.VersionMatchRule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 规则自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ VersionControlProperties.class, ClusterSameProperties.class })
public class RuleAutoconfigure {

    /**
     * 构造版本匹配规则
     *
     * @param properties
     *         版本匹配规则配置
     * @return 版本匹配规则
     */
    @Bean
    public IRule versionMatchRule(VersionControlProperties properties) {
        return new VersionMatchRule(properties);
    }

    /**
     * 构造相同集群策略
     *
     * @param nacosDiscoveryProperties
     *         Nacos发现配置
     * @param properties
     *         相同集群配置
     * @return 相同集群策略
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public IRule clusterSameRule(NacosDiscoveryProperties nacosDiscoveryProperties, ClusterSameProperties properties) {
        String clusterName = nacosDiscoveryProperties.getClusterName();
        return new ClusterSameRule(clusterName, properties);
    }
}
