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
package net.guerlab.cloud.auth.factory;

import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.TokenFactoryProperties;
import net.guerlab.cloud.commons.ip.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

/**
 * 抽象token工厂
 *
 * @param <T>
 *         token实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractTokenFactory<T, P extends TokenFactoryProperties<?>> implements TokenFactory<T> {

    /**
     * 配置文件
     */
    protected P properties;

    protected static String getObjectValue(@Nullable Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    @SuppressWarnings("SameReturnValue")
    protected abstract String getPrefix();

    @Override
    public final String getRefreshTokenPrefix() {
        return getPrefix() + CONNECTORS + TokenType.SIMPLE_NAME_REFRESH_TOKEN + CONNECTORS;
    }

    @Override
    public final String getAccessTokenPrefix() {
        return getPrefix() + CONNECTORS + TokenType.SIMPLE_NAME_ACCESS_TOKEN + CONNECTORS;
    }

    @Override
    public final boolean acceptIp(String ip) {
        if (IpUtils.inList(properties.getDenyIpList(), ip)) {
            return false;
        }

        if (properties.getAllowIpList() == null || properties.getAllowIpList().isEmpty()) {
            return true;
        }

        return IpUtils.inList(properties.getAllowIpList(), ip);
    }

    @Override
    public final boolean enabled() {
        return properties.isEnable();
    }

    @Override
    public final boolean isDefault() {
        return properties.isDefaultFactory();
    }

    @Override
    public final int getOrder() {
        return properties.getOrder();
    }

    @Override
    public int compareTo(TokenFactory<?> o) {
        if (isDefault() && o.isDefault()) {
            return getOrder() - o.getOrder();
        } else if (isDefault()) {
            return -1;
        } else if (o.isDefault()) {
            return 1;
        }

        return getOrder() - o.getOrder();
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setProperties(P properties) {
        this.properties = properties;
    }
}
